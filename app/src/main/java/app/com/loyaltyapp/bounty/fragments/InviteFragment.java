package app.com.loyaltyapp.bounty.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.adapters.AllContactAdapters;
import app.com.loyaltyapp.bounty.models.ContactList;
import app.com.loyaltyapp.bounty.models.UserDetails;


public class InviteFragment extends Fragment {


    Context mContext;
    Toolbar toolbar;
    ProgressBar progressBar;
    RecyclerView rv_contact;
    EditText et_searchConatct;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    Button btnRef;
    FloatingActionButton fb_sendConatct;
    private AllContactAdapters mAdapter;
    ImageButton ib_closeRequest;
    LinearLayoutManager layoutManager;


    List<UserDetails> contactVOList = new ArrayList();
    UserDetails userDetails;

    ContactList getSelecedObject;
    DatabaseReference databaseReference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_invite, container, false);
        //  return inflater.inflate(R.layout.fragment_invite, container, false);


        mContext = getActivity();

        rv_contact = (RecyclerView) view.findViewById(R.id.rvContacts);
        progressBar = (ProgressBar) view.findViewById(R.id.p_bar);


        progressBar.setBackgroundColor(getResources().getColor(R.color.white));
        // Read and show the contacts
        showContacts();

        return view;
    }


    @SuppressLint("StaticFieldLeak")
    private void showContacts() {


        //  permission check
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected void onPreExecute() {

                    progressBar.setVisibility(View.VISIBLE);
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... voids) {

                    progressBar.setVisibility(View.VISIBLE);
                    // get Contacts from the application
                    getUsersFromApp();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    progressBar.setVisibility(View.GONE);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rv_contact.setLayoutManager(layoutManager);
                    mAdapter = new AllContactAdapters(contactVOList, getActivity());
                    rv_contact.setAdapter(mAdapter);

                }
            }.execute();

        }
    }

    private void getUsersFromApp() {


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DataSnapshot threadSnap = dataSnapshot.child("Customers");

                Iterable<DataSnapshot> threadDetails = threadSnap.getChildren();
                for (DataSnapshot snap : threadDetails) {
                    //for (DataSnapshot locationItem : dataSnapshot.getChildren()) {
                    String loc_id = snap.getKey();
                    userDetails = snap.getValue(UserDetails.class);
//                    lolocationData.getLocation_id();
                    //  userDetails.setId(loc_id);
                    contactVOList.add(userDetails);
                    Log.d("list size-->", contactVOList.size() + "");
                }

                mAdapter = new AllContactAdapters(contactVOList, mContext);
                layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                rv_contact.setLayoutManager(layoutManager);
                rv_contact.setAdapter(mAdapter);


                //rv_contact.addItemDecoration(new EqualSpacingItemDecoration(20,EqualSpacingItemDecoration.VERTICAL));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    private void getAllContacts() {
//
//
//        ContentResolver contentResolver = mContext.getContentResolver();
//        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//
//                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
//                if (hasPhoneNumber > 0) {
//                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//                    contactVO = new ContactList();
//                    contactVO.setContactName(name);
//
//                    Cursor phoneCursor = contentResolver.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{id},
//                            null);
//                    if (phoneCursor.moveToNext()) {
//                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        contactVO.setContactNumber(phoneNumber);
//                    }
//
//                    if(phoneCursor.moveToNext()){
//                        String image_uri = phoneCursor
//                                .getString(phoneCursor
//                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
//
//                        System.out.println("Contact1 : " + phoneCursor + ", Number "
//                                + ", image_uri " + image_uri);
//
//
//
//                            contactVO.setContactImage(image_uri);
//
//
//                    }
//
//
//                    phoneCursor.close();
//
//
//
//
//
//                    contactVOList.add(contactVO);
//                }
//            }
//
//
//        }
//
//
//
//
//
//    }


}
