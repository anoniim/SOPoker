package net.solvetheriddle.sopoker.network;

import net.solvetheriddle.sopoker.network.model.UserResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface StackExchangeService {

    @GET("/me")
    Observable<UserResponse> getProfile(
            @Query("access_token") String accessToken,
            @Query("key") String key,
            @Query("site") String site);

}
