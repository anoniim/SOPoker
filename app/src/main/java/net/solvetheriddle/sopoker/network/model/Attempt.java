package net.solvetheriddle.sopoker.network.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.IntDef;

import net.solvetheriddle.sopoker.app.profile.data.local.db.DateConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Attempt {

    private @Status int status;

    @PrimaryKey
    private Date timestamp;
    private boolean manual;

    public Attempt(final Date date, final boolean manual) {
        this.timestamp = date;
        this.manual = manual;
    }
    @Embedded private User user;

    public Attempt(final Date timestamp, final int status, User user) {
        this.timestamp = timestamp;
        this.status = status;
        this.user = user;
    }

    public boolean isManual() {
        return manual;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public void setManual(final boolean manual) {
        this.manual = manual;
    }

    @Override
    public String toString() {
        return "Attempt{" +
               "timestamp=" + timestamp +
               ", status=" + status +
               ", manual=" + manual +
               '}';
    }

    @IntDef()
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
        int POKE_SUCCESS = 0;
        int POKE_NOT_NEEDED = 1;
        int POKE_ERROR = 2;
    }
}
