package com.example.homedical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import io.opencensus.common.ServerStatsFieldEnums;

import static io.opencensus.common.ServerStatsFieldEnums.*;

@SuppressWarnings("deprecation")
public class MangerActivity extends AppCompatActivity {
    Button notification;

    private static  final  String CHANNEL_ID = "personal_notifications";
    private static  final  String CHANNEL_NAME = "simple";
    private static  final  String CHANNEL_DESC = "simple_notifications";
   // private final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        notification = findViewById(R.id.buttonaddnot);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              displeyNotification();
            }
        });
    }
    private  void  displeyNotification(){
        Intent intent = new Intent(this, TipOfDay.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_star_notific)
                .setContentTitle("HoMedical")
                .setContentText("There is new tip! Hurry up")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,mbuilder.build());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manu_maneger , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item3:
                Toast.makeText(this , "signOut successful  " , Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this , Login.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
