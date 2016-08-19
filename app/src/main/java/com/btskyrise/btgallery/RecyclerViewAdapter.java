package com.btskyrise.btgallery;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final int TARGET_WIDTH = 400;
    public static final int TARGET_HEIGHT = 400;

    private List<ImageModel> items;
    private int itemLayout;
    private Activity context;

    public RecyclerViewAdapter(List<ImageModel> items, int itemLayout, Activity context) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ImageModel item = items.get(position);

        holder.imageName.setText(item.getImageName());
        Picasso.with(context).load(new File(item.getImagePath())).resize(TARGET_WIDTH, TARGET_HEIGHT).centerCrop().into(holder.image);

        holder.containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageContext.getInstance().setImageContext(item);
                startActivityWithSharedViews(holder);
            }
        });

        holder.itemView.setTag(item);
    }

    private void startActivityWithSharedViews(ViewHolder holder) {
        Pair<View, String> p1 = Pair.create((View)holder.image, context.getString(R.string.image_transition));
        Pair<View, String> p2 = Pair.create((View)holder.imageName, context.getString(R.string.name_transition));

        Intent intent = new Intent(context, DetailActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, p1, p2);
        context.startActivity(intent, options.toBundle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View containerView;
        public ImageView image;
        public TextView imageName;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            imageName = (TextView) itemView.findViewById(R.id.name);
            containerView = itemView.findViewById(R.id.container_view);
        }
    }
}