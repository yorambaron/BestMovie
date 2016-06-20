package com.yboweb.bestmovie;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.javacodegeeks.androidnavigationdrawerexample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by test on 24/03/16.
 */
public class HorizontalListAdapter extends ArrayAdapter<ImageItem> {





    //private final ColorMatrixColorFilter grayscaleFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<ImageItem> mGridData = new ArrayList<ImageItem>();

    public HorizontalListAdapter(Context mContext, int layoutResourceId, ArrayList<ImageItem> mGridData) {
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
            /* holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title); */
            holder.imageView = (ImageView) row.findViewById(R.id.my_item_image);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageItem item = mGridData.get(position);
        /* holder.titleTextView.setText(Html.fromHtml(item.getTitle())); */



        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}