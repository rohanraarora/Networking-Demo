package com.example.android.networkingdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CourseAsyncTask.CoursesDownloadListener{

    ArrayList<Course> courses = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> titles = new ArrayList<>();
    ProgressBar progressBar;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://codingninjas.in/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoursesService service = retrofit.create(CoursesService.class);

        final Call<CoursesResponse> coursesResponseCall = service.getCourses();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);


                coursesResponseCall.enqueue(new Callback<CoursesResponse>() {
                    @Override
                    public void onResponse(Call<CoursesResponse> call, Response<CoursesResponse> response) {
                        CoursesResponse coursesResponse = response.body();
                        ArrayList<Course> courses = coursesResponse.data.courses;
                        titles.clear();
                        for(Course course:courses){
                            titles.add(course.title);
                        }

                        arrayAdapter.notifyDataSetChanged();
                        listView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<CoursesResponse> call, Throwable t) {

                    }
                });

            //fetchCourses();
            }
        });

        listView = (ListView)findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(arrayAdapter);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);



        //This is in second_branch

        //Another comment in master

        //Another comment in second branch







    }


    public void fetchCourses(){

        String urlString = "https://codingninjas.in/api/v2/courses.json";
        CourseAsyncTask asyncTask = new CourseAsyncTask(this);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        asyncTask.execute(urlString);
        Log.i("Course Async Task","After execution");


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

    @Override
    public void onDownload(ArrayList<Course> courses) {

        titles.clear();
        for(Course course:courses){
            titles.add(course.title);
        }

        arrayAdapter.notifyDataSetChanged();
        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);


    }

    public void setTextInView(String s){

        //TODO
    }
}
