package app.com.loyaltyapp.bounty.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.loyaltyapp.bounty.R;

public class FragmentViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewOfferId;
        public TextView textViewOfferDesc;
        public ImageView offerImageView;
        public FragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOfferId = itemView.findViewById(R.id.offer_id);
            textViewOfferDesc = itemView.findViewById(R.id.offer_desc);
            offerImageView = itemView.findViewById(R.id.offer_image);
        }

}
