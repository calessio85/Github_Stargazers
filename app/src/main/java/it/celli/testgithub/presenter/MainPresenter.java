package it.celli.testgithub.presenter;


import java.util.List;

import io.reactivex.Observable;
import it.celli.testgithub.data.Stargazer;
import it.celli.testgithub.di.RetrofitInstance;

public class MainPresenter {

    private RetrofitInstance retrofitInstance;

    public MainPresenter(RetrofitInstance retrofitInstance) {
        this.retrofitInstance = retrofitInstance;
    }

    public Observable<List<Stargazer>> loadStargazers(String owner, String repo, int page) {
        return retrofitInstance.getService().getStargazersList(owner, repo, page);
    }
}
