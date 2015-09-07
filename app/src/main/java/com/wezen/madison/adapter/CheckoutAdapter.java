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
import android.widget.TextView;

import com.wezen.madison.R;
import com.wezen.madison.fragment.AddressFragment;
import com.wezen.madison.model.ItemForCheckout;
import com.wezen.madison.model.ShoppingCartItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eder on 5/5/15.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemForCheckout> mDataset;
    private Context context;
    private FragmentManager fm;

    public CheckoutAdapter(ArrayList<ItemForCheckout> mDataset, Context context, FragmentManager fm){
        this.mDataset = mDataset;
        this.context = context;
        this.fm = fm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = null;
        RecyclerView.ViewHolder vh = null;
        switch (ShoppingCartItemType.valueOf(i)){
            case NORMAL_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_checkout,parent,false);
                vh = new ViewHolder(v);
                break;
            case TOTAL:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_total_checkout,parent,false);
                vh = new ViewHolderTotal(v);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemForCheckout item = mDataset.get(position);
        if(item.getType() == ShoppingCartItemType.NORMAL_ITEM){
            ((ViewHolder)viewHolder).name.setText(item.getBeverage().getName());
            ((ViewHolder)viewHolder).amount.setText(context.getResources().getString(R.string.amount) +" "+ String.valueOf(item.getAmount()));
            ((ViewHolder)viewHolder).price.setText(item.getBeverage().getPrice());
            ((ViewHolder)viewHolder).addOrDelete.setImageResource(R.drawable.ic_content_add);
            ((ViewHolder)viewHolder).addOrDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(item);
                }
            });
            ((ViewHolder)viewHolder).beverage.setImageBitmap(decodeSampledBitmapFromResource(
                    context.getResources(),
                    item.getBeverage().getImageResId(),
                    80,
                    100
        ));

        }else if(item.getType() == ShoppingCartItemType.TOTAL){
            ((ViewHolderTotal)viewHolder).total.setText(String.valueOf(item.getTotal()));
            ((ViewHolderTotal)viewHolder).continuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // OrderDialogFragment dialog = new OrderDialogFragment();
                    //dialog.show(fm,null);
                    android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, AddressFragment.newInstance("", ""));
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(int position, ItemForCheckout item){
        mDataset.add(position,item);
        notifyItemInserted(position);
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        switch (mDataset.get(position).getType()){
            case NORMAL_ITEM:
                break;
            case TOTAL:
                type = 1;
                break;
        }
        return type;
    }

    public void remove(ItemForCheckout item){
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView amount;
        public TextView price;
        public ImageButton addOrDelete;
        public ImageView beverage;

        public ViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.tvCheckoutName);
            amount = (TextView)v.findViewById(R.id.tvCheckoutAmount);
            price = (TextView)v.findViewById(R.id.tvCheckoutPrice);
            addOrDelete = (ImageButton)v.findViewById(R.id.ibCheckoutAddOrDelete);
            beverage = (ImageView)v.findViewById(R.id.ivCheckoutBeverage);


        }
    }

    public class ViewHolderTotal extends RecyclerView.ViewHolder{

        public TextView total;
        public Button continuar;

        public ViewHolderTotal(View itemView) {
            super(itemView);
            total = (TextView) itemView.findViewById(R.id.tvCheckoutTotal);
            continuar = (Button) itemView.findViewById(R.id.btnCheckoutTotal);
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
