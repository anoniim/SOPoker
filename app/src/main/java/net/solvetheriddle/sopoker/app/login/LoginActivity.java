package net.solvetheriddle.sopoker.app.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginScreenContract.View {

    public static final int REDIRECT_DELAY = 2000;
    public static final int REQUEST_AUTHENTICATE = 100;
    private static final String TAG = LoginActivity.class.getCanonicalName();

    @BindView(R.id.login_button) Button mLoginButton;
    @BindView(R.id.login_log) TextView mLoginLog;

    @Inject LoginPresenter mLoginPresenter;

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
        } else {
            mLoginButton.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isLoggingOut()) {
            mLoginPresenter.logout();
        } else {
            mLoginPresenter.autoLogin();
        }
    }

    private boolean isLoggingOut() {
        final String action = getIntent().getAction();
        return action != null && action.equals("net.solvetheriddle.sopoker.LOGOUT");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void openAuthenticationActivity(@NonNull String loginUrl) {
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
    public void redirectToProfile() {
        startActivity(ProfileActivity.getCallingIntent(this));
        finish();
    }

    @Override
    public void clearBackStack() {
        final Intent clearBackStackIntent = new Intent(this, LoginActivity.class);
        clearBackStackIntent
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(clearBackStackIntent);
    }

    @Override
    public void log(@StringRes final int logResId) {
        log(getString(logResId));
    }

    private void log(String line) {
        CharSequence currentLog = mLoginLog.getText();
        mLoginLog.setText(currentLog + "\n" + line);
        Log.i("LOG", line);
    }
}