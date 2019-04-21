package com.spit.fest.oculus.HelperClass;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;
import com.spit.fest.oculus.Fragments.OtherFragments.RateDialogFragment;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.DatabaseUtils;
import com.spit.fest.oculus.Utils.ImageUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EventViewHolder extends RecyclerView.ViewHolder {

    private TextView monthText;
    private TextView dateText;
    private TextView dayText;
    private TextView titleText;
    private TextView venueText;
    private TextView votesNoText;
    private TextView votesPercentText;
    private TextView unfoldTitleText;
    private RatingBar unfoldRating;
    private TextView unfoldDescText;
    private TextView unfoldVenueText;
    private TextView unfoldTimeText;
    private TextView unfoldRegFeeText;
    private TextView unfoldPrizeText;
    private TextView unfoldPerson1Text;
    private TextView unfoldPhone1Text;
    public FoldingCell foldingCell;
    private ImageView imgPhoto;
    private FirebaseAuth mAuth;
    private Button btnRate;
    private Context context;
    private Event event;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        mAuth = FirebaseAuth.getInstance();
        monthText = itemView.findViewById(R.id.month);
        dateText = itemView.findViewById(R.id.date);
        dayText = itemView.findViewById(R.id.day);
        titleText = itemView.findViewById(R.id.txtTitle);
        venueText = itemView.findViewById(R.id.venue);
        votesNoText = itemView.findViewById(R.id.votesNo);
        votesPercentText = itemView.findViewById(R.id.votesPercent);
        imgPhoto = itemView.findViewById(R.id.imgPhoto);
        unfoldTitleText = itemView.findViewById(R.id.unfoldTitle);
        unfoldRating = itemView.findViewById(R.id.unfoldRating);
        unfoldDescText = itemView.findViewById(R.id.txtDesc);
        unfoldVenueText = itemView.findViewById(R.id.unfoldTxtVenue);
        unfoldTimeText = itemView.findViewById(R.id.unfoldTxtTime);
        unfoldRegFeeText = itemView.findViewById(R.id.unfoldTxtReg);
        unfoldRegFeeText.setMovementMethod(LinkMovementMethod.getInstance());
        unfoldPrizeText = itemView.findViewById(R.id.txtPrize);
        unfoldPerson1Text = itemView.findViewById(R.id.txtPerson1);
        unfoldPerson1Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPoc();
            }
        });
        unfoldPhone1Text = itemView.findViewById(R.id.txtPhone1);
        unfoldPhone1Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPoc();
            }
        });
        foldingCell = itemView.findViewById(R.id.folding_cell);
        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foldingCell.isUnfolded())
                    Log.e("Description: ",String.valueOf(unfoldDescText.getHeight()));
                try {
                    foldingCell.toggle(false);
                }
                catch (Exception e)
                {
                    foldingCell.toggle(true);
                }
            }
        });

        Button bookBtn = itemView.findViewById(R.id.btnBook);

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForEvent();
            }
        });
        Button unfoldBookBtn = itemView.findViewById(R.id.unfoldBtnBook);
        unfoldBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForEvent();
            }
        });
        btnRate = itemView.findViewById(R.id.btnRate);
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fragment to rate an event
                if (mAuth.getCurrentUser() != null)
                {
                    DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Rating").child(event.getEmail().replace('.',',')).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(mAuth.getCurrentUser().getUid()))
                                Toast.makeText(context,"You've rated this event",Toast.LENGTH_SHORT).show();
                            else {
                                ArrayList<String> params = new ArrayList<>();
                                params.add(event.getEmail().replace('.',','));
                                params.add(mAuth.getCurrentUser().getUid());
                                params.add(event.getImageApp());
                                RateDialogFragment rateDialogFragment = RateDialogFragment.newInstance(params);
                                rateDialogFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "rate_bottomsheet");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        ImageView callPerson1 = itemView.findViewById(R.id.callPerson1);
        callPerson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPoc();
            }
        });
    }

    public void setDate(ArrayList<String> dateString)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar[] = new Calendar[dateString.size()];
        for (int i = 0 ; i<calendar.length ; i++)
        {
            calendar[i] = Calendar.getInstance();
            try {
                calendar[i].setTime(dateFormat.parse(dateString.get(i)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String dayOfWeek[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        String monthsOfYear[]= {"January","Feb","March","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String day[] = new String[dateString.size()];
        for (int i = 0 ; i<day.length ; i++)
            day[i] = dayOfWeek[calendar[i].get(Calendar.DAY_OF_WEEK)-1];
        String date[] = new String[dateString.size()];
        for (int i = 0 ; i<date.length ; i++)
            date[i] = String.valueOf(calendar[i].get(Calendar.DATE));
        String month = monthsOfYear[calendar[0].get(Calendar.MONTH)];
        monthText.setText(month);
        if (day.length == 1)
            dayText.setText(day[0]);
        else
            dayText.setText(day[0] + " / " + day[1]);
        if (date.length == 1)
            dateText.setText(date[0]);
        else
            dateText.setText(date[0] + " / " + date[1]);
    }

    public void setTitle(String title)
    {
        titleText.setText(title);
        unfoldTitleText.setText(title);
    }

    public void setVenue(String venue)
    {
        venueText.setText(venue);
        unfoldVenueText.setText(venue);
    }

    private void setRegFee(String regFee)
    {
        unfoldRegFeeText.setText(Html.fromHtml(regFee));
    }

    public void setVotesNo(int votesNo)
    {
        votesNoText.setText(String.valueOf(votesNo+" votes"));
    }

    public void setUnfoldRating(double avg)
    {
        unfoldRating.setRating((float)avg);
    }

    private void setDesc(String desc)
    {
        unfoldDescText.setText(Html.fromHtml(desc));
    }

    private void setUnfoldTime(String time)
    {
        unfoldTimeText.setText(Html.fromHtml(time));
    }

    private void setUnfoldPrize(String prize)
    {
        unfoldPrizeText.setText(prize);
    }

    private void setUnfoldPerson1(String person1)
    {
        unfoldPerson1Text.setText(person1);
    }

    private void setUnfoldPhone1(String phone1)
    {
        unfoldPhone1Text.setText(phone1);
    }

    private void setPhoto(String url)
    {
        ImageUtils.newInstance().setImageWithPlaceholder(context, url, imgPhoto, R.drawable.dfs);
    }

    private void setRateButton()
    {
        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Rating").child(event.getEmail().replace('.',',')).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mAuth.getCurrentUser() != null)
                {
                    if (dataSnapshot.hasChild(mAuth.getCurrentUser().getUid()))
                    {
                        btnRate.setText("Rated");
                        Drawable img = context.getResources().getDrawable( R.drawable.ic_favorite_black_24dp );
                        btnRate.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);
                    }
                    else
                    {
                        btnRate.setText("Rate event");
                        Drawable img = context.getResources().getDrawable( R.drawable.ic_favorite_border_black_24dp );
                        btnRate.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setVotesPercent(double avgRating)
    {
        votesPercentText.setText(String.valueOf((int)(Math.ceil(avgRating/5*100)))+"%");
    }

    private void callPoc()
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+event.getPocs().getContact_no()));
        context.startActivity(intent);
    }

    private void registerForEvent()
    {
        if (event.getEventUrl() != null)
        {
            if (!event.getEventUrl().startsWith("http"))
                Toast.makeText(context, event.getEventUrl(), Toast.LENGTH_LONG).show();
            else
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getEventUrl()));
                context.startActivity(intent);
            }
        }
    }

    public void setEvent(Event event) {
        this.event = event;

        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Event").child(event.getEmail().replace('.', ',')).child("id").setValue(getAdapterPosition());

        setDate(event.getDate());
        setTitle(event.getTitle());
        setVenue(event.getVenue());
        setRegFee(event.getRegistration_fee());
        setVotesNo(event.getPeopleRated());
        setUnfoldRating(event.getAvgRating());
        setDesc(event.getDescription());
        setUnfoldTime(event.getTiming());
        setUnfoldPrize(event.getPrizes());
        setUnfoldPerson1(event.getPocs().getName());
        setUnfoldPhone1(String.valueOf(event.getPocs().getContact_no()));
        setPhoto(event.getImageApp());
        setRateButton();
        setVotesPercent(event.getAvgRating());
    }
}
