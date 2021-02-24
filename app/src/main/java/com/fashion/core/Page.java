package com.fashion.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
    @SerializedName("total")
    public int total;
    @SerializedName("page")
    public int page;
    @SerializedName("perPage")
    public int perPage;
    @SerializedName("result")
    public List<T> result = new ArrayList<>();
}
