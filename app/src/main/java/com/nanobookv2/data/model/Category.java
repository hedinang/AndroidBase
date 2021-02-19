package com.nanobookv2.data.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    public int id;
    @SerializedName("nameEn")
    public String nameEn;
    @SerializedName("nameVi")
    public String nameVi;
    @SerializedName("isPrimary")
    public Boolean isPrimary;
}
