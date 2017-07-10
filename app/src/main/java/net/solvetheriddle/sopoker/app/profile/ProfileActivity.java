package net.solvetheriddle.sopoker.app.profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.SoPokerApp;
import net.solvetheriddle.sopoker.app.settings.SettingsActivity;
import net.solvetheriddle.sopoker.dagger.component.DaggerProfileScreenComponent;
import net.solvetheriddle.sopoker.network.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements ProfileScreenContract.View {

    public static final String PROFILE = "profile";

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.username_text) TextView mUsername;
    @BindView(R.id.poke_fab) View mFab;

    @Nullable
    private User mProfile;

    @Inject
    ProfilePresenter mProfilePresenter;

    public static Intent getCallingIntent(Context context, final User profile) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.PROFILE, profile);
        return intent;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SoPokerApp application = (SoPokerApp) getApplication();
        DaggerProfileScreenComponent.builder()
                .appComponent(application.getAppComponent())
                .build()
                .inject(this);

        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mProfile = getProfile();



        showProfile(mProfile);

        mFab.setOnClickListener(view -> mProfilePresenter.schedulePoking());
    }

    @Nullable
    User getProfile() {
        if (getIntent() != null && getIntent().getExtras().containsKey(PROFILE)) {
            return getIntent().getParcelableExtra(PROFILE);
        } else {
            return null;
        }
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
    public void showProfile(final User profile) {
        mUsername.setText(profile.getDisplayName());
        // TODO
    }
}
