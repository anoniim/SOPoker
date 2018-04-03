package net.solvetheriddle.sopoker.app.profile.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import net.solvetheriddle.sopoker.app.profile.data.local.AttemptDao;
import net.solvetheriddle.sopoker.network.model.Attempt;

@Database(entities = {Attempt.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AttemptDao attemptModel();
}