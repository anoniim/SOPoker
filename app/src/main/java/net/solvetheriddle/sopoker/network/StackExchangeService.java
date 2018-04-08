package net.solvetheriddle.sopoker.network;

import net.solvetheriddle.sopoker.network.model.UserResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackExchangeService {

    @GET("/me")
    Observable<Response<UserResponse>> getProfile(
            @Query("access_token") String accessToken,
            @Query("key") String key,
            @Query("site") String site);

}
