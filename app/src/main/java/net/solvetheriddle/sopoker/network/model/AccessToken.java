package net.solvetheriddle.sopoker.network.model;


public class AccessToken {

    private final String mAccessToken;
    private final int mExpiry;

    public AccessToken(final String accessToken, final int expiry) {
        mAccessToken = accessToken;
        mExpiry = expiry;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public int getExpiry() {
        return mExpiry;
    }
}
