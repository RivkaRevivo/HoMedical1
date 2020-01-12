package com.example.homedical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference mDatabase;
    Button head;
    Button belly;
    Button back;
    Button throat;
    Button eyes;

    public static  final  String CHANNEL_ID = "personal_notifications";
    private static  final  String CHANNEL_NAME = "simple";
    private static  final  String CHANNEL_DESC = "simple_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        head = (Button)findViewById(R.id.head);
        belly = (Button)findViewById(R.id.belly);
        back = (Button)findViewById(R.id.back);
        throat = (Button)findViewById(R.id.t);
        eyes = (Button)findViewById(R.id.eyes);
        mDatabase = FirebaseDatabase.getInstance().getReference("offers");
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Head.class));

            }
        });
        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),eyes.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Back.class));
            }
        });
        belly.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Belly.class));
            }
        });
        throat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Throat.class));
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successfull";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
//

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item3:
                Toast.makeText(this , "signOut successful  " , Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this , Login.class));
                return true;
            case R.id.item2:
                Toast.makeText(this , "Please suggest medicine to the system  " , Toast.LENGTH_SHORT).show();
                openDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type details for your medicine");

        Context context = this.getBaseContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText edittext = new EditText(this);
        final EditText edittext2 = new EditText(this);
        final EditText edittext3 = new EditText(this);
        final Spinner spinner = new Spinner(this);
        edittext.setHint("medicine name");
        edittext2.setHint("medicine problem");
        edittext3.setHint("medicne description");


        List<String> list = new ArrayList<String>();
        list.add("1: head");
        list.add("2: eyes");
        list.add("3: back");
        list.add("4: throat");
        list.add("5: belly");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        layout.addView(edittext);
        layout.addView(edittext2);
        layout.addView(spinner);
        layout.addView(edittext3);

        builder.setView(layout);
        builder.setPositiveButton("send medicine", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = edittext.getText().toString();
                String problem = edittext2.getText().toString();
                String desc = edittext3.getText().toString();
                String category = String.valueOf(spinner.getSelectedItem()).substring(0,1);


                Map<String, String> offer = new HashMap<>();
                offer.put("name",name);
                offer.put("problem",problem);
                offer.put("category",category);
                offer.put("desc",desc);
                mDatabase.push().setValue(offer);
            }
        });


        builder.setNegativeButton("close ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String userID = fAuth.getCurrentUser().getUid();
            if (userID.equals("QbTt0dIE0tN2myTl9A5GhLomd6f2") ||userID.equals("LLNOpi2gQgXSIiT2yfAVdTY9BIH2") ){
                startActivity(new Intent(MainActivity.this, MangerActivity.class));
            }
        } else {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }

}
