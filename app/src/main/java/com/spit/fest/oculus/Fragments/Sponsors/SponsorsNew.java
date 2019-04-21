package com.spit.fest.oculus.Fragments.Sponsors;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.spit.fest.oculus.Activities.MainActivity;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.DatabaseUtils;
import com.spit.fest.oculus.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class SponsorsNew extends Fragment {

    private static SponsorFragment sponsorFragment;

    View rootView;
    LinearLayout root;
    Map<String,Map<String,String>> sponsors = new HashMap<>();
    Map<Integer,Map<String,String>> displayOrder = new HashMap<>();
    ProgressBar progressBar ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public SponsorsNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null)
                {
                    Intent intent = new Intent(getContext(), SwipeActivity.class);
                    startActivity(intent);
                }
            }
        };

        MainActivity.fragment = getSponsorFragment();
        MainActivity.navigation.setSelectedItemId(R.id.navigation_sponsors);

        rootView = inflater.inflate(R.layout.fragment_sponsor, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = rootView.findViewById(R.id.listSponsors);
        progressBar = rootView.findViewById(R.id.sponsorsProgress) ;

//        viewSetter();
//        if(StaticSponsors.isIsDataSet()){

//            sponsors= StaticSponsors.getSponsors();
//            displayOrder=StaticSponsors.getDisplayOrder();
//            createImageViews();


//        }else{
        fetchFirebase();
//        }
//

    }


    void fetchFirebase(){
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait while we fetch data");
        progressDialog.show();
        String path = "spon2019/";
        DatabaseReference sponsorsRef = DatabaseUtils.getDatabaseReference().getDatabaseInstance().child(path);
        sponsorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                Log.i("SPON",dataSnapshot.getChildren().toString());
                for(DataSnapshot eachType : dataSnapshot.getChildren()){
                    if(Objects.requireNonNull(eachType.getKey()).equals("displayDetails")){
                        continue;
                    }

                    Map<String,String> urls = new HashMap<>();
                    for(DataSnapshot eachSponsor: eachType.getChildren()){
                        urls.put(Objects.requireNonNull(eachSponsor.getKey()),Objects.requireNonNull(eachSponsor.getValue(String.class)));

                    }


                    sponsors.put(eachType.getKey(),urls);


                }

                for(DataSnapshot displayEach : dataSnapshot.child("displayDetails").getChildren()){
                    int position = Objects.requireNonNull(Objects.requireNonNull(displayEach.child("position")).getValue(Integer.class));
                    for(DataSnapshot keys:displayEach.getChildren()){
                        if(Objects.requireNonNull(keys.getKey()).equals("position")){
                            continue;
                        }
                        Map<String,String> displayKeyCombo = new HashMap<>();
                        displayKeyCombo.put(keys.getKey(),Objects.requireNonNull(keys.getValue(String.class)));
                        displayOrder.put(position,displayKeyCombo);


                    }


                }


//                StaticSponsors.setUrls(images);
                createImageViews();
                StaticSponsors.setData(sponsors,displayOrder);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Snackbar.make(rootView,"Error Loading Data",Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchFirebase();
                    }
                }).show();
            }
        });
    }



    String[] returnRandomUrl(Map<String,String> images){
        if(images.size()==0){
            return null;
        }

        String url ;
        Random random = new Random();
        int  i = random.nextInt(images.size());

        String key  = String.valueOf(Objects.requireNonNull(images.keySet().toArray())[i]);


        url = images.get(key);
        images.remove(key);

        return new String[]{key,url};
    }


    void setEachCard(String title,Map<String,String> images){

        if (images != null)
        {
            if(images.size()==0){
                return;
            }
            View view = LayoutInflater.from(getContext()).inflate(R.layout.each_sponsor_card,root,false);
            TextView titleView = view.findViewById(R.id.sponsorType);
            titleView.setText(title);


            LinearLayout rootLinear = view.findViewById(R.id.linearSponsors);
            CardView cardView = view.findViewById(R.id.cardSponsor);

            int size = images.size();
            int sizeOfImage = 300;
            if(size<3){
                sizeOfImage=500;
            }
            Resources r = Objects.requireNonNull(getContext()).getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 8,r.getDisplayMetrics());


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,sizeOfImage,1);
            params.setMargins(px,0,px,0);


            ArrayList<ImageView> imageViews = new ArrayList<>();

            for(int i = 0;i<size;i++){

                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(params);
                imageViews.add(imageView);

//            Glide.with(getContext()).load(returnRandomUrl(images)).into(imageView);
                final String[] image = returnRandomUrl(images);
                ImageUtils.newInstance().setImage(getContext(),image[1] , imageView, null);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = getRedirect(image[0]);
//                    Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();

                        if(url ==null){
                            return;
                        }
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                    }
                });
            }

            ArrayList<LinearLayout> linearLayouts =new ArrayList<>();

            for(int i=0;i<imageViews.size();i++){
                LinearLayout linearLayout = getLinearLayout(i,linearLayouts);
                linearLayout.addView(imageViews.get(i));
            }

            for(LinearLayout linearLayout:linearLayouts){
                rootLinear.addView(linearLayout);
            }
            root.addView(cardView);
        }
    }


    void createImageViews(){


        root.removeAllViews();
        int size = displayOrder.size();

        Log.i("images received:",Integer.toString(size));
        for(int i=1;i<=size;i++){
            Map<String,String> keys = displayOrder.get(i);
            assert keys != null;
            for(String title : keys.keySet()){

                setEachCard(title,sponsors.get(keys.get(title)));

            }


        }




//        Map<String,ArrayList<String>> sortedSponsors = new TreeMap<>(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return 0;
//            }
//        });
//
//
//
//        int min = -1;
//        for(int i=0;i<size;i++)
//        {
//
//
//            String minKey = "";
//            for(String types : sponsors.keySet()){
//
//                if(min > sponsors.get(types).size()){
//                    min = sponsors.get(types).size();
//                    minKey = types;
//                }
//
//            }
//            sortedSponsors.put(minKey,sponsors.get(minKey));
//            sponsors.remove(minKey);
//        }




//        for(String types : sortedSponsors.keySet()){
//
//            setEachCard(types,sortedSponsors.get(types));
//
//        }


    }
    void addLinearLayout(ArrayList<LinearLayout> linearLayouts){
        LinearLayout addable = new LinearLayout(getContext());
        addable.setPadding(15,15,15,15);
        addable.setOrientation(LinearLayout.HORIZONTAL);
        linearLayouts.add(addable);
    }






    LinearLayout getLinearLayout(int i,ArrayList<LinearLayout> linearLayouts){

        if(linearLayouts == null){
            linearLayouts = new ArrayList<>();
        }

        int position = i%5;

        if( position ==0 || position ==2){
            addLinearLayout(linearLayouts);
        }


        return linearLayouts.get(linearLayouts.size()-1);


    }

    public static SponsorFragment getSponsorFragment() {
        if (sponsorFragment == null)
            sponsorFragment = new SponsorFragment();
        return sponsorFragment;
    }


    String getRedirect(String name){
        if(!sponsors.containsKey("urls")){
            return null;
        }

        if(!Objects.requireNonNull(sponsors.get("urls")).containsKey(name)){
            return null;
        }

        return Objects.requireNonNull(sponsors.get("urls")).get(name);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
