package net.solvetheriddle.sopoker.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {return new User(source);}

        @Override
        public User[] newArray(int size) {return new User[size];}
    };
    @SerializedName("badge_counts")
    @Expose
    public BadgeCounts badgeCounts;
    @SerializedName("view_count")
    @Expose
    public Integer viewCount;
    @SerializedName("down_vote_count")
    @Expose
    public Integer downVoteCount;
    @SerializedName("up_vote_count")
    @Expose
    public Integer upVoteCount;
    @SerializedName("answer_count")
    @Expose
    public Integer answerCount;
    @SerializedName("question_count")
    @Expose
    public Integer questionCount;
    @SerializedName("account_id")
    @Expose
    public Integer accountId;
    @SerializedName("is_employee")
    @Expose
    public Boolean isEmployee;
    @SerializedName("last_modified_date")
    @Expose
    public Integer lastModifiedDate;
    @SerializedName("last_access_date")
    @Expose
    public Integer lastAccessDate;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("reputation_change_year")
    @Expose
    public Integer reputationChangeYear;
    @SerializedName("reputation_change_quarter")
    @Expose
    public Integer reputationChangeQuarter;
    @SerializedName("reputation_change_month")
    @Expose
    public Integer reputationChangeMonth;
    @SerializedName("reputation_change_week")
    @Expose
    public Integer reputationChangeWeek;
    @SerializedName("reputation_change_day")
    @Expose
    public Integer reputationChangeDay;
    @SerializedName("reputation")
    @Expose
    public Integer reputation;
    @SerializedName("creation_date")
    @Expose
    public Integer creationDate;
    @SerializedName("user_type")
    @Expose
    public String userType;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("accept_rate")
    @Expose
    public Integer acceptRate;
    @SerializedName("about_me")
    @Expose
    public String aboutMe;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("website_url")
    @Expose
    public String websiteUrl;
    @SerializedName("link")
    @Expose
    public String link;
    @SerializedName("profile_image")
    @Expose
    public String profileImage;
    @SerializedName("display_name")
    @Expose
    public String displayName;

    public User() {}

    protected User(Parcel in) {
        this.badgeCounts = in.readParcelable(BadgeCounts.class.getClassLoader());
        this.viewCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.downVoteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.upVoteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.answerCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.questionCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.accountId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isEmployee = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.lastModifiedDate = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastAccessDate = (Integer) in.readValue(Integer.class.getClassLoader());
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reputationChangeYear = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reputationChangeQuarter = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reputationChangeMonth = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reputationChangeWeek = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reputationChangeDay = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reputation = (Integer) in.readValue(Integer.class.getClassLoader());
        this.creationDate = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userType = in.readString();
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.acceptRate = (Integer) in.readValue(Integer.class.getClassLoader());
        this.aboutMe = in.readString();
        this.location = in.readString();
        this.websiteUrl = in.readString();
        this.link = in.readString();
        this.profileImage = in.readString();
        this.displayName = in.readString();
    }

    public BadgeCounts getBadgeCounts() {
        return badgeCounts;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public Integer getDownVoteCount() {
        return downVoteCount;
    }

    public Integer getUpVoteCount() {
        return upVoteCount;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Boolean getEmployee() {
        return isEmployee;
    }

    public Integer getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Integer getLastAccessDate() {
        return lastAccessDate;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getReputationChangeYear() {
        return reputationChangeYear;
    }

    public Integer getReputationChangeQuarter() {
        return reputationChangeQuarter;
    }

    public Integer getReputationChangeMonth() {
        return reputationChangeMonth;
    }

    public Integer getReputationChangeWeek() {
        return reputationChangeWeek;
    }

    public Integer getReputationChangeDay() {
        return reputationChangeDay;
    }

    public Integer getReputation() {
        return reputation;
    }

    public Integer getCreationDate() {
        return creationDate;
    }

    public String getUserType() {
        return userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAcceptRate() {
        return acceptRate;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getLocation() {
        return location;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getLink() {
        return link;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.badgeCounts, flags);
        dest.writeValue(this.viewCount);
        dest.writeValue(this.downVoteCount);
        dest.writeValue(this.upVoteCount);
        dest.writeValue(this.answerCount);
        dest.writeValue(this.questionCount);
        dest.writeValue(this.accountId);
        dest.writeValue(this.isEmployee);
        dest.writeValue(this.lastModifiedDate);
        dest.writeValue(this.lastAccessDate);
        dest.writeValue(this.age);
        dest.writeValue(this.reputationChangeYear);
        dest.writeValue(this.reputationChangeQuarter);
        dest.writeValue(this.reputationChangeMonth);
        dest.writeValue(this.reputationChangeWeek);
        dest.writeValue(this.reputationChangeDay);
        dest.writeValue(this.reputation);
        dest.writeValue(this.creationDate);
        dest.writeString(this.userType);
        dest.writeValue(this.userId);
        dest.writeValue(this.acceptRate);
        dest.writeString(this.aboutMe);
        dest.writeString(this.location);
        dest.writeString(this.websiteUrl);
        dest.writeString(this.link);
        dest.writeString(this.profileImage);
        dest.writeString(this.displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
