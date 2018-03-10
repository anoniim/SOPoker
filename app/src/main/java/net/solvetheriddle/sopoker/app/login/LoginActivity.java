package net.solvetheriddle.sopoker.app.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.SoPokerApp;
import net.solvetheriddle.sopoker.app.profile.ProfileActivity;
import net.solvetheriddle.sopoker.dagger.component.DaggerLoginScreenComponent;
import net.solvetheriddle.sopoker.dagger.module.LoginScreenModule;
import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginScreenContract.View {

    public static final int REDIRECT_DELAY = 2000;
    public static final int REQUEST_AUTHENTICATE = 100;
    private static final String SHARED_PREFERENCES_NAME = "AuthStatePreference";
    private static final String AUTH_STATE = "AUTH_STATE";
    private static final String USED_INTENT = "USED_INTENT";
    private static final String TAG = LoginActivity.class.getCanonicalName();
    @BindView(R.id.login_button) Button mLoginButton;
    @BindView(R.id.login_log) TextView mLoginLog;

    @Inject LoginPresenter mLoginPresenter;

    private AlertDialog mLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SoPokerApp application = (SoPokerApp) getApplication();
        DaggerLoginScreenComponent.builder()
                .appComponent(application.getAppComponent())
                .loginScreenModule(new LoginScreenModule(this))
                .build()
                .inject(this);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initLoginButton();
    }

    private void initLoginButton() {
        mLoginButton.setOnClickListener(view -> {
            mLoginButton.setEnabled(false);
            mLoginPresenter.login();
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
            final Intent data) {
        if (requestCode == REQUEST_AUTHENTICATE && resultCode == RESULT_OK) {
            final AccessToken accessToken = data
                    .getParcelableExtra(AuthenticationActivity.EXTRA_AUTH_TOKEN);
            mLoginPresenter.authenticationSuccessful(accessToken);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoginPresenter.autoLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        mLoginDialog = null;
        super.onDestroy();
    }

    @Override
    public void startAuthentication(@NonNull String loginUrl) {
        log(R.string.login_log_open_auth_dialog);

        startActivityForResult(AuthenticationActivity.getStartingIntent(this, loginUrl),
                REQUEST_AUTHENTICATE);
    }

    @Override
    public void showError(final String message) {
        log(getString(R.string.login_log_error, message));
        mLoginButton.setEnabled(true);
    }

    @Override
    public void logProfile(@Nullable final User profile) {
        log(getString(R.string.login_log_logged_in, profile));
    }

    @Override
    public void redirectToProfile(final User profile) {
        log(R.string.login_log_done);
        // TODO Rx for the delay?
        new Handler().postDelayed(() -> {
            finishAndGoToProfile(profile);
        }, REDIRECT_DELAY);
    }

    void finishAndGoToProfile(final User profile) {
        startActivity(ProfileActivity.getCallingIntent(this, profile));
        finish();
    }

    @Override
    public void log(@StringRes final int log) {
        log(getString(log));
    }

    private void log(String line) {
        CharSequence currentLog = mLoginLog.getText();
        mLoginLog.setText(currentLog + "\n" + line);
        Log.i("LOG", line);
    }
}