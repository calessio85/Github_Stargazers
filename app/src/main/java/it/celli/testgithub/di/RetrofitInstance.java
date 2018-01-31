package it.celli.testgithub.di;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import it.celli.testgithub.data.GithubAPIService;
import it.celli.testgithub.utils.Utils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private GithubAPIService service;

    public RetrofitInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.service = retrofit.create(GithubAPIService.class);
    }

    public GithubAPIService getService() {
        return this.service;
    }
}
