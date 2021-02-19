package com.nanobookv2.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseBase<T> implements Serializable {
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public T data;
}
