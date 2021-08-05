package com.example.myapplication.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class dogeAPI {
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()

                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .baseUrl("https://api.thedogapi.com/v1/")
                        .build();
            }
            return retrofit;
        }


}
