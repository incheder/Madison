package com.wezen.madison.categories;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;
import com.wezen.madison.R;
import com.wezen.madison.model.Beverage;
import com.wezen.madison.model.BeverageMenu;
import com.wezen.madison.model.Category;
import com.wezen.madison.services.ServicesListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eder on 4/8/15.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<Category> mDataset;
    private Context context;
    private FragmentManager fm;

    public CategoriesAdapter(ArrayList<Category> mDataset, Context context, FragmentManager fm){
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
        final Category item = mDataset.get(position);
        viewHolder.tvFooter.setText(item.getName());
        Picasso.with(context).load(item.getImage()).into(viewHolder.ivBeverage);
        //viewHolder.categoryColor.setBackgroundColor(Color.parseColor(item.getMainColor()));
        //viewHolder.tvFooter.setBackgroundColor(Color.parseColor(item.getSecondaryColor()));
        viewHolder.ivBeverage.setColorFilter(Color.parseColor(item.getMainColor()));

        RxView.clicks(viewHolder.content).subscribe(aVoid ->{
            Intent servicesList = new Intent(context, ServicesListActivity.class);
            servicesList.putExtra(ServicesListActivity.CATEGORY_ID, item.getId());
            context.startActivity(servicesList);
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(int position, Category item){
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
        private FrameLayout categoryColor;

        public ViewHolder(View v) {
            super(v);

            tvFooter = (TextView)v.findViewById(R.id.tvItemDrinkPrice);
            content = v.findViewById(R.id.beverageMenuContent);
            ivBeverage = (ImageView)v.findViewById(R.id.ivBeverageMenu);
            categoryColor = (FrameLayout)v.findViewById(R.id.categoryColor);

        }
    }

    /*public static int calculateInSampleSize(
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
    }*/

}
