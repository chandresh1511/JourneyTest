package com.mahi.journeytest.PostModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentResponseResult {


    @SerializedName("postId")
    @Expose
    public int postId;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("body")
    @Expose
    public String body;

    public CommentResponseResult(int postId,int id,String name, String email, String body) {
        super();
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public List<Object> depositeMasters = null;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.id = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
