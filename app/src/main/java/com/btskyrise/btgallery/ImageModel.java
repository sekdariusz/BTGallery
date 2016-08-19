package com.btskyrise.btgallery;

public class ImageModel {

    private String imageName;
    private String imagePath;

    public ImageModel(String imageName, String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

}
