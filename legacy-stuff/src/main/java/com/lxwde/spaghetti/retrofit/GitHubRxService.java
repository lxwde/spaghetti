package com.lxwde.spaghetti.retrofit;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

class GitHubRxService {

    private GitHubRxApi gitHubApi;

    GitHubRxService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        gitHubApi = retrofit.create(GitHubRxApi.class);
    }

    Observable<String> getTopContributors(String userName) {
        return gitHubApi.listRepos(userName)
                .flatMapIterable(x -> x)
                .flatMap(repo -> gitHubApi.listRepoContributors(userName, repo.getName()))
                .flatMapIterable(x -> x)
                .filter(c -> c.getContributions() > 100)
                .sorted((a, b) -> b.getContributions() - a.getContributions())
                .map(Contributor::getName)
                .distinct();
    }
}
