package net.solvetheriddle.sopoker.app.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.SoPokerApp;
import net.solvetheriddle.sopoker.app.profile.ProfileActivity;
import net.solvetheriddle.sopoker.dagger.component.DaggerLoginScreenComponent;
import net.solvetheriddle.sopoker.dagger.module.LoginScreenModule;
import net.solvetheriddle.sopoker.network.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginScreenContract.View {

    public static final int REDIRECT_DELAY = 2000;

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

        mLoginPresenter.autoLogin();
    }

    private void initLoginButton() {
        mLoginButton.setOnClickListener(view -> {
            mLoginButton.setEnabled(false);
            mLoginPresenter.login();
        });
    }

    @Override
    protected void onDestroy() {
        mLoginDialog = null;
        super.onDestroy();
    }

    @Override
    public void openAuthDialog(@NonNull String loginUrl) {
        log(R.string.login_log_open_auth_dialog);

        LoginWebView loginWebView = new LoginWebView(this);
        loginWebView.init(mLoginPresenter);
        loginWebView.login(loginUrl);

        mLoginDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.login_button)
                .setView(loginWebView)
                .setOnDismissListener(view -> {
                    mLoginButton.setEnabled(true);
                    log(R.string.login_log_close_auth_dialog);
                })
                .create();
        mLoginDialog.show();
    }

    @Override
    public void closeAuthDialog() {
        mLoginDialog.dismiss();
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
    }
}