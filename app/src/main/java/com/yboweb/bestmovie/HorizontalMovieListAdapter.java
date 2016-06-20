package com.yboweb.bestmovie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.javacodegeeks.androidnavigationdrawerexample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by test on 16/04/16.
 */



    /**
     * Created by test on 24/03/16.
     */
    public class HorizontalMovieListAdapter extends ArrayAdapter<MovieItem> {





        //private final ColorMatrixColorFilter grayscaleFilter;
        private Context mContext;
        private int layoutResourceId;
        private ArrayList<MovieItem> mGridData = new ArrayList<MovieItem>();

        public HorizontalMovieListAdapter(Context mContext, int layoutResourceId, ArrayList<MovieItem> mGridData) {
            super(mContext, layoutResourceId, mGridData);
            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.mGridData = mGridData;
        }


        /**
         * Updates grid data and refresh grid items.
         *
         * @param mGridData
         */
        public void setGridData(ArrayList<MovieItem> mGridData) {

            this.mGridData = mGridData;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;
            ;

            if (row == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
            /* holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title); */
                holder.imageView = (ImageView) row.findViewById(R.id.my_item_image);
                holder.textView = (TextView) row.findViewById(R.id.my_item_title);

                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            MovieItem item = mGridData.get(position);
            Picasso.with(mContext).load(item.getImage(position)).into(holder.imageView);
            holder.textView.setText(item.gettTitle());
            final String id = item.getId();
             final Activity activity = (Activity) mContext;



            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {



                    Intent intent = new Intent(activity, ScrollingActivity.class);
                    Bundle args = new Bundle();

                    args.putString("id", id);
                    intent.putExtras(args);
                    activity.startActivity(intent);
                }
            });
            return row;
        }

         class ViewHolder {
            ImageView imageView;
             TextView textView;
        }
    }

