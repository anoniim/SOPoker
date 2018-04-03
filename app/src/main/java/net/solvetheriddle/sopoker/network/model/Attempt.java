package net.solvetheriddle.sopoker.network.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import net.solvetheriddle.sopoker.app.profile.data.local.db.DateConverter;

import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Attempt {

    @PrimaryKey
    private Date timestamp;
    private @AttemptStatus int status;
    @Embedded private User user;

    public Attempt(final Date timestamp, final int status, User user) {
        this.timestamp = timestamp;
        this.status = status;
        this.user = user;
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
}
