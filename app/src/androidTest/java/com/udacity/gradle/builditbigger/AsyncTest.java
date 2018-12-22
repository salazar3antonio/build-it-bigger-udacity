package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AsyncTest {

    private Context appContext;

    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testAsyncIfEmpty() {

        final CountDownLatch signal = new CountDownLatch(1);

        new MainActivity.EndpointsAsyncTask() {

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                assertNotNull(result);

                signal.countDown();

            }
        }.execute(appContext);


        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
