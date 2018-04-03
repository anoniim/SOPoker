package net.solvetheriddle.sopoker.app.profile.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import net.solvetheriddle.sopoker.network.model.Attempt;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface AttemptDao {

    @Query("select * from attempt"
           + " order by timestamp desc")
    LiveData<List<Attempt>> loadAllAttempts();

    @Query("select * from attempt"
           + " where status = 0"
           + " order by timestamp desc limit 1")
    Flowable<Attempt> loadLatestSuccessfulAttempt();

    @Insert(onConflict = IGNORE)
    void insertAttempt(Attempt attempt);

    @Delete
    void deleteAttempt(Attempt attempt);

    @Query("DELETE FROM attempt")
    void deleteAll();
}
