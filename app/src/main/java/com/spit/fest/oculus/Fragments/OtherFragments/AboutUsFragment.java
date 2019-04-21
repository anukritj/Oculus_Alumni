package com.spit.fest.oculus.Fragments.OtherFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.joooonho.SelectableRoundedImageView;
import com.spit.fest.oculus.Activities.MainActivity;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.HelperClass.Committee;
import com.spit.fest.oculus.HelperClass.CommitteeAdapter;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.ImageUtils;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    private static AboutUsFragment aboutUsFragment;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public AboutUsFragment() {
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

        MainActivity.fragment = getAboutUsFragment();

        return inflater.inflate(R.layout.about_us_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
//        MapView mapView = view.findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume(); // needed to get the map to display immediately
//
//        try {
//            MapsInitializer.initialize(getContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                MarkerOptions markerOptions = new MarkerOptions();
//                LatLng latLng = new LatLng(19.123208,72.83611);
//                markerOptions.position(latLng);
//                markerOptions.title("Sardar Patel Institue of Technology");
//                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
//                googleMap.addMarker(markerOptions);
//                googleMap.getUiSettings().setAllGesturesEnabled(true);
//                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
//                googleMap.setMinZoomPreference(50f);
//                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//            }
//        });

        (view.findViewById(R.id.aboutUsInstagram)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/oculus2k19?utm_source=ig_profile_share&igshid=1e20dow5slxfj"));
                startActivity(intent);
            }
        });

        (view.findViewById(R.id.aboutUsFB)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/profile.php?id=271451559717087&ref=content_filter"));
                startActivity(intent);
            }
        });

        (view.findViewById(R.id.aboutUsIn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/oculusseesall/"));
                startActivity(intent);
            }
        });

        (view.findViewById(R.id.aboutUsTwitter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mobile.twitter.com/OculusSeesAll"));
                startActivity(intent);
            }
        });

        (view.findViewById(R.id.aboutUsYoutube)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCm5gXAKIwUQTuGngoGlM4pg"));
                startActivity(intent);
            }
        });

        (view.findViewById(R.id.aboutUsPhone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + ((TextView) (view.findViewById(R.id.aboutUsPhone))).getText().toString()));
                startActivity(intent);
            }
        });

        (view.findViewById(R.id.aboutUsWebsite)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView) (view.findViewById(R.id.aboutUsWebsite))).getText().toString()));
                startActivity(intent);
            }
        });

        (view.findViewById(R.id.aboutUsEmail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + ((TextView) (view.findViewById(R.id.aboutUsEmail))).getText().toString()));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

        (view.findViewById(R.id.aboutUsAddress)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:19.123208,72.83611?z=10&q=Bharatiya+Vidya+Bhavan's+Sardar+Patel+Institute+Of+Technology(label)"));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        SelectableRoundedImageView developers = view.findViewById(R.id.tech_head);
        ImageUtils.newInstance().setImage(getContext(),"https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Ftech_heads-min.jpg?alt=media&token=0a0f5547-7ecb-465b-a678-b41d33316160", developers, null);

        SelectableRoundedImageView cpvcp = view.findViewById(R.id.cpvcp);
        ImageUtils.newInstance().setImage(getContext(),"https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Fcpvcp1-min.jpg?alt=media&token=91408f08-e884-45ad-a8f1-76d1960dee3b", cpvcp, null);

        RecyclerView recyclerView1 = view.findViewById(R.id.developerRecyclerView);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setNestedScrollingEnabled(false);
        CommitteeAdapter committeeAdapter1 = new CommitteeAdapter(getContext());
        committeeAdapter1.addCommitteeMember(new Committee("https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Faashay-min.jpg?alt=media&token=0b0ad704-a26c-4865-bf58-1ef8257060eb","Aashay Jain","Lead Android Developer"));
        committeeAdapter1.addCommitteeMember(new Committee("https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Faditya-min.jpg?alt=media&token=8c1e2463-744c-461b-b89d-d678d11b2ad1","Aditya Malani","Lead Android Developer"));
        committeeAdapter1.addCommitteeMember(new Committee("https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Fmahesh-min.jpg?alt=media&token=aca5877d-7968-4ba2-a2c1-6d377ff3e7f8","Mahesh Tamse","Android Developer"));
        recyclerView1.setAdapter(committeeAdapter1);
        RecyclerView recyclerView2 = view.findViewById(R.id.cpvcpRecyclerView);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setNestedScrollingEnabled(false);
        CommitteeAdapter committeeAdapter2 = new CommitteeAdapter(getContext());
        committeeAdapter2.addCommitteeMember(new Committee("https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Fanukrit-min.jpeg?alt=media&token=ecfa6736-6e5c-4683-9c92-a49ca4a59ec5","Anukrit Jain","Chairperson"));
        committeeAdapter2.addCommitteeMember(new Committee("https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Fazain-min.jpeg?alt=media&token=cf756394-4d36-4ae8-b605-144eb5a305f5","Azain Jaffer","Vice Chairperson"));
        committeeAdapter2.addCommitteeMember(new Committee("https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Fahmed-min.jpeg?alt=media&token=0120c010-11d0-4989-827a-6550987ae59a","Ahmed Shaikh","Vice Chairperson"));
        committeeAdapter2.addCommitteeMember(new Committee("https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Fanushree-min.jpeg?alt=media&token=fcb33f28-b4c5-4437-9d1c-fcd4d2803aa8","Anushree Kulai","Vice Chairperson"));
        committeeAdapter2.addCommitteeMember(new Committee("https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Committee%2FApp%2Fvaibhav-min.jpeg?alt=media&token=f7200448-9777-41d8-b6f5-1db687e28fa5","Vaibhav Bagri","Vice Chairperson"));
        recyclerView2.setAdapter(committeeAdapter2);
    }

    public static AboutUsFragment getAboutUsFragment() {
        if (aboutUsFragment == null)
            aboutUsFragment = new AboutUsFragment();
        return aboutUsFragment;
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
