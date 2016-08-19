package com.btskyrise.btgallery;

public class ImageContext {

    private static ImageContext imageContext;

    public static ImageContext getInstance() {
        if (imageContext == null) {
            imageContext = new ImageContext();
        }

        return imageContext;
    }

    private ImageModel selectedItem;

    public void setImageContext(ImageModel selectedItem) {
        this.selectedItem = selectedItem;
    }

    public ImageModel getSelectedItem() {
        return selectedItem;
    }
}
