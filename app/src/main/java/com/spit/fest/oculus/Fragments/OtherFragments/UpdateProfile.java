//package com.spit.fest.oculus.Fragments.BottomNavigationFragments;
//
//
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.ValueEventListener;
//import com.spit.fest.oculus.Activities.MainActivity;
//import com.spit.fest.oculus.Activities.SwipeActivity;
//import com.spit.fest.oculus.HelperClass.Profile;
//import com.spit.fest.oculus.R;
//import com.spit.fest.oculus.Utils.DatabaseUtils;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class UpdateProfile extends DialogFragment {
//    private ProgressDialog progressDialog;
//    private Profile currentProfile;
//    private FirebaseAuth mAuth;
//    private EditText contact, age, college, degree;
//    private TextView name;
//
//    private boolean updateForceFully = false;
//
//    public UpdateProfile() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setStyle(android.app.DialogFragment.STYLE_NO_FRAME,R.style.Dialog);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Dialog dialog = getDialog();
//        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        mAuth = FirebaseAuth.getInstance();
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_update_profile, new FrameLayout(Objects.requireNonNull(getActivity())), false);
//        startProgress();
//        Button cancel = view.findViewById(R.id.cancelUpdate);
//        Button update = view.findViewById(R.id.updateProfile);
//
//        if (mAuth.getCurrentUser() != null)
//        {
//            name = view.findViewById(R.id.nameProfile);
//            name.setText(mAuth.getCurrentUser().getDisplayName());
//            contact = view.findViewById(R.id.contactProfile);
//            age = view.findViewById(R.id.ageProfile);
//            college = view.findViewById(R.id.collegeProfile);
//            degree = view.findViewById(R.id.degreeProfile);
//            retrieveProfile();
//        }
//
//        if(updateForceFully){
//            cancel.setEnabled(false);
//        }
//
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateProfile();
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                stopProgress();
//            }
//        });
//        Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(view);
//        return dialog;
//    }
//
//    void startProgress(){
//        if(progressDialog == null)
//        {
//            progressDialog = new ProgressDialog(getContext());
//        }
//        progressDialog.setTitle("Loading Profile");
//        progressDialog.setMessage("Please wait while we contact servers");
//        progressDialog.setIndeterminate(false);
//        progressDialog.show();
//
//    }
//    void stopProgress(){
//        if(progressDialog == null){
//            return;
//        }
//
//        progressDialog.dismiss();
//
//    }
//    void  retrieveProfile(){
//        String path = "Users/"+Objects.requireNonNull(mAuth.getCurrentUser()).getUid()+"/";
//        Log.e("UpdateProfile",path);
//        DatabaseReference reference = DatabaseUtils.getDatabaseReference().getDatabaseInstance().child(path);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try{
//                    currentProfile = dataSnapshot.getValue(Profile.class);
//                }catch (Exception e){
//                    currentProfile = null;
//                }
//                setUi();
//                stopProgress();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    void setUi(){
//        if(currentProfile == null){
//            return;
//        }
////        name.setText(currentProfile.getName());
//        contact.setText(String.valueOf(currentProfile.getContact_no()));
//        age.setText(String.valueOf(currentProfile.getAge()));
//        college.setText(currentProfile.getCollege());
//        degree.setText(currentProfile.getDegree());
//    }
//
//    void updateProfile(){
//        if(TextUtils.isEmpty(contact.getText().toString())){
//            contact.setError("Please enter your Contact no.");
//            return;
//        }
//        if(TextUtils.isEmpty(college.getText().toString())){
//            college.setError("Please enter your College name");
//            return;
//        }
//
//        if(TextUtils.isEmpty(age.getText().toString())){
//            age.setError("Please enter your age");
//            return;
//        }
//
//        if(TextUtils.isEmpty(degree.getText().toString())){
//            degree.setError("Please enter your degree/stream");
//            return;
//        }
//
//
//        Map<String,Object> updates = new HashMap<>();
//        try
//        {
//            if (!(contact.getText().toString().length() >= 10 && contact.getText().toString().length() <= 11))
//                throw new Exception();
//            if (!(Integer.parseInt(age.getText().toString()) > 0 && Integer.parseInt(age.getText().toString()) < 100))
//                throw new Exception();
//            updates.put("age", Integer.parseInt(age.getText().toString()));
//            updates.put("name",name.getText().toString());
//            updates.put("college",college.getText().toString());
//            updates.put("contact_no",Long.parseLong(contact.getText().toString()));
//            updates.put("degree", degree.getText().toString());
//            updates.put("email",Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail()));
//            String path = "Users/"+mAuth.getCurrentUser().getUid()+"/";
//            DatabaseReference reference = DatabaseUtils.getDatabaseReference().getDatabaseInstance().child(path);
//            startProgress();
//            reference.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//
//                    if(updateForceFully){
//                        SharedPreferences.Editor shared = Objects.requireNonNull(getContext()).getSharedPreferences("userData",Context.MODE_PRIVATE).edit();
//                        shared.putBoolean("loggedIn",true).apply();
//                        if (Build.VERSION.SDK_INT < 23)
//                            startActivity(new Intent(getActivity(),MainActivity.class));
//                        else
//                            startActivity(new Intent(getActivity(),SwipeActivity.class));
//                    }
//
//                    stopProgress();
//
//                    Toast.makeText(getContext(), "Updated Profile Successfully", Toast.LENGTH_SHORT).show();
//                    dismiss();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    stopProgress();
//                    Toast.makeText(getContext(), "Error Updating Profile", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        }
//        catch (Exception e)
//        {
//            if (progressDialog != null && progressDialog.isShowing())
//                stopProgress();
//            Toast.makeText(getContext(), "Enter valid data", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    public void setUpdateForceFully(boolean updateForceFully) {
//        this.updateForceFully = updateForceFully;
//    }
//}
package com.spit.fest.oculus.Fragments.OtherFragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.HelperClass.Profile;
import com.spit.fest.oculus.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProfile extends DialogFragment {
    private ProgressDialog progressDialog;
    private Profile currentProfile;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText contact, age, college, degree;
    private TextView name;

    private boolean updateForceFully = false;

    public UpdateProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(android.app.DialogFragment.STYLE_NO_FRAME,R.style.Dialog);

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

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        Dialog dialog = getDialog();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        retrieveProfile();
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startProgress();
        Button cancel = view.findViewById(R.id.cancelUpdate);
        Button update = view.findViewById(R.id.updateProfile);

        name = view.findViewById(R.id.nameProfile);
        name.setText(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
        contact = view.findViewById(R.id.contactProfile);
        age = view.findViewById(R.id.ageProfile);
        college = view.findViewById(R.id.collegeProfile);
        degree = view.findViewById(R.id.degreeProfile);

        if(updateForceFully){
            cancel.setEnabled(false);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                stopProgress();
            }
        });
    }

    void startProgress(){
        if(progressDialog == null)
        {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setTitle("Loading Profile");
        progressDialog.setMessage("Please wait while we contact servers");
        progressDialog.setIndeterminate(false);
        progressDialog.show();

    }
    void stopProgress(){
        if(progressDialog == null){
            return;
        }

        progressDialog.dismiss();

    }
    void retrieveProfile(){
        String path = "Users/"+Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()+"/";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    currentProfile = dataSnapshot.getValue(Profile.class);
                }catch (Exception e){
                    currentProfile = null;
                }
                setUi();
                stopProgress();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void setUi(){
        if(currentProfile == null){
            return;
        }
//        name.setText(currentProfile.getName());
        contact.setText(String.valueOf(currentProfile.getContact_no()));
        age.setText(String.valueOf(currentProfile.getAge()));
        college.setText(currentProfile.getCollege());
        degree.setText(currentProfile.getDegree());
    }

    void updateProfile(){
        if(TextUtils.isEmpty(contact.getText().toString())){
            contact.setError("Please enter your Contact no.");
            return;
        }
        if(TextUtils.isEmpty(college.getText().toString())){
            college.setError("Please enter your College name");
            return;
        }

        if(TextUtils.isEmpty(age.getText().toString())){
            age.setError("Please enter your age");
            return;
        }

        if(TextUtils.isEmpty(degree.getText().toString())){
            degree.setError("Please enter your degree/stream");
            return;
        }


        Map<String,Object> updates = new HashMap<>();
        try
        {
            if (!(contact.getText().toString().length() >= 10 && contact.getText().toString().length() <= 11))
                throw new Exception();
            if (!(Integer.parseInt(age.getText().toString()) > 0 && Integer.parseInt(age.getText().toString()) < 100))
                throw new Exception();
            updates.put("age", Integer.parseInt(age.getText().toString()));
            updates.put("name",name.getText().toString());
            updates.put("college",college.getText().toString());
            updates.put("contact_no",Long.parseLong(contact.getText().toString()));
            updates.put("degree", degree.getText().toString());
            updates.put("email",Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()));
            String path = "Users/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/";
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
            startProgress();
            reference.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    if(updateForceFully){
                        SharedPreferences.Editor shared = Objects.requireNonNull(getContext()).getSharedPreferences("userData",Context.MODE_PRIVATE).edit();
                        shared.putBoolean("loggedIn",true).apply();
                        startActivity(new Intent(Objects.requireNonNull(getActivity()),SwipeActivity.class));
                    }

                    stopProgress();

                    Toast.makeText(Objects.requireNonNull(getContext()), "Updated Profile Successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    stopProgress();
                    Toast.makeText(Objects.requireNonNull(getContext()), "Error Updating Profile", Toast.LENGTH_SHORT).show();

                }
            });
        }
        catch (Exception e)
        {
            if (progressDialog != null && progressDialog.isShowing())
                stopProgress();
            Toast.makeText(Objects.requireNonNull(getContext()), "Enter valid data", Toast.LENGTH_LONG).show();
        }
    }

    public void setUpdateForceFully(boolean updateForceFully) {
        this.updateForceFully = updateForceFully;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("UpdateProfile","onPause()");
        InputMethodManager inputManager = (InputMethodManager) Objects.requireNonNull(getActivity())
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null)
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
