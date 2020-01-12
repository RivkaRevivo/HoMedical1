package com.example.homedical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import io.opencensus.common.ServerStatsFieldEnums;

import static io.opencensus.common.ServerStatsFieldEnums.*;

@SuppressWarnings("deprecation")
public class MangerActivity extends AppCompatActivity {
    Button offers;
    Button tip;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    String key ="";
    static String mes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger);
        mDatabase = FirebaseDatabase.getInstance().getReference("offers");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("Medicals");
        offers = findViewById(R.id.Offer);
        tip = findViewById(R.id.tip);


        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOfferDialgo();
            }
        });

        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentipdialog();

            }
        });
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

    public void openOfferDialgo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Medicals Offers");
        Context context = this.getBaseContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextView edittext = new TextView(this);
        final TextView edittext2 = new TextView(this);
        final TextView edittext3 = new TextView(this);
        final TextView edittext4 = new TextView(this);
        edittext.setTextSize(20);
        edittext2.setTextSize(20);
        edittext3.setTextSize(20);
        edittext4.setTextSize(20);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot offer : dataSnapshot.getChildren()){
                        edittext.setText( (String)offer.child("name").getValue());
                        edittext2.setText( (String)offer.child("category").getValue());
                        edittext3.setText( (String)offer.child("problem").getValue());
                        edittext4.setText( (String)offer.child("desc").getValue());
                        key = offer.getKey() ;
                        break;
                    }
                }
                else {
                    edittext.setText( "No offers");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        layout.addView(edittext);
        layout.addView(edittext2);
        layout.addView(edittext3);
        layout.addView(edittext4);
        builder.setView(layout);

        builder.setPositiveButton("Accept medicine", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = edittext.getText().toString();
                String category = edittext2.getText().toString();
                String problem = edittext3.getText().toString();
                String desc = edittext4.getText().toString();

                Map<String, String> offer = new HashMap<>();
                offer.put("name",name);
                offer.put("problem",problem);
                offer.put("category",category);
                offer.put("desc",desc);
                mDatabase2.push().setValue(offer);
                mDatabase.child(key).removeValue();


            }
        });

        builder.setNegativeButton("Reject medicine ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mDatabase.child(key).removeValue();
                dialog.cancel();
            }
        });
        builder.show();
    }


    public void  opentipdialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        edittext.setHint("type for today");
        builder.setView(edittext);
        final Intent intent = new Intent(this, TipOfDay.class);
        builder.setPositiveButton("send tip", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
             mes =(String) edittext.getText().toString();
             //intent.putExtra("tip", mes);
             //startActivity(intent);
             //finish();

            }
        });
        builder.show();
    }
}