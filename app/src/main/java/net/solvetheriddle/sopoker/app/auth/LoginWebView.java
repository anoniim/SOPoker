package net.solvetheriddle.sopoker.app.auth;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginWebView extends WebView {

    private AuthResponseHandler mLoginPresenter;

    public LoginWebView(Context context) {
        super(context);
    }

    public LoginWebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginWebView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoginWebView(final Context context, final AttributeSet attrs, final int defStyleAttr,
            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(final AuthResponseHandler loginPresenter) {
        mLoginPresenter = loginPresenter;
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(new LoginWebViewClient());
    }

    public void login(final String loginUrl) {
        loadUrl(loginUrl);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    interface AuthResponseHandler {
        void parseAccessToken(String url);
    }

    private class LoginWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
            mLoginPresenter.parseAccessToken(url);
        }
    }
}
