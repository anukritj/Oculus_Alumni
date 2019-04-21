package com.spit.fest.oculus.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.spit.fest.oculus.Activities.LoginActivity;
import com.spit.fest.oculus.Activities.MainActivity;
import com.spit.fest.oculus.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FcmService extends FirebaseMessagingService {

    static final String channelId = "mainNotificationChannel";
    boolean hasPublishedNoti = false;
    //4 cultural
    //3 technical
    // 1 literary
    //2 fun

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("FCM rec",remoteMessage.getData().toString());
        Map<String,String> data  = remoteMessage.getData();
        createOreoChannel();

        //no time consuming task

        createNotification(data);


        if(data.containsKey("clearCache")){
            cacheClear();
        }







//        Toast.makeText(this, "FCM received "+remoteMessage.getMessageId().toString() , Toast.LENGTH_SHORT).show();
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void cacheClear() {

        try {
            File dir = this.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createNotification(final Map<String, String> data) {
        if(!data.containsKey("title")){
            return;
        }



        final NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this,channelId)
                .setColor(Color.MAGENTA)
                .setSmallIcon(R.drawable.ic_stat_name).setContentTitle(data.get("title"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                ;
        notiBuilder.setAutoCancel(true);


        Intent i = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,0);
        notiBuilder.setContentIntent(pendingIntent);


        final NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        if(data.containsKey("event")){
            linkEvent(data.get("event"),notiBuilder);
        }

        if(data.containsKey("desc")){
            notiBuilder.setContentText(data.get("desc"));
        }

//        Random r = new Random();
        final int notificationId =15;
//        if(data.containsKey("notificationId")){//is throwing some error
//            try {
//                notificationId = Integer.parseInt(data.get("notificationId"));
//            }catch (Exception e){
//
//            }
//        }

        if(data.containsKey("image")){
            //start service to load image

            Glide.with(this).asBitmap().load(data.get("image")).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    managerCompat.notify(notificationId,notiBuilder.build());

                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                    notiBuilder.setLargeIcon(resource);
                    notiBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(resource).setSummaryText(data.get("desc")));
                    managerCompat.notify(notificationId,notiBuilder.build());

                    return true;
                }
            }).submit();

        }else{

            if(data.containsKey("desc")){
                notiBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(data.get("desc")));
            }

            managerCompat.notify(notificationId,notiBuilder.build());


        }



        Log.i("FCM","Tried creating noti");



    }

    private void linkEvent(String event, NotificationCompat.Builder notiBuilder) {
        String eventDetails[] = event.split("/");
        if(eventDetails.length !=2){
            return;
        }



        Intent toEvent = new Intent(this, MainActivity.class);
//        toEvent.putExtra("Count",3);

//        eventDetails[0] = eventDetails[0].toLowerCase();
//
//        switch (eventDetails[0]){
//
//            case "cultural":
//                toEvent.putExtra("Count",4);
//
//                break;
//
//            case "fun":
//                toEvent.putExtra("Count",2);
//
//                break;
//
//            case "technical":
//                toEvent.putExtra("Count",3);
//
//
//                break;
//            case "literary":
//                toEvent.putExtra("Count",1);
//
//                break;
//            default:
//                return;
//
//        }

        toEvent.putExtra("fromFcm",true);
        toEvent.putExtra("event",eventDetails[1]);
        ArrayList<String> events = new ArrayList<>();
        events.add(eventDetails[0]);
        events.add(eventDetails[1]);

        toEvent.putStringArrayListExtra("paramsTimeline",events);

        toEvent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


        PendingIntent pendingIntent = PendingIntent
                .getActivity(this,0,toEvent,PendingIntent.FLAG_CANCEL_CURRENT);
        notiBuilder.addAction(0,"Visit Event",pendingIntent);


    }


    private void createOreoChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Oculus Updates";
            String description = "This chanel provides notification for upcoming events so you dont miss out on any fun";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.i("FCM rec","Message deleted");

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i("FCM token ",s);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
