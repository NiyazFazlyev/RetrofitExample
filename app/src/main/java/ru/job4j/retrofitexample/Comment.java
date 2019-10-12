package ru.job4j.retrofitexample;

import com.google.gson.annotations.SerializedName;

public class Comment {
    private String id;
    private String postId;
    private String name;
    private String email;

    @SerializedName("body")
    private String text;

    public String getId() {
        return id;
    }

    public String getPostId() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}

