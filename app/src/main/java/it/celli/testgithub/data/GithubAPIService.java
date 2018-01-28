package it.celli.testgithub.data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubAPIService {

    @Headers({
            "Accept: application/vnd.github.v3+json"
    })
    @GET("repos/{owner}/{repo}/stargazers")
    Observable<List<Stargazer>> getStargazersList(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);
}
