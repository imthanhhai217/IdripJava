package com.jaroid.demoretrofit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jaroid.demoretrofit.R;
import com.jaroid.demoretrofit.interfaces.WishListChangeListener;
import com.jaroid.demoretrofit.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> mListData;
    private Context mContext;
    private WishListChangeListener wishListChangeListener;

    public ProductAdapter(List<Product> listData) {
        this.mListData = listData;
    }

    public void setData(List<Product> listData) {
        this.mListData = listData;
        notifyDataSetChanged();
    }

    public void setWishListChangeListener(WishListChangeListener wishListChangeListener) {
        this.wishListChangeListener = wishListChangeListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListData.get(position);

        Glide.with(mContext).load(product.getImages().get(0)).into(holder.imgProduct);
        holder.tvProductName.setText(product.getTitle());
        holder.tvPrice.setText(product.getPrice() + "");
        holder.tvRating.setText(product.getRating() + "");
        if (product.isWishList()) {
            holder.imgWishList
                    .setBackground(mContext
                            .getResources()
                            .getDrawable(R.drawable.ic_wishlist_selected, null));
        } else {
            holder.imgWishList
                    .setBackground(mContext
                            .getResources()
                            .getDrawable(R.drawable.ic_wishlist_unselected, null));

        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgProduct, imgWishList, imgStar;
        TextView tvProductName, tvPrice, tvRating;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            imgWishList = itemView.findViewById(R.id.imgWishList);
            imgStar = itemView.findViewById(R.id.imgStar);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRating = itemView.findViewById(R.id.tvRating);

            imgWishList.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgWishList:
                    int position = getAdapterPosition();
                    if (mListData.get(position).isWishList()) {
                        wishListChangeListener.onRemoveWishList(position);
                    } else {
                        wishListChangeListener.onAddWishList(position);
                    }
                    break;
            }
        }
    }
}
