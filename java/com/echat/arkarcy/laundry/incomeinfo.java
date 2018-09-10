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

public class incomeinfo extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private RecyclerView mincomeList;
    private Query mQuery;
    private Button mbyearly,mbmonth;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomeinfo);
        name="^";

        // Toast.makeText(customerDetails.this,"CustomerDetails",Toast.LENGTH_SHORT).show();



        mincomeList = (RecyclerView) findViewById(R.id.income_list);
        mincomeList.setHasFixedSize(true);
        mincomeList.setLayoutManager(new LinearLayoutManager(this));
        Intent in = getIntent();
        final Bundle b = in.getExtras();
        try{
            mbmonth = (Button) findViewById(R.id.bmonth1);
            name = (String)b.get("yearly");
            if(name.equals("yearly")) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("yincome");
                mbmonth.setText("Year");
            }
            else {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("income");
                mbmonth.setText("month");
            }
            mQuery = mDatabase.orderByKey();


            Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"monthly",Toast.LENGTH_LONG).show();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("income");
            mQuery = mDatabase.orderByKey();
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<income,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<income,BlogViewHolder>(
                income.class,
                R.layout.incomeinfo,
                BlogViewHolder.class,
                mQuery
        ) {

            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, income model, int position) {
                viewHolder.setMonth(model.getMonth());
                viewHolder.setIncome(model.getMonthincome());

            }

        };

        mincomeList.setAdapter(firebaseRecyclerAdapter);

    }

    private static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setMonth(String month) {
            Button uText = (Button) mView.findViewById(R.id.bmonth);
            uText.setText(month);
        }

        public void setIncome(long income) {
            Button uText = (Button) mView.findViewById(R.id.bincome);
            uText.setText(String.valueOf(income));
        }
    }



}
