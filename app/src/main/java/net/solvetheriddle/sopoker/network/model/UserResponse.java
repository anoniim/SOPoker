package net.solvetheriddle.sopoker.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse implements Parcelable {

    public static final Parcelable.Creator<UserResponse> CREATOR = new Parcelable.Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel source) {return new UserResponse(source);}

        @Override
        public UserResponse[] newArray(int size) {return new UserResponse[size];}
    };
    @SerializedName("items")
    @Expose
    public List<User> items = null;
    @SerializedName("has_more")
    @Expose
    public Boolean hasMore;
    @SerializedName("quota_max")
    @Expose
    public Integer quotaMax;
    @SerializedName("quota_remaining")
    @Expose
    public Integer quotaRemaining;

    public UserResponse() {}

    protected UserResponse(Parcel in) {
        this.items = in.createTypedArrayList(User.CREATOR);
        this.hasMore = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.quotaMax = (Integer) in.readValue(Integer.class.getClassLoader());
        this.quotaRemaining = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public List<User> getItems() {
        return items;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public Integer getQuotaMax() {
        return quotaMax;
    }

    public Integer getQuotaRemaining() {
        return quotaRemaining;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.items);
        dest.writeValue(this.hasMore);
        dest.writeValue(this.quotaMax);
        dest.writeValue(this.quotaRemaining);
    }
}