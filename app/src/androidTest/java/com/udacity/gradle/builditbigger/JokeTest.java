package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ragnar on 10/4/2015.
 */
public class JokeTest extends ApplicationTestCase<Application> implements OnJokeReceivedListener {

    CountDownLatch signal;
    String joke;

    public JokeTest() {
        super(Application.class);
    }

    public void testJoke() {
        try {
            signal = new CountDownLatch(1);
            new EndpointsAsyncTask().execute(this);
            signal.await(5, TimeUnit.SECONDS);

            assertNotNull("joke is null", joke);

            assertFalse("joke is empty", joke.isEmpty());

        } catch (Exception ex) {
            fail();
        }
    }

    @Override
    public void onReceived(String joke) {
        this.joke = joke;
        signal.countDown();
    }
}