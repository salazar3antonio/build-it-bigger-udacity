package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.studentproject.jokes_java_lib.Joke;
import com.studentproject.myandroidlibrary.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.backend.MyEndpoint;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();

        try {
            endpointsAsyncTask.execute(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

        private static final String JOKE_EXTRA = "joke_extra";

        private MyApi myApiService = null;
        private Context mContext;

        @Override
        protected String doInBackground(Context... params) {

            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                myApiService = builder.build();
            }

            mContext = params[0];

            try {
                return myApiService.getJoke().execute().getJoke();
            } catch (IOException e) {
                return e.getMessage();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            Intent jokeDisplayIntent = new Intent(mContext, JokeDisplayActivity.class);
            jokeDisplayIntent.putExtra(JOKE_EXTRA, result);
            mContext.startActivity(jokeDisplayIntent);

        }


    }

}
