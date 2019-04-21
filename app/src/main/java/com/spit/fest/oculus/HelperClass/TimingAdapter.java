package com.spit.fest.oculus.HelperClass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spit.fest.oculus.Fragments.BottomNavigationFragments.EventFragment;
import com.spit.fest.oculus.R;

import java.util.ArrayList;

class TimingAdapter extends RecyclerView.Adapter<TimingAdapter.TimingViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Timing> timings;
    private Context context;

    TimingAdapter(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        timings = new ArrayList<>();
    }

    void addEventsToTimeline(ArrayList<Timing> timing)
    {
        timings.clear();
        timings.addAll(timing);
    }

    @NonNull
    @Override
    public TimingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TimingViewHolder(layoutInflater.inflate(R.layout.item_event_timeline, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimingViewHolder timingViewHolder, int i) {
        Timing timing = timings.get(i);
        timingViewHolder.setTitle(timing.getTitle());
        timingViewHolder.setId(timing.getId());
        timingViewHolder.setTiming(timing.getTiming());
        timingViewHolder.setCategory(timing.getCategory());
    }

    @Override
    public int getItemCount() {
        return timings.size();
    }

    class TimingViewHolder extends RecyclerView.ViewHolder
    {

        private TextView titleText;
        private TextView timing;
        private long id;
        private String category;
        private FragmentManager fragmentManager;

        TimingViewHolder(@NonNull View itemView) {
            super(itemView);
            fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> params = new ArrayList<>();
                    params.add(category);
                    params.add(String.valueOf(id));
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("paramsTimeline",params);
                    Fragment fragment = new EventFragment();
                    fragment.setArguments(bundle);
                    replaceFragment(fragment);
                }
            });
            titleText = itemView.findViewById(R.id.event_name_textview);
            timing = itemView.findViewById(R.id.event_timing_textview);
        }

        public void setId(long id)
        {
            this.id = id;
        }

        void setTiming(String timing)
        {
            this.timing.setText(timing);
        }

        void setCategory(String category) {
            this.category = category;
        }

        private void replaceFragment(Fragment fragment)
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.navigation_fragment_container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commitAllowingStateLoss();
        }

        public void setTitle(String title) {
            this.titleText.setText(title);
        }
    }

}
