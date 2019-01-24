package com.application.hp.sqllite;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
    }
    public void Start(View view){

        Async task = new Async(this);
        task.execute(10);
    }

    private static class Async extends AsyncTask<Integer,Integer,String>{
        private WeakReference<MainActivity> activityWeakReference;

        Async(MainActivity activity){
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MainActivity activity = activityWeakReference.get();
            if (activity==null||activity.isFinishing()){
                return;
            }

            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for(int i=0;i<integers[0];i++){
                publishProgress((i*100)/integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Progress Completed!!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            MainActivity activity = activityWeakReference.get();
            if (activity==null||activity.isFinishing()){
                return;
            }

            activity.progressBar.setProgress(values[0]);
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MainActivity activity = activityWeakReference.get();
            if (activity==null||activity.isFinishing()){
                return;
            }

            Toast.makeText(activity,s,Toast.LENGTH_LONG).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);
        }


    }
}
