package net.solvetheriddle.sopoker.app.login;


import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginWebView extends WebView {

    private LoginPresenter mLoginPresenter;

    public LoginWebView(Context context) {
        super(context);
    }

    public void init(final LoginPresenter loginPresenter) {
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

    private class LoginWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
            mLoginPresenter.parseAccessToken(url);
        }
    }
}
