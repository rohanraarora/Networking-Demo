package com.example.android.networkingdemo;

import java.util.ArrayList;

/**
 * Created by ralph on 07/10/17.
 */

public class CoursesResponse {

    Data data;
    String message;
    int status;

    public static class Data{

        ArrayList<Course> courses;

    }

}
