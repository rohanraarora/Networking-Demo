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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            fetchCourses();
            }
        });

        listView = (ListView)findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(arrayAdapter);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);





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
