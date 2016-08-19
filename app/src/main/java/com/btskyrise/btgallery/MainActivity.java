package com.btskyrise.btgallery;

import android.os.Build;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int SPAN_COUNT = 3;

    private List<ImageModel> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupData();
        setupRecyclerView();

        setExitTransition();
        setReenterTransition();
    }

    private void setupData() {
        items.clear();
        ThumbnailsFetcher thumbnailsFetcher = new ThumbnailsFetcher(this);
        thumbnailsFetcher.getThumbnailsFromMemory(items);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(SPAN_COUNT, (int) getResources().getDimension(R.dimen.photo_tile_spacing), true));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(items, R.layout.item_view, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void setExitTransition() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = new Fade();
            transition.setDuration(600);
            transition.setInterpolator(new FastOutLinearInInterpolator());
            getWindow().setExitTransition(transition);
        }
    }

    private void setReenterTransition() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = new Explode();
            transition.setDuration(600);
            transition.setInterpolator(new FastOutLinearInInterpolator());
            getWindow().setReenterTransition(transition);
        }
    }
}
