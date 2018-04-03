package net.solvetheriddle.sopoker.network.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef()
@Retention(RetentionPolicy.SOURCE)
public @interface AttemptStatus {
    int SUCCESS = 0;
    int CONNECTION_ERROR = 1;
    int REQUST_ERROR = 2;
}
