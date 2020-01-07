package com.example.homedical;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabasehelper {

    private FirebaseDatabase mdatabase;
    private DatabaseReference mReferenceMedicals;
    private List<Medical> medicals = new ArrayList<>();

    public interface DataStatus {
        void DataisLoaded (List<Medical> medicals , List <String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabasehelper() {
        mdatabase = FirebaseDatabase.getInstance();
        mReferenceMedicals = mdatabase.getReference("Medicals");
    }
    public void getMedical (final DataStatus dataStatus){
        mReferenceMedicals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicals.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keysmedic : dataSnapshot.getChildren()){
                    keys.add(keysmedic.getKey());
                    Medical medical = keysmedic.getValue(Medical.class);
                    medicals.add(medical);
                }
                dataStatus.DataisLoaded(medicals,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
