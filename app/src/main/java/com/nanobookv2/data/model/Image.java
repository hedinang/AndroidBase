package com.nanobookv2.data.model;

public class Image {
    private String id;
    private String imageId;
    public String imageName;
    public String imagePath;
    public String imageType;

    public String getId() {
        if (id == null)
            return imageId;
        else
            return id;
    }

    public void setId(String id) {
        this.imageId = id;
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
        this.id = imageId;
    }
}
