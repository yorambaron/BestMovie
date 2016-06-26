package com.yboweb.bestmovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yboweb.bestmovie.androidnavigationdrawerexample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by test on 25/05/16.
 */
class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    private float proportion = 1;
    private ArrayList<ImageItem> mGridData = new ArrayList<>();
    LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mGridData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        final String id = mGridData.get(position).getId();
        View itemView = null;
        String imageUrl = mGridData.get(position).getImage();
        final String actorsJson = mGridData.get(position).getActorsJaon();
        int type = mGridData.get(position).getType();
        ImageView imageView = null;

        Log.d("CustomPage", "Type:" + type + " Actorsjson:" + actorsJson + " id:" + id);

        if (type == ImageItem.ACTOR_DETAIL_MOVIE_ITEM) {
            if(id != null) {
                Log.d("CustomPage", "MYID Type:" + type + " Id:" + id + " Title:" + mGridData.get(position).gettTitle());
                itemView = mLayoutInflater.inflate(R.layout.actor_movie_layout, container, false);
                TextView title = (TextView) itemView.findViewById(R.id.my_item_title);
                title.setText(mGridData.get(position).gettTitle());
                imageView = (ImageView) itemView.findViewById(R.id.my_item_image);
                Picasso.with(mContext).load(imageUrl).into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(mContext, ScrollingActivity.class);
                        Bundle args = new Bundle();

                        args.putString("id", mGridData.get(position).getId());
                        intent.putExtras(args);
                        mContext.startActivity(intent);


                    }
                });
            }

        } else if (type == ImageItem.ACTOR_ITEM) {
            Log.d("CustomPage", " Type:" + type + " actorsJson:" + actorsJson);
            itemView = mLayoutInflater.inflate(R.layout.actor_name, container, false);
            imageView = (ImageView) itemView.findViewById(R.id.my_actor_item_image);
            TextView title = (TextView) itemView.findViewById(R.id.my_actor_item_title);
            title.setText(mGridData.get(position).gettTitle());
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Bundle mBundle = new Bundle();
                    mBundle.putString("combined", actorsJson);
                    Intent mIntent = new Intent(mContext, ActorScrollingActivity.class);
                    mIntent.putExtras(mBundle);

                    mContext.startActivity(mIntent);

                }
            });
            Picasso.with(mContext).load(imageUrl).into(imageView);


        } else if (type == ImageItem.VIDEO_ITEM) {
            final String key = mGridData.get(position).getVideoKey();
            //Youtube comment
            if (key != null) {
                Log.d("CustomPage", "Youtube: " + "Type:" + type);

                itemView = mLayoutInflater.inflate(R.layout.image_item_layout, container, false);
                final ImageView imageButton = (ImageView) itemView.findViewById(R.id.play_button);
                imageButton.setVisibility(View.VISIBLE);

                // We want that the click will take place in any place we touch
                FrameLayout frame = (FrameLayout) itemView.findViewById(R.id.touch_link);

                frame.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {


                        Intent intent = new Intent(mContext, PlayVideo.class);
                        intent.setPackage(mContext.getPackageName());
                        Bundle args = new Bundle();
                        args.putString("YOUTUBE_PARAM", key);
                        intent.putExtras(args);
                        mContext.startActivity(intent);

                    }
                });
                imageView = (ImageView) itemView.findViewById(R.id.my_item_image);
                Picasso.with(mContext).load(imageUrl).into(imageView);
            }
        } else if (type == ImageItem.GALLERY_ITEM) {
            final String imageGalleryNames = mGridData.get(position).getimageGalleryNames();

            if (imageGalleryNames != null) {
                itemView = mLayoutInflater.inflate(R.layout.image_item_layout, container, false);
                imageView  = (ImageView) itemView.findViewById(R.id.my_item_image);
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, GridViewActivity.class);
                        intent.setPackage(mContext.getPackageName());
                        Bundle args = new Bundle();
                        args.putString("images", imageGalleryNames);
                        intent.putExtras(args);
                        mContext.startActivity(intent);

                    }
                });
            }
            Picasso.with(mContext).load(imageUrl).into(imageView);
        }

     else {  Log.d("CustomPage", "else");
            itemView = mLayoutInflater.inflate(R.layout.image_item_layout, container, false);
            imageView  = (ImageView) itemView.findViewById(R.id.my_item_image);
            mGridData.get(position).setImageView(imageView);
            Picasso.with(mContext).load(imageUrl).into(imageView);
        }


        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        return(proportion);
    }

    public void set_propop(float value) {
        proportion = value;

    }
    void addItem(ImageItem item) {
        mGridData.add(item);
    }

    public ArrayList<ImageItem> getData() {
        return(mGridData);
    }

    public void setGridData(ArrayList<ImageItem> mGridData) {

        this.mGridData = mGridData;
        notifyDataSetChanged();
    }
}