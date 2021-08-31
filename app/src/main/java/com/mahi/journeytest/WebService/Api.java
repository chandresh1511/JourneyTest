package com.mahi.journeytest.WebService;


import com.mahi.journeytest.PostModel.CommentResponseResult;
import com.mahi.journeytest.PostModel.PostResponseResult;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {

    // @POST("Register/UserRegister")
    //Call<UserRegistrationModel> userRegister(@Body UserRegistrationByMobileNumberRequestModel userRegistrationByMobileNumberRequestModel);

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("posts")
    Call<List<PostResponseResult>> postResponseResultCall();

    @GET("comments")
    Call<List<CommentResponseResult>> commentResponseResultCall(@Query("postId") String postId);


}
