package net.solvetheriddle.sopoker.app.profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.SoPokerApp;
import net.solvetheriddle.sopoker.app.login.AuthenticationActivity;
import net.solvetheriddle.sopoker.app.settings.SettingsActivity;
import net.solvetheriddle.sopoker.dagger.component.DaggerProfileScreenComponent;
import net.solvetheriddle.sopoker.dagger.module.ProfileScreenModule;
import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements ProfileScreenContract.View {

    public static final int REQUEST_AUTHENTICATE = 100;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.username_text) TextView mUsername;
    @BindView(R.id.poke_fab) View mFab;
    @BindView(android.R.id.content) View mContentView;
    @Inject ProfilePresenter mProfilePresenter;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SoPokerApp application = (SoPokerApp) getApplication();
        DaggerProfileScreenComponent.builder()
                .appComponent(application.getAppComponent())
                .profileScreenModule(new ProfileScreenModule(this))
                .build()
                .inject(this);

        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mProfilePresenter.loadProfile();

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
    public void startAuthenticationActivity(@NonNull String loginUrl) {
        startActivityForResult(AuthenticationActivity.getStartingIntent(this, loginUrl),
                REQUEST_AUTHENTICATE);
    }

    @Override
    public void showAuthenticationError() {
        Snackbar.make(mContentView, "Please sign in", Snackbar.LENGTH_LONG)
                .setAction("SIGN IN", v -> mProfilePresenter.authenticate())
                .show();
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
    public void showProfile(final User profile) {
        mUsername.setText(profile.getDisplayName());
        // TODO
    }

    @Override
    public void showError(final String message) {
        Snackbar.make(mContentView, message, Snackbar.LENGTH_LONG).show();
    }
}
