package it.celli.testgithub.di;


import android.content.Context;

import com.squareup.picasso.Picasso;

public class PicassoInstance {

    private Picasso picasso;

    public PicassoInstance(Context context) {
        picasso = new Picasso.Builder(context).build();
    }

    public Picasso getInstance() {
        return picasso;
    }
}
