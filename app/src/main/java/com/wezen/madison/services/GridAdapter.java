package com.wezen.madison.services;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wezen.madison.R;
import com.wezen.madison.fragment.BeverageDetailFragment;
import com.wezen.madison.model.Beverage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eder on 4/8/15.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private List<Beverage> mDataset;
    private Context context;
    private FragmentManager fm;

    public GridAdapter(ArrayList<Beverage> mDataset, Context context, FragmentManager fm){
        this.mDataset = mDataset;
        this.context = context;
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_drink,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Beverage beverage = mDataset.get(position);
        //viewHolder.tvHeader.setText(mDataset.get(position).getName());
        //viewHolder.tvServiceName.setText(mDataset.get(position).getPrice());
        //viewHolder.ivBeverage.setImageResource(mDataset.get(position).getImageResId());
        /*viewHolder.ivBeverage.setImageBitmap(decodeSampledBitmapFromResource(
                context.getResources(),
                mDataset.get(position).getImageResId(),
                80,
                100
        ));*/

        viewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Click", "Item Click");
                //TODO pasar como parametro al adpter el fragmentmanager
                /*android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container, BeverageDetailFragment.newInstance("", ""));
                        ft.addToBackStack(null);
                        ft.commit();*/
                Intent serviceDetail = new Intent(context, ServiceDetailActivity.class);
                context.startActivity(serviceDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(int position, Beverage item){
        mDataset.add(position,item);
        notifyItemInserted(position);
    }

    public void remove(Beverage item){
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

      //  public TextView tvHeader;
        public TextView tvServiceName;
        public View content;
        public ImageView ivBeverage;

        public ViewHolder(View v) {
            super(v);
           // tvHeader = (TextView)v.findViewById(R.id.tvItemDrinkName);
            tvServiceName = (TextView)v.findViewById(R.id.tvItemServiceName);
            content = v.findViewById(R.id.itemGridContent);
            ivBeverage = (ImageView)v.findViewById(R.id.ivBeverage);
            
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
