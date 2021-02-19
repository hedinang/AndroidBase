package com.nanobookv2.data.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Campaign {
    @SerializedName("selectionKey")
    public String selectionKey;
    @SerializedName("description")
    public String description;
    @SerializedName("bgColor")
    public String bgColor;
    @SerializedName("fontSize")
    public int fontSize;
    @SerializedName("fontKind")
    public String fontKind;
    @SerializedName("link")
    public String link;
    @SerializedName("createdAt")
    public Timestamp createdAt;
    @SerializedName("updatedAt")
    public Timestamp updatedAt;
}
