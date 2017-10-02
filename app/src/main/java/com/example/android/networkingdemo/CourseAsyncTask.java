package com.example.android.networkingdemo;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ralph on 01/10/17.
 */

public class CourseAsyncTask extends AsyncTask<String,Void,ArrayList<Course>> {

    public CoursesDownloadListener coursesDownloadListener;

    public CourseAsyncTask(CoursesDownloadListener listener) {

        coursesDownloadListener = listener;
    }

    @Override
    protected ArrayList<Course> doInBackground(String... strings) { //DIFFERENT THREAD
        //CODE

        Log.i("Course AsyncTask","Inside Async Task ");
        String urlString = strings[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            Log.i("Courses Response:","Connection Started");
            urlConnection.connect();
            Log.i("Courses Response:","Connection Complete");
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String response = "";
            while (scanner.hasNext()){
                response += scanner.next();
            }
            Log.i("Courses Response:",response);
            ArrayList<Course> courses = parseCourses(response);
            return  courses;



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<Course> parseCourses(String response) throws JSONException{
        ArrayList<Course> courses = null;
        JSONObject rootObject = new JSONObject(response);
        JSONObject dataObject = rootObject.getJSONObject("data");
        JSONArray coursesJSONArray = dataObject.getJSONArray("courses");
        if(coursesJSONArray!=null){
            courses = new ArrayList<>();
            for(int i = 0;i<coursesJSONArray.length();i++){
                JSONObject courseObject = coursesJSONArray.getJSONObject(i);
                int id = courseObject.getInt("id");
                String title = courseObject.getString("title");
                String name = courseObject.getString("name");
                String overview = courseObject.getString("overview");
                Course course = new Course(id,title,name,overview);
                courses.add(course);
            }
        }
        return courses;
    }

    @Override
    protected void onPreExecute() { //MAIN THREAD
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Course> courses) {//MAIN THREAD

        Log.i("Courses Array Size",courses.size() +"");
        coursesDownloadListener.onDownload(courses);
    }



    public static interface CoursesDownloadListener{

        void onDownload(ArrayList<Course> courses);

    }

}
