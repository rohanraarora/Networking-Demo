package com.example.android.networkingdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ralph on 07/10/17.
 */

public interface CoursesService {


    @POST("courses.json")
    Call<CoursesResponse> getCourses();

}
