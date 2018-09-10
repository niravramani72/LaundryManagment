package com.echat.arkarcy.laundry;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button mbaddcustomer,mbcustomers,mbfind,mbincome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbaddcustomer = (Button) findViewById(R.id.baddcustomer);
        mbcustomers = (Button) findViewById(R.id.bcustomers);
        mbfind = (Button) findViewById(R.id.bfind);
        mbincome = (Button) findViewById(R.id.bincome);

        mbaddcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addc = new Intent(getApplicationContext(),addcustomerform.class);
                startActivity(addc);
            }
        });

        mbcustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addc = new Intent(getApplicationContext(),customerDetails.class);
                startActivity(addc);
            }
        });

        mbfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View subView = inflater.inflate(R.layout.dialog_search, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(subView);

                final EditText searchname = (EditText) subView.findViewById(R.id.editsearchname);
                final EditText searchaddress = (EditText) subView.findViewById(R.id.editsearchaddress);
                builder.setTitle("Enter name");

                builder.setPositiveButton("Find", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String sear = searchname.getText().toString();
                        String searadd = searchaddress.getText().toString();
                        Intent custd =new Intent(MainActivity.this, customerDetails.class);
                        Bundle extras = new Bundle();
                        extras.putString("searchVal",sear);
                        extras.putString("searchValAdd",searadd);
                        custd.putExtras(extras);
                        startActivity(custd);

                    }


                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        mbincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View subView = inflater.inflate(R.layout.dialog_income, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(subView);

                final Button monthly = (Button) subView.findViewById(R.id.button2);
                final Button yearly = (Button) subView.findViewById(R.id.button3);
                monthly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(MainActivity.this, incomeinfo.class);
                        Bundle extras = new Bundle();
                        extras.putString("monthly","monthly");
                        in.putExtras(extras);
                        startActivity(in);
                    }
                });
                yearly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(MainActivity.this, incomeinfo.class);
                        Bundle extras = new Bundle();
                        extras.putString("yearly","yearly");
                        in.putExtras(extras);
                        startActivity(in);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
}
