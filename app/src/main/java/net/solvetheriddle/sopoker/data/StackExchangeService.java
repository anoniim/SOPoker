package net.solvetheriddle.sopoker.data;


import net.solvetheriddle.sopoker.data.model.Response;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface StackExchangeService {

    @GET("/users")
    Observable<Response> getUser(@Query("site") String site, @Query("inname") String inname);

//    @GET("/oauth/dialog")
//    Call login(String clientId, String scope, String redirectUri, String state);
}
