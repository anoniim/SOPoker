package net.solvetheriddle.sopoker.app.profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.SoPokerApp;
import net.solvetheriddle.sopoker.dagger.component.DaggerProfileScreenComponent;
import net.solvetheriddle.sopoker.network.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements ProfileScreenContract.View {

    public static final String PROFILE = "profile";
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @Nullable
    private User mProfile;

    public static Intent getCallingIntent(Context context, final User profile) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.PROFILE, profile);
        return intent;
    }
//    @BindView(R.id.poke_fab) FloatingActionButton mFab;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        SoPokerApp application = (SoPokerApp) getApplication();
        DaggerProfileScreenComponent.builder()
                .appComponent(application.getAppComponent())
                .build()
                .inject(this);

        setSupportActionBar(mToolbar);

        mProfile = getIntent().getParcelableExtra(PROFILE);

//        mFab.setOnClickListener(view -> mLoginPresenter.poke());
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProfile(final User profile) {
        // TODO
    }
}
