package app.com.loyaltyapp.bounty.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.models.UserDetails;

public class CardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private UserDetails currentUser;
    private String mParam1;
    private String mParam2;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    String cardNumber;
    TextView tv,tvpoints;

    ImageView iv;
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    private OnFragmentInteractionListener mListener;

    public CardFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CardFragment(UserDetails user){
        this.currentUser = user;
    }

    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        // Inflate the layout for this fragment
        // barcode data

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        String uid = mFirebaseAuth.getUid();


        System.out.println("Fragment -- CardNumber ----> "+getCardNumber());

        iv = view.findViewById(R.id.card_image);

//        String barcode_data = currentUser.getCardNumber();

        // barcode image

//        ImageView iv = view.findViewById(R.id.card_image);
        tv = view.findViewById(R.id.card_number);
        tvpoints = view.findViewById(R.id.points);

//        try {
//            bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.CODE_128, 600, 300);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        iv.setImageBitmap(bitmap);
//
//        //barcode text
//        TextView tv = view.findViewById(R.id.card_number);
//        tv.setGravity(Gravity.CENTER_HORIZONTAL);
////        tv.setText(barcode_data);

        loadData(uid);
        return view;
    }

        private static final int WHITE = 0xFFFFFFFF;
        private static final int BLACK = 0xFF000000;

        Bitmap encodeAsBitmap (String contents, BarcodeFormat format,int img_width, int img_height) throws
        WriterException {
            String contentsToEncode = contents;
            if (contentsToEncode == null) {
                return null;
            }
            Map<EncodeHintType, Object> hints = null;
            String encoding = guessAppropriateEncoding(contentsToEncode);
            if (encoding != null) {
                hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
                hints.put(EncodeHintType.CHARACTER_SET, encoding);
            }
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix result;
            try {
                result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
            } catch (IllegalArgumentException iae) {
                // Unsupported format
                return null;
            }
            int width = result.getWidth();
            int height = result.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;

        }

        private static String guessAppropriateEncoding (CharSequence contents){
            // Very crude at the moment
            for (int i = 0; i < contents.length(); i++) {
                if (contents.charAt(i) > 0xFF) {
                    return "UTF-8";
                }
            }
            return null;
        }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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
                        currentUser = snap.getValue(UserDetails.class);
                        setCardNumber(currentUser.getCardNumber());
                        String points  = currentUser.getPoints();
                        System.out.println("Card Fragment cardNumber -----> "+getCardNumber());
                        System.out.println("currentUser -----> "+currentUser.toString());
                        System.out.println("Points ---> "+ points);
                        Bitmap bitmap;


                        try {
                            bitmap = encodeAsBitmap(getCardNumber(), BarcodeFormat.CODE_128, 600, 300);
                            iv.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }


                        //barcode text

                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        tvpoints.setGravity(Gravity.CENTER_HORIZONTAL);
                        tv.setText(getCardNumber());
                        tvpoints.setText(points + " Points");
//                        tv_userPoints.setText(points);
//                        tv_userEmail.setText(userData.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
