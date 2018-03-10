package net.solvetheriddle.sopoker.network.model;


import android.os.Parcel;
import android.os.Parcelable;

public class AccessToken implements Parcelable {

    public static final int NO_EXPIRY = -1;
    public static final Parcelable.Creator<AccessToken> CREATOR
            = new Parcelable.Creator<AccessToken>() {
        @Override
        public AccessToken createFromParcel(Parcel source) {return new AccessToken(source);}

        @Override
        public AccessToken[] newArray(int size) {return new AccessToken[size];}
    };
    private final String mAccessToken;
    private final int mExpiry;

    public AccessToken(final String accessToken, final int expiry) {
        mAccessToken = accessToken;
        mExpiry = expiry;
    }

    protected AccessToken(Parcel in) {
        this.mAccessToken = in.readString();
        this.mExpiry = in.readInt();
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public int getExpiry() {
        return mExpiry;
    }

    @Override
    public String toString() {
        return "Access token [" + mAccessToken + ", " +mExpiry + "]";
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAccessToken);
        dest.writeInt(this.mExpiry);
    }
}
