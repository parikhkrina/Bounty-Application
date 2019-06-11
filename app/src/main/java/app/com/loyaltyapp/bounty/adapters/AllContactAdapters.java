package app.com.loyaltyapp.bounty.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.activities.LentPoints;
import app.com.loyaltyapp.bounty.activities.SelectContactActivity;
import app.com.loyaltyapp.bounty.fragments.InviteFragment;
import app.com.loyaltyapp.bounty.models.ContactList;
import app.com.loyaltyapp.bounty.models.UserDetails;

import static android.app.Activity.RESULT_OK;

public class AllContactAdapters extends RecyclerView.Adapter<AllContactAdapters.ContactViewHolder> {


    private List<UserDetails> contactList =  new ArrayList<>();
    private Context mContext;
//    UserDetails userData;



    public AllContactAdapters(List<UserDetails> userList, Context mContext) {

        this.contactList = userList;
        this.mContext = mContext;


    }

    @NonNull
    @Override
    public AllContactAdapters.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_contact_view, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final AllContactAdapters.ContactViewHolder contactViewHolder, final int i) {

        final UserDetails  userData = contactList.get(i);

        contactViewHolder.tvContactName.setText(userData.getName());
        contactViewHolder.tvPhoneNumber.setText(userData.getPhoneNumber());

        contactViewHolder.iv_selectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("usedate_"+contactList.get(i).getName());
                System.out.println("usedate_pojo_"+userData.getName());

                Intent intent = new Intent(mContext, LentPoints.class);

                intent.putExtra("transfer_username", userData);
                mContext.startActivity(intent);


            }
        });
    }
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        ImageView ivContactImage;

        TextView tvContactName;
        TextView tvPhoneNumber;
        CheckBox iv_selectContact;

        public ContactViewHolder(View itemView) {
            super(itemView);


            ivContactImage = (ImageView) itemView.findViewById(R.id.iv_conatctImage);

            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvContactNumber);

            // ll_contactView = (RelativeLayout) itemView.findViewById(R.id.ll_contactView);

            iv_selectContact = (CheckBox) itemView.findViewById(R.id.iv_selectContact);


        }


    }




}
