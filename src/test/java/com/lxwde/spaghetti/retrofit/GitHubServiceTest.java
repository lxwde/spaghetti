package com.lxwde.spaghetti.retrofit;

import org.junit.Test;

import java.io.IOException;

public class GitHubServiceTest {

    @Test
    public void testGetTopContributors() throws IOException {
        String userName = "eugenp";
        new GitHubBasicService().getTopContributors(userName).forEach( s -> System.out.println(s));
    }

    @Test
    public void testRxGetTopContributors() {
        String userName = "eugenp";
        new GitHubRxService().getTopContributors(userName).subscribe(System.out::println);
    }
}
