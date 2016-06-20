package com.yboweb.bestmovie;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
 * Created by test on 27/03/16.
 */
public class VerticalAdapter  extends ArrayAdapter<ImageItem> {





        //private final ColorMatrixColorFilter grayscaleFilter;
        private Context mContext;
        private int layoutResourceId;
        private ArrayList<ImageItem> mGridData = new ArrayList<ImageItem>();

        public VerticalAdapter(Context mContext, int layoutResourceId, ArrayList<ImageItem> mGridData) {
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
        public void setGridData(ArrayList<ImageItem> mGridData) {

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
                holder.imageView = (ImageView) row.findViewById(R.id.main_image);
                holder.titleView =   (TextView) row.findViewById(R.id.title);
                holder.ratingView =   (TextView) row.findViewById(R.id.rating);
                holder.votingView =   (TextView) row.findViewById(R.id.voting);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            ImageItem item = mGridData.get(position);

            Picasso.with(mContext).load(item.getImage()).resize(740, 400).into(holder.imageView);
            //Picasso.with(mContext).load(item.getImage(position)).into(holder.imageView);
             holder.titleView.setText(item.gettTitle());
            holder.titleView.setTextColor(Color.WHITE);
             holder.ratingView.setText(item.gettRating());
            holder.ratingView.setTextColor(Color.WHITE);
             holder.votingView.setText(item.getVoting());
            holder.votingView.setTextColor(Color.WHITE);
            return row;
        }

        static class ViewHolder {
            ImageView imageView;
            TextView titleView;
            TextView votingView;
            TextView ratingView;
            String id;
        }
    }

