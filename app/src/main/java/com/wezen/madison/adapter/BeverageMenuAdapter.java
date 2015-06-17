package com.wezen.madison.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wezen.madison.R;
import com.wezen.madison.fragment.BeverageDetailFragment;
import com.wezen.madison.fragment.GridFragment;
import com.wezen.madison.model.Beverage;
import com.wezen.madison.model.BeverageMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eder on 4/8/15.
 */
public class BeverageMenuAdapter extends RecyclerView.Adapter<BeverageMenuAdapter.ViewHolder> {
    private List<BeverageMenu> mDataset;
    private Context context;
    private FragmentManager fm;

    public BeverageMenuAdapter(ArrayList<BeverageMenu> mDataset, Context context, FragmentManager fm){
        this.mDataset = mDataset;
        this.context = context;
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_beverage_menu,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final BeverageMenu item = mDataset.get(position);
        viewHolder.tvFooter.setText(item.getBeverageMenuName());
        Bitmap bmp = decodeSampledBitmapFromResource(
                item.getBeverageMenuImage(),
                80,
                100
        );
        viewHolder.ivBeverage.setImageBitmap(bmp);
        final TextView tvName = viewHolder.tvFooter;

        Palette.generateAsync(bmp, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Here's your generated palette
                Palette.Swatch swatch = palette.getMutedSwatch();
                if(swatch != null){
                    tvName.setBackgroundColor(swatch.getRgb());
                    tvName.setTextColor(swatch.getTitleTextColor());
                }
            }
        });


        viewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container, GridFragment.newInstance(position));
                        ft.addToBackStack(null);
                        ft.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(int position, BeverageMenu item){
        mDataset.add(position,item);
        notifyItemInserted(position);
    }

    public void remove(Beverage item){
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvFooter;
        public View content;
        public ImageView ivBeverage;

        public ViewHolder(View v) {
            super(v);

            tvFooter = (TextView)v.findViewById(R.id.tvBeverageMenu);
            content = v.findViewById(R.id.beverageMenuContent);
            ivBeverage = (ImageView)v.findViewById(R.id.ivBeverageMenu);
            
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

    public static Bitmap decodeSampledBitmapFromResource(byte[] res,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeByteArray(res,0,res.length);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return  BitmapFactory.decodeByteArray(res,0,res.length);
    }
}
