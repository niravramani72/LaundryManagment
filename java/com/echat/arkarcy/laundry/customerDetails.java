package com.echat.arkarcy.laundry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class customerDetails extends AppCompatActivity {

    private RecyclerView mbloglist;
    private DatabaseReference mDatabase,mchatR;
    private FirebaseDatabase mdatabase = FirebaseDatabase.getInstance(),malldata;
    private DatabaseReference mRef = mdatabase.getReference();
    private Query mQueryReversePost;
    private static View extra;
    private static  String address="^",currentadd="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        address="^";
        mchatR = FirebaseDatabase.getInstance().getReference().child("Customer");
       // Toast.makeText(customerDetails.this,"CustomerDetails",Toast.LENGTH_SHORT).show();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customer");
        mQueryReversePost = mDatabase.orderByKey();
        mbloglist = (RecyclerView) findViewById(R.id.cust_list);
        mbloglist.setHasFixedSize(true);
        mbloglist.setLayoutManager(new LinearLayoutManager(this));
        extra = getLayoutInflater().inflate(R.layout.extra, null);
        Intent in = getIntent();
        final Bundle b = in.getExtras();
        try{

            String name = (String)b.get("searchVal");
           // Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Customer");
            mQueryReversePost = mDatabase.orderByChild("name").equalTo(name);
            if(name.equals(""))
                mQueryReversePost = mDatabase.orderByKey();
        }catch(Exception e){
           // Toast.makeText(getApplicationContext(),"No search element",Toast.LENGTH_SHORT).show();
        }
        try {
            address = (String)b.get("searchValAdd");
         //   Toast.makeText(getApplicationContext(),address,Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
       // Toast.makeText(getApplicationContext(),address,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<customer,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<customer, BlogViewHolder>(
                customer.class,
                R.layout.customerlayout,
                BlogViewHolder.class,
                mQueryReversePost
        ) {

            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, final customer model, int position) {

                currentadd=model.getAddress();
                viewHolder.setName(model.getName());
                viewHolder.setAddress(model.getAddress());
                viewHolder.setPhone(model.getPhone());


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cloths =new Intent(customerDetails.this, Clothes.class);
                        Bundle extras = new Bundle();
                        extras.putString("name",model.getName());
                        extras.putString("address",model.getAddress());
                        extras.putString("phone",model.getPhone());
                        cloths.putExtras(extras);
                        startActivity(cloths);
                    }
                });
            }
        };

        mbloglist.setAdapter(firebaseRecyclerAdapter);

    }

    private static class BlogViewHolder extends RecyclerView.ViewHolder{


        View mView;
        RecyclerView.LayoutParams param;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
        }

        public void setName(String cname){
            if(!currentadd.contains(address) && !address.equals("^")) {

            }
            else {
                TextView cText = (TextView) mView.findViewById(R.id.clname);
                cText.setText(cname);
            }
        }
        public void setAddress(String cadd){
            if(!currentadd.contains(address) && !address.equals("^")) {

            }
            else {
                TextView uText = (TextView) mView.findViewById(R.id.claddress);
                uText.setText(cadd);
            }

        }
        public void setPhone(String cphone){
            if(!currentadd.contains(address) && !address.equals("^")) {
                param.height = 0;
                param.width = 0;
            }
            else {
                TextView uText = (TextView) mView.findViewById(R.id.clphone);
                uText.setText(cphone);
            }
        }
    }




}
