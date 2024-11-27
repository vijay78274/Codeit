package com.example.codeit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

public class FullScreenImage extends DialogFragment {

    private static final String IMAGE_URL = "image_url";

    public static FullScreenImage newInstance(String imageUrl) {
        FullScreenImage fragment = new FullScreenImage();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_image, container, false);

        // Get the ImageView from the layout and load the image using Glide or Picasso
        ImageView imageView = view.findViewById(R.id.full_screen_image_view);
        String imageUrl = getArguments().getString(IMAGE_URL);

        Glide.with(this)
                .load(imageUrl)
                .into(imageView);

        return view;
    }
}

