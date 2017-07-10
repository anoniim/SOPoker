package net.solvetheriddle.sopoker.network;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.User;
import net.solvetheriddle.sopoker.network.model.UserResponse;

import java.util.List;

public class ResponseParser {

    @Nullable
    public AccessToken parseAccessToken(@NonNull final String url) {
        if (url.contains("access_token=")) {
            String[] parameters = Uri.parse(url).getFragment().split("&");
            if (parameters.length > 0) {
                String accessToken = getParameterValue(parameters[0]);
                if (!TextUtils.isEmpty(accessToken)) {
                    int expiry = getExpiry(parameters);
                    return new AccessToken(accessToken, expiry);
                }
            }
        }
        return null;
    }

    private int getExpiry(@NonNull final String[] accessTokenParameters) {
        int expiry = AccessToken.NO_EXPIRY;
        if (accessTokenParameters.length > 1) {
            expiry = Integer.valueOf(getParameterValue(accessTokenParameters[1]));
        }
        return expiry;
    }

    @NonNull
    private String getParameterValue(@NonNull final String keyValue) {
        if (TextUtils.isEmpty(keyValue)) {
//        return keyValue.split("=")[1];
            return keyValue.substring(keyValue.indexOf("=") + 1);
        } else {
            return "";
        }
    }

    @Nullable
    public User parseUser(final UserResponse response) {
        if (response != null) {
            List<User> users = response.getItems();
            if (users != null && !users.isEmpty()) {
                return users.get(0);
            }
        }
        return null;
    }
}
