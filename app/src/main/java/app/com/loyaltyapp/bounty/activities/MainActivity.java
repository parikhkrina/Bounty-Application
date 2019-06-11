package app.com.loyaltyapp.bounty.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.adapters.AllContactAdapters;
import app.com.loyaltyapp.bounty.adapters.CategoryMenuAdapter;
import app.com.loyaltyapp.bounty.fragments.CardFragment;
import app.com.loyaltyapp.bounty.fragments.EarnFragment;
import app.com.loyaltyapp.bounty.fragments.FeedbackFragment;
import app.com.loyaltyapp.bounty.fragments.InviteFragment;
import app.com.loyaltyapp.bounty.fragments.PrivacyFragment;
import app.com.loyaltyapp.bounty.fragments.ProfileFragment;
import app.com.loyaltyapp.bounty.fragments.RedeemFragment;
import app.com.loyaltyapp.bounty.fragments.ShareAppFragment;
import app.com.loyaltyapp.bounty.fragments.TermsConditionFragment;
import app.com.loyaltyapp.bounty.fragments.TransferFragment;
import app.com.loyaltyapp.bounty.interfaceListeners.onMenuItemClickInterface;
import app.com.loyaltyapp.bounty.models.MenuModel;
import app.com.loyaltyapp.bounty.models.OffersModel;
import app.com.loyaltyapp.bounty.models.UserDetails;
import app.com.loyaltyapp.bounty.utils.ComicTextView;
import app.com.loyaltyapp.bounty.utils.SharedPreferenceHelper;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;

public class MainActivity extends AppCompatActivity implements onMenuItemClickInterface,
        CardFragment.OnFragmentInteractionListener,
        RedeemFragment.RedeemFragmentInteractionListener,
        EarnFragment.OnListFragmentInteractionListener {


    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    RecyclerView recyclerView_menu;
    CategoryMenuAdapter categoryMenuAdapter;
    ArrayList<MenuModel> menuModelsList;
    Context mContext;
    LinearLayoutManager linearLayoutManager;

    List<UserDetails> userDetails = new ArrayList<>();
    onMenuItemClickInterface clickInterface;
    UserDetails currentUser;

    ComicTextView tv_name, tv_userEmail;

    UserDetails userData = new UserDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_card);
    }

    private void init() {

        mContext = this;
        clickInterface = this;
        menuModelsList = new ArrayList<>();


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(), "user_id",null);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        tv_name = (ComicTextView) findViewById(R.id.user_name);
        tv_userEmail = (ComicTextView) findViewById(R.id.textView);


        recyclerView_menu = (RecyclerView) findViewById(R.id.nav_drawer_recycler_view);

        String uid = mFirebaseAuth.getCurrentUser().getUid();

         loadData(uid);

//        System.out.println("init function ----> "+userData.toString());

//        tv_name.setText(userData.getName());
//        tv_userEmail.setText(userData.getEmail());
//        String points  =  userData.getPoints();


        // linearLayoutManager = new LinearLayoutManager(menuModelsList,mContext);

        menuModelsList.add(new MenuModel(getString(R.string.nav_item_profile)));
        menuModelsList.add(new MenuModel(getString(R.string.nav_item_transfer)));
        menuModelsList.add(new MenuModel(getString(R.string.nav_item_share)));
        menuModelsList.add(new MenuModel(getString(R.string.nav_item_privacy)));
        menuModelsList.add(new MenuModel(getString(R.string.nav_item_feedback)));
        menuModelsList.add(new MenuModel(getString(R.string.nav_item_term)));
        menuModelsList.add(new MenuModel(getString(R.string.nav_item_logout)));

        categoryMenuAdapter = new CategoryMenuAdapter(menuModelsList, mContext, clickInterface);
        //chatContextAdapter = new ChatContextAdapter(mContext,list_of_subject);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView_menu.setLayoutManager(linearLayoutManager);
        recyclerView_menu.setAdapter(categoryMenuAdapter);
        //  navigationView.setNavigationItemSelectedListener(this);


        DividerItemDecoration itemDecor = new DividerItemDecoration(mContext, HORIZONTAL);
        recyclerView_menu.addItemDecoration(itemDecor);
        categoryMenuAdapter.setClickListener(this);

    }

    private void loadData(final String uid) {
//        String cardNumber;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DataSnapshot threadSnap = dataSnapshot.child("Customers");

                Iterable<DataSnapshot> threadDetails = threadSnap.getChildren();

                System.out.println("ThreadDetails -----> "+threadDetails.toString());


                for (DataSnapshot snap : threadDetails) {

                    //String uid  = snap.getKey();

                    System.out.println("UID -----> "+uid);
                    //currentUser = snap.getValue(UserDetails.class);


                    if(snap.getKey().equals(uid)) {
                        userData = snap.getValue(UserDetails.class);
                        String cardNumber = userData.getCardNumber();
                        System.out.println("cardNumber -----> "+cardNumber);
                        System.out.println("currentUser -----> "+userData.toString());
                        tv_name.setText(userData.getName());
                        tv_userEmail.setText(userData.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fragments = getSupportFragmentManager();

        if (fragments.getBackStackEntryCount() > 1) {
            //  fragments.popBackStack(null,fragments.);

            if (!fragments.popBackStackImmediate()) {
                finish();
            }

        } else {
            // supportFinishAfterTransition();
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();

            }
        }


    }


    // interface for navigation drawer menu
    @Override
    public void oninterfaceClick(View view, int position) {

        Fragment fragment = null;


        //creating fragment object
        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction ft = fragments.beginTransaction();
        fragments.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.commit();


        // onclick of recyclerview item open fragment

        switch (position) {
            case 0:
                fragment = new ProfileFragment();
                break;
            case 1:
                if(userData!=null)
                fragment = new TransferFragment();
                break;
            case 2:
                fragment = new ShareAppFragment();
                break;
            case 3:
                fragment = new PrivacyFragment();
                break;
            case 4:
                fragment = new FeedbackFragment();
                break;
            case 5:
                fragment = new TermsConditionFragment();
                break;
            case 6:
                logout();
                break;

            default:
                break;
        }


        if (fragment != null) {

            final FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();

            // put the fragment in place
            transaction.replace(R.id.content_frame, fragment);

            // this is the part that will cause a fragment to be added to backstack,
            // this way we can return to it at any time using this tag
            transaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }

    private void logout() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        FirebaseAuth.getInstance().signOut();
                        SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(), "user_id", "");
                        SharedPreferences settings = getApplicationContext().getSharedPreferences("user_id", Context.MODE_PRIVATE);
                        settings.edit().remove("user_id").commit();
                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    @Override
    public void RedeemFragmentInteraction(OffersModel item) {
        Toast.makeText(this, "Item: " + item.getOfferId() + ", " + item.getOfferDesc(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteraction(OffersModel item) {
        Toast.makeText(this, "Item: " + item.getOfferId() + ", " + item.getOfferDesc(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_redeem:
                    RedeemFragment redeemFragment = new RedeemFragment(userData);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, redeemFragment, "redeem_fragment")
                            .commit();
                    return true;
                case R.id.navigation_earn:
                    EarnFragment earnFragment = new EarnFragment(userData);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, earnFragment, "earn_fragment")
                            .commit();
                    return true;
                case R.id.navigation_card:
                    CardFragment cardFragment = new CardFragment(userData);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, cardFragment, "card_fragment")
                            .commit();
                    return true;

            }
            return false;
        }
    };


}

