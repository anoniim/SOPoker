package net.solvetheriddle.sopoker.app.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.network.ResponseParser;
import net.solvetheriddle.sopoker.network.model.AccessToken;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AuthenticationActivity extends Activity implements LoginWebView.AuthResponseHandler {

    public static final String EXTRA_AUTH_URL = "EXTRA_AUTH_URL";
    public static final String EXTRA_AUTH_TOKEN = "EXTRA_AUTH_TOKEN";
    private static final String TAG = AuthenticationActivity.class.getCanonicalName();
    @BindView(R.id.auth_webview)
    LoginWebView mLoginWebView;
    private ResponseParser mResponseParser;

    public static Intent getStartingIntent(Context context, final String authUrl) {
        final Intent intent = new Intent(context, AuthenticationActivity.class);
        intent.putExtra(EXTRA_AUTH_URL, authUrl);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        ButterKnife.bind(this);

        mLoginWebView.init(this);
        mLoginWebView.login(getIntent().getStringExtra(EXTRA_AUTH_URL));

        mResponseParser = new ResponseParser();
    }

    @Override
    public void parseAccessToken(final String url) {
        Log.d(TAG, "URL opened: " + url);
        if (url != null) {
            AccessToken accessToken = mResponseParser.parseAccessToken(url);
            if (accessToken != null) {
                finishWithOk(accessToken);
            } else {
                // URL does not contain access token
                Log.d(TAG, "URL does not contain access token");
            }
        } else {
            Log.d(TAG, "Tried to parse access token from null URL");
        }
    }

    void finishWithOk(final AccessToken accessToken) {
        final Intent data = new Intent();
        data.putExtra(EXTRA_AUTH_TOKEN, accessToken);
        setResult(RESULT_OK, data);
        finish();
    }
}
