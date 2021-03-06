package net.solvetheriddle.sopoker.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Entity(foreignKeys = {
//        @ForeignKey(entity = User.class,
//                parentColumns = "attemptTimestamp",
//                childColumns = "attemptTimestamp")})
public class BadgeCounts implements Parcelable {

    @SerializedName("bronze")
    @Expose
    public Integer bronze;
    @SerializedName("silver")
    @Expose
    public Integer silver;
    @SerializedName("gold")
    @Expose
    public Integer gold;

//    @PrimaryKey
//    @NonNull
//    @ColumnInfo(name="attemptTimestamp")
//    public long attemptTimestamp;

    public BadgeCounts() {}

    public static final Parcelable.Creator<BadgeCounts> CREATOR = new Parcelable.Creator<BadgeCounts>() {
        @Override
        public BadgeCounts createFromParcel(Parcel source) {return new BadgeCounts(source);}

        @Override
        public BadgeCounts[] newArray(int size) {return new BadgeCounts[size];}
    };

    protected BadgeCounts(Parcel in) {
        this.bronze = (Integer) in.readValue(Integer.class.getClassLoader());
        this.silver = (Integer) in.readValue(Integer.class.getClassLoader());
        this.gold = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public Integer getBronze() {
        return bronze;
    }

    public Integer getSilver() {
        return silver;
    }

    public Integer getGold() {
        return gold;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.bronze);
        dest.writeValue(this.silver);
        dest.writeValue(this.gold);
    }
}
