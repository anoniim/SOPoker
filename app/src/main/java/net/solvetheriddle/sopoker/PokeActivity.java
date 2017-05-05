package net.solvetheriddle.sopoker;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.solvetheriddle.sopoker.data.component.DaggerPokeScreenComponent;
import net.solvetheriddle.sopoker.data.model.Response;
import net.solvetheriddle.sopoker.data.model.User;
import net.solvetheriddle.sopoker.data.module.PokeScreenModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokeActivity extends AppCompatActivity implements PokeScreenContract.View {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.login_button) Button mLoginButton;
    @BindView(R.id.user_text) TextView mUserText;
    @BindView(R.id.user_edit) EditText mUserEdit;
    @BindView(R.id.poke_fab) FloatingActionButton mFab;
    @BindView(R.id.content) ViewGroup mContent;

    @Inject PokePresenter mPokePresenter;

    private AlertDialog mLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SoPokerApp application = (SoPokerApp) getApplication();
        DaggerPokeScreenComponent.builder()
                .netComponent(application.getNetComponent())
                .pokeScreenModule(new PokeScreenModule(this))
                .build().inject(this);

        setSupportActionBar(mToolbar);

        mLoginButton.setOnClickListener(view -> {
            mPokePresenter.loadUsers(mUserEdit.getText().toString());
            mLoginButton.setEnabled(false);
        });
        mFab.setOnClickListener(view -> mPokePresenter.poke());

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPokePresenter.handleAuthResponse(getIntent().getData());
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
    public void showUsers(final Response response) {
        if (response != null) {
            List<User> users = response.getItems();
            if (users != null) {
                if (users.isEmpty()) {
                    mUserText.setText("no users");
                } else {
                    mUserText.setText(users.get(0).getDisplayName() + "\n(" + users.size() + " users found)");
                }
            } else {
                mUserText.setText("users null");
            }
        } else {
            mUserText.setText("null response");
        }
    }

    @Override
    public void showToken(final String token) {
        mUserText.setText(token);
    }

    @Override
    public void showError(final String message) {
        mUserText.setText(message);
        mLoginButton.setEnabled(true);
    }

    @Override
    public void showComplete() {
        mLoginButton.setEnabled(true);
    }

    @Override
    public void openAuthDialog(final Uri authUri) {
        WebView webView = new WebView(this){
            @Override
            public boolean onCheckIsTextEditor() {
                return true;
            }
        };
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(authUri.toString());
        webView.setWebViewClient(new LoginWebViewClient());

        mLoginDialog = new AlertDialog.Builder(this)
                .setTitle("Login to StackOverflow")
                .setView(webView)
                .create();
        mLoginDialog.show();
    }

    @Override
    public void closeAuthDialog() {
        mLoginDialog.dismiss();
    }

    private class LoginWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
            mPokePresenter.getAccessToken(url);
        }
    }
}