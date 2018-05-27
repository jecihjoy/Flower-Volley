package com.example.jecihjoy.androidrestapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.jecihjoy.androidrestapp.MainActivity;
import com.example.jecihjoy.androidrestapp.R;
import com.example.jecihjoy.androidrestapp.model.Flower;
import java.util.List;

/**
 * Created by Jecihjoy on 5/22/2018.
 */

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder> {

    private Context ctx;
    private  List<Flower> flowerList;
    private View view;
    private LruCache<Integer, Bitmap> imageCache;
    private RequestQueue requestQueue;

    public FlowerAdapter(Context context, List<Flower> flowers) {
        flowerList = flowers;
        ctx = context;

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);

        requestQueue = Volley.newRequestQueue(ctx);
    }

    public  class FlowerViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView image;

        public FlowerViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.txname);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    @Override
    public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layout = R.layout.flower;
        LayoutInflater infater = LayoutInflater.from(context);
        boolean attachImmeaditely = false;

        view = infater.inflate(layout, parent,attachImmeaditely);
        FlowerViewHolder myViewHolder = new FlowerViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FlowerViewHolder holder, int position) {
        final Flower myflower = flowerList.get (position);
        holder.textView.setText(myflower.getName());
        Bitmap bitmap = imageCache.get(myflower.getProductId());
        if(bitmap != null){
            holder.image.setImageBitmap(bitmap);
        }else {
            String imageurl = MainActivity.PHOTOS_BASE_URL + myflower.getPhoto();
            ImageRequest request = new ImageRequest(
                    imageurl,//"http://services.hanselandpetal.com/photos/dianthus.jpg",

                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder.image.setImageBitmap(response);
                            imageCache.put(myflower.getProductId(), response);
                        }
                    },

                    0, 0,
                    ImageView.ScaleType.CENTER_CROP,

                    Bitmap.Config.ARGB_8888,

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.d("FlowerAdapter", error.getMessage());
                        }
                    }

            );
            requestQueue.add(request);
        }

    }

    @Override
    public int getItemCount() {
        return flowerList.size();
    }

}
