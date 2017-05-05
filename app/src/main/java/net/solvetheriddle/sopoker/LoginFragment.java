package net.solvetheriddle.sopoker;


import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

public class LoginFragment extends WebViewFragment {


    public static final String LOGIN_URI = "LOGIN_URI";

    public static Fragment newInstance(final Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(LOGIN_URI, uri);
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(args);
        return loginFragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        WebView webView = getWebView();
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(getPage());
        webView.setWebViewClient(new LoginWebViewClient(getActivity()));
        return view;
    }

    private String getPage() {
        return getArguments().getParcelable(LOGIN_URI).toString();
    }

    private class LoginWebViewClient extends WebViewClient {

        private static final String EXPECTED_HOST = "stackexchange.com"; // TODO Marcel
        private final Activity activity;

        private LoginWebViewClient(Activity activity) {
            this.activity = activity;
        }

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
            Uri url = request.getUrl();
            if(!EXPECTED_HOST.equals(url.getHost())) return false;

            String accessToken = url.getQueryParameter("access_token");
            String expires = url.getQueryParameter("expires");
            return true;
        }

    }
}
