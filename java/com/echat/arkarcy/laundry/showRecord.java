package com.echat.arkarcy.laundry;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class showRecord extends AppCompatActivity {

    private RecyclerView mpicklist;
    private Query mQuery;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private String customerid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_record);
        Intent in = getIntent();
        final Bundle b = in.getExtras();
        try {
            customerid = (String) b.get("customerid");
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),customerid,Toast.LENGTH_LONG).show();
        }
        if(((String)b.get("type")).equals("pickupDetail"))
        mQuery = mDatabase.child("ClothPickupDetail").child(customerid).orderByKey();
        if(((String)b.get("type")).equals("delevierdDetail"))
            mQuery = mDatabase.child("ClothDeliverDetail").child(customerid).orderByKey();
        mpicklist = (RecyclerView) findViewById(R.id.pickup_list);
        mpicklist.setHasFixedSize(true);
        mpicklist.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(getApplicationContext(),"Shown",Toast.LENGTH_SHORT).show();
       final FirebaseRecyclerAdapter<pickupDetails,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<pickupDetails, BlogViewHolder>(
               pickupDetails.class,
               R.layout.pickupdata,
               BlogViewHolder.class,
               mQuery
       ) {
           @Override
           protected void populateViewHolder(BlogViewHolder viewHolder, pickupDetails model, int position) {
               viewHolder.setdate(model.getDate());
               viewHolder.setnoramal(model.getNormalcloth());
               viewHolder.setheavy(model.getHeavycloth());
               viewHolder.settotal(model.getTotal());
           }
       };
        mpicklist.setAdapter(firebaseRecyclerAdapter);
       /*
        final FirebaseRecyclerAdapter<pickupDetails,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<pickupDetails,BlogViewHolder>(
                pickupDetails.class,
                R.layout.pickupdata,
                BlogViewHolder.class,
                mQuery
        ) {

            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, final pickupDetails model, int position) {
                viewHolder.setdate(model.getDate());
                viewHolder.setnoramal(model.getNormalcloth());
                viewHolder.setheavy(model.getHeavycloth());

            }
        };

        mpicklist.setAdapter(firebaseRecyclerAdapter);*/

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setnoramal(String normal) {
            Button btn = (Button) mView.findViewById(R.id.bnormal);
            btn.setText(normal);
        }
        public void setheavy(String heavy) {
            Button btn = (Button) mView.findViewById(R.id.bheavy);
            btn.setText(heavy);
        }
        public void setdate(String date) {
            Button btn = (Button) mView.findViewById(R.id.bdate);
            btn.setText(date);
        }

        public void settotal(String total) {
            Button btn = (Button) mView.findViewById(R.id.btotal);
            btn.setText(total);
        }
    }

    /*public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setnoramal(String normal) {
            Button btn = (Button) mView.findViewById(R.id.bnormal);
            btn.setText(normal);
        }
        public void setheavy(String heavy) {
            Button btn = (Button) mView.findViewById(R.id.bheavy);
            btn.setText(heavy);
        }
        public void setdate(String date) {
            Button btn = (Button) mView.findViewById(R.id.bdate);
            btn.setText(date);
        }
    }*/



}
