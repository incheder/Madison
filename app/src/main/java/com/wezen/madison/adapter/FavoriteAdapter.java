package com.wezen.madison.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wezen.madison.R;
import com.wezen.madison.com.wezen.madison.fragment.AddressFragment;
import com.wezen.madison.model.FavoriteItem;
import com.wezen.madison.model.ItemForCheckout;
import com.wezen.madison.model.ShoppingCartItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eder on 15/5/15.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FavoriteItem> mDataset;
    private Context context;
    private FragmentManager fm;

    public FavoriteAdapter(ArrayList<FavoriteItem> mDataset, Context context, FragmentManager fm){
        this.mDataset = mDataset;
        this.context = context;
        this.fm = fm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = null;
        RecyclerView.ViewHolder vh = null;
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favorites,parent,false);
                vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final FavoriteItem item = mDataset.get(position);

            ((ViewHolder)viewHolder).name.setText(item.getFavName());
            ((ViewHolder)viewHolder).price.setText(item.getFavPrice());
            ((ViewHolder)viewHolder).content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // remove(item);
                }
            });
            ((ViewHolder)viewHolder).image.setImageBitmap(decodeSampledBitmapFromResource(
                    context.getResources(),
                    item.getFavImage(),
                    80,
                    100
            ));



    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(int position, FavoriteItem item){
        mDataset.add(position,item);
        notifyItemInserted(position);
    }



    public void remove(ItemForCheckout item){
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView price;
        public ImageView image;
        public LinearLayout content;

        public ViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.tvFavoriteName);
            price = (TextView)v.findViewById(R.id.tvFavoritePrice);
            image = (ImageView)v.findViewById(R.id.ivFavoriteIcon);
            content = (LinearLayout)v.findViewById(R.id.favContent);


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
