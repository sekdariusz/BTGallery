package com.btskyrise.btgallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class DetailActivity extends AppCompatActivity {

    public static final float FULL_HD = 1920;

    private ImageView fullSizePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        ImageModel selectedItem = ImageContext.getInstance().getSelectedItem();
        setupViewsFor(selectedItem);
    }

    private void setupViewsFor(final ImageModel selectedItem) {
        fullSizePhoto = (ImageView) findViewById(R.id.image);

        ResizePhotoTask resizePhotoTask = new ResizePhotoTask(fullSizePhoto);
        resizePhotoTask.execute(selectedItem.getImagePath());

        TextView imageName = (TextView) findViewById(R.id.name);
        imageName.setText(selectedItem.getImageName());
    }

    private class ResizePhotoTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;

        public ResizePhotoTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imagePath = strings[0];
            File imgFile = new  File(imagePath);
            if(imgFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                float bitmapWidth = bitmap.getWidth();
                float bitmapHeight = bitmap.getHeight();

                if (bitmapWidth > FULL_HD || bitmapHeight > FULL_HD) {
                    float biggerSize = Math.max(bitmapWidth, bitmapHeight);
                    float scaleFactor = biggerSize / FULL_HD;
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int)(bitmapWidth/scaleFactor), (int)(bitmapHeight/scaleFactor), false);
                }
                return bitmap;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null)
                imageView.setImageBitmap(bitmap);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startPostponedEnterTransition();
            }
        }
    }
}
