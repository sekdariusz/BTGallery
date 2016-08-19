package com.btskyrise.btgallery;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class ThumbnailsFetcher {

    private Cursor imageCursor;
    private Activity context;

    public ThumbnailsFetcher(Activity context) {
        this.context = context;
    }

    public void getThumbnailsFromMemory(final List<ImageModel> items) {
        Nammu.askForPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE, new PermissionCallback() {
            @Override
            public void permissionGranted() {
                readPhotosFromMemoryToList(items);
            }

            @Override
            public void permissionRefused() {
                Toast.makeText(context, "Permissions required to show photos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readPhotosFromMemoryToList(final List<ImageModel> items) {
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
        imageCursor = context.getContentResolver().query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media._ID);

        final int nameColumnIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        final int imagePathColumnIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        final int size = imageCursor.getCount();

        new AsyncTask<Void, ImageModel, Void>() {

            String imageName;
            String imagePath;

            @Override
            protected Void doInBackground(Void... voids) {

                for (int i = 0; i < size; i++) {
                    imageCursor.moveToPosition(i);
                    imageName = imageCursor.getString(nameColumnIndex);
                    imagePath = imageCursor.getString(imagePathColumnIndex);
                    publishProgress(new ImageModel(imageName, imagePath));
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(ImageModel... values) {
                super.onProgressUpdate(values);

                items.add(values[0]);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                imageCursor.close();
            }
        }.execute();
    }
}
