package com.example.homedical;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerView_Config {

    private Context mContext;
    private MedicalAdapter mMedicalAdapter;
    public void setConfig (RecyclerView recyclerView, Context context, List<Medical> medicals, List<String> keys ){
        mContext = context;
        mMedicalAdapter = new MedicalAdapter(medicals,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mMedicalAdapter);
    }

    class MedicalitemView extends RecyclerView.ViewHolder {
        private TextView mProblem;
        private TextView mName;
        private Button get;
        private Button add;
        private String key;
        private DatabaseReference mDatabase;

        public MedicalitemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.medical_list_item,parent ,false));
            mProblem = (TextView) itemView.findViewById(R.id.problem_textview);
            mName = (TextView) itemView.findViewById(R.id.name_textview);
            get = (Button)itemView.findViewById(R.id.Button);
            add = (Button)itemView.findViewById(R.id.button_add);
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

        public void bind(final Medical medical, String key) {
            mProblem.setText(medical.getProblem());
            mName.setText(medical.getName());
            this.key = key;

            get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GetDesc.class);
                    intent.putExtra("desc", medical.getDesc());
                    //  intent.putExtra("key", key);
                    mContext.startActivity(intent);
                }
            });
            {
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String u = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        mDatabase.child("users").child(u).child("favorites").push().setValue(medical.getKey());

                    }
                });
        }
        }
    }

    class MedicalAdapter extends RecyclerView.Adapter<MedicalitemView >{
        private List<Medical> mMedicalList;
        private List<String> keys;

        public MedicalAdapter(List<Medical> mMedicalList, List<String> keys) {
            this.mMedicalList = mMedicalList;
            this.keys = keys;
        }

        @NonNull
        @Override
        public MedicalitemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MedicalitemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MedicalitemView holder, int position) {
            holder.bind(mMedicalList.get(position), keys.get(position));
        }

        @Override
        public int getItemCount() {
            return mMedicalList.size();
        }


    }
}
