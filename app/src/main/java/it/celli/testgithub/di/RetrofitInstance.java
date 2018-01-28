package it.celli.testgithub.di;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import it.celli.testgithub.data.GithubAPIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "https://api.github.com";

    private GithubAPIService service;

    public RetrofitInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.service = retrofit.create(GithubAPIService.class);
    }

    public GithubAPIService getService() {
        return this.service;
    }
}
