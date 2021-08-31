package com.mahi.journeytest.PostModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostResponseResult {

    @SerializedName("userId")
    @Expose
    public int userId;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("body")
    @Expose
    public String body;

    public PostResponseResult(int userId,int id,String title, String body) {
        super();
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }


    public List<Object> depositeMasters = null;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.id = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public List<Object> getDepositeMasters() {
        return depositeMasters;
    }

    public void setDepositeMasters(List<Object> depositeMasters) {
        this.depositeMasters = depositeMasters;
    }
}
