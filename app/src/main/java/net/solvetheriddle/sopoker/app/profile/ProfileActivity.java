package net.solvetheriddle.sopoker.app.profile;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.app.auth.AuthenticationActivity;
import net.solvetheriddle.sopoker.app.settings.SettingsActivity;
import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.Disposable;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getCanonicalName();
    public static final int REQUEST_AUTHENTICATE = 100;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.username_text) TextView mUsername;
    @BindView(R.id.reputation_text) TextView mReputationText;
    @BindView(R.id.history_title) TextView mHistoryTitle;
    @BindView(R.id.attempt_history_view) RecyclerView mHistoryView;
    @BindView(R.id.poke_fab) View mFab;
    @BindView(android.R.id.content) View mContentView;

    @Inject ProfilePresenter mProfilePresenter;
    @Inject ProfilePresenter.Factory mProfileViewModelFactory;
    private AttemptHistoryAdapter mAttemptHistoryAdapter;
    private Disposable mAllAttemptsSubscription;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

//        SoPokerApp application = (SoPokerApp) getApplication();
//        DaggerProfileScreenComponent.builder()
//                .appComponent(application.getAppComponent())
//                .profileScreenModule(new ProfileScreenModule(this))
//                .build()
//                .inject(this);

        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        initHistoryView();

        mProfilePresenter = ViewModelProviders.of(this, mProfileViewModelFactory)
                .get(ProfilePresenter.class);

        setObservers();

        mFab.setOnClickListener(view -> mProfilePresenter.schedulePoking());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(SettingsActivity.getCallingIntent(this));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
            final Intent data) {
        if (requestCode == REQUEST_AUTHENTICATE && resultCode == RESULT_OK) {
            final AccessToken accessToken = data
                    .getParcelableExtra(AuthenticationActivity.EXTRA_AUTH_TOKEN);
            mProfilePresenter.authenticationSuccessful(accessToken);
        } else {
//            mLoginButton.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        mAllAttemptsSubscription.dispose();
        super.onDestroy();
    }

    private void startAuthenticationActivity() {
        startActivityForResult(AuthenticationActivity.getStartingIntent(this, mProfilePresenter.getLoginUrl()),
                REQUEST_AUTHENTICATE);
    }

    private void showAuthenticationError() {
        Snackbar.make(mContentView, "Please sign in", Snackbar.LENGTH_LONG)
                .setAction("SIGN IN", v -> startAuthenticationActivity())
                .show();
    }

    private void showProfile(final User profile) {
        mUsername.setText(profile.getDisplayName());
        mReputationText.setText(String.format(getString(R.string.reputation),
                profile.getReputation(),
                profile.getBadgeCounts().getGold(),
                profile.getBadgeCounts().getSilver(),
                profile.getBadgeCounts().getBronze()));
    }

    private void showError(final String message) {
        Snackbar.make(mContentView, message, Snackbar.LENGTH_LONG).show();
    }

    private void initHistoryView() {
        mAttemptHistoryAdapter = new AttemptHistoryAdapter(this);
        mHistoryView.setAdapter(mAttemptHistoryAdapter);
        mHistoryView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setObservers() {
        mAllAttemptsSubscription = mProfilePresenter.getLatestProfile().subscribe(
                response -> {
                    Log.i(TAG, "Profile loaded");
                    showProfile(response);
                },
                throwable -> {
                    Log.i(TAG, "Profile load failed");
                    if (throwable instanceof IllegalAccessException) {
                        showAuthenticationError();
                    } else {
                        showError(throwable.getMessage());
                    }
                });

        mProfilePresenter.getAllAttempts()
                .observe(this, attempts -> {
                    if (attempts != null && !attempts.isEmpty()) {
                        mAttemptHistoryAdapter.setAttempts(attempts);
                        mHistoryTitle.setText(String.format(getString(R.string.history_and_count), attempts.size()));
                    } else {
                        mHistoryTitle.setText("No attempts have been made yet");
                    }
                });
    }
}
