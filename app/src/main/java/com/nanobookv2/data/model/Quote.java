package com.nanobookv2.data.model;

import com.google.gson.annotations.SerializedName;

public class Quote {
    @SerializedName("chapterId")
    public int chapterId;
    @SerializedName("bookId")
    public String bookId;
    @SerializedName("quote")
    public String quote;
    @SerializedName("author")
    public String author;
    @SerializedName("bgColor")
    public String bgColor;
}
