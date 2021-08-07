package com.example.myapplication.API;
import com.example.myapplication.Model.AnalysisDoge;
import com.example.myapplication.Model.AnalysisDoge;
import com.example.myapplication.Model.Result;
import com.example.myapplication.Model.uploadDoge;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface dogeService {

        @GET("breeds")
        @Headers({"Accept: application/json"})
        Call<List<Result>> getDoge(
                @Query("api_key") String apiKey,
                @Query("page") Integer page,
                @Query("limit") Integer limit
                //@Query("language") String language,
                //@Query("page") int pageIndex
        );

        @Multipart
        @POST("images/upload")
        Call<uploadDoge> upload(
                @Part MultipartBody.Part file,
                @Query("api_key") String apiKey
        );

        @GET("images/{image_id}/analysis")
        Call<List<AnalysisDoge>> getAnalysisDoge(
                @Path("image_id") String image_id,
                @Query("api_key") String apiKey
        );
}
