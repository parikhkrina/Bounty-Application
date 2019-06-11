package app.com.loyaltyapp.bounty.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.models.OffersModel;
import app.com.loyaltyapp.bounty.models.UserDetails;
import app.com.loyaltyapp.bounty.viewholder.FragmentViewHolder;

public class EarnFragment extends Fragment {

    private UserDetails currentUser = new UserDetails();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private DatabaseReference mFirebaseReference;
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    FirebaseRecyclerAdapter mFirebaseAdapter;
    public EarnFragment() {
    }

    @SuppressLint("ValidFragment")
    public EarnFragment(UserDetails user) {
        this.currentUser = user;
    }

    @SuppressWarnings("unused")
    public static EarnFragment newInstance(int columnCount) {
        EarnFragment fragment = new EarnFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_earn_list, container, false);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFirebaseReference = FirebaseDatabase.getInstance().getReference().child("EarnOffers");
        mFirebaseReference.keepSynced(true);

        SnapshotParser<OffersModel> parser = new SnapshotParser<OffersModel>() {
            @Override
            public OffersModel parseSnapshot(DataSnapshot dataSnapshot) {
                OffersModel offersModel = dataSnapshot.getValue(OffersModel.class);
                return offersModel;
            }
        };

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<OffersModel>()
                .setQuery(mFirebaseReference,parser).build();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<OffersModel, FragmentViewHolder>(options) {

            @NonNull
            @Override
            public FragmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.card_view,viewGroup,false);
                FragmentViewHolder viewHolder = new FragmentViewHolder(view);

                return viewHolder;
            }

            @Override
            protected void onBindViewHolder( final FragmentViewHolder holder, int position, OffersModel model) {
                holder.textViewOfferId.setText(model.getOfferName());
                holder.textViewOfferDesc.setText(model.getOfferDesc());
                Picasso.with(getContext()).load(model.getOfferImage()).into(holder.offerImageView);
            }

        };

        recyclerView.setAdapter(mFirebaseAdapter);

        return view;

    }

    @Override
    public void onStart()
    {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(OffersModel item);
    }



}
