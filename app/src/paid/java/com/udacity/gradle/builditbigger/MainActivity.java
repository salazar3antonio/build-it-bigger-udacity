package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.studentproject.jokes_java_lib.Joke;
import com.studentproject.myandroidlibrary.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.backend.MyEndpoint;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MobileAds.initialize(this, "ca-app-pub-3949996284289774~5260558639");


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

    class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

        private static final String JOKE_EXTRA = "joke_extra";

        private MyEndpoint myEndpointApi = new MyEndpoint();
        private Context mContext;

        @Override
        protected String doInBackground(Context... params) {

            mContext = params[0];
            Joke joke = myEndpointApi.getJoke();
            return joke.getJoke();

        }

        @Override
        protected void onPostExecute(String result) {

            Intent jokeDisplayIntent = new Intent(mContext, JokeDisplayActivity.class);
            jokeDisplayIntent.putExtra(JOKE_EXTRA, result);
            mContext.startActivity(jokeDisplayIntent);

        }


    }


}
