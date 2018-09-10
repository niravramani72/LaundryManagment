package com.echat.arkarcy.laundry;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class addcustomerform extends AppCompatActivity {

    private EditText mcadd,mcphone,mcname;
    private Button mbsave;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcustomerform);

        mcadd = (EditText) findViewById(R.id.cadd);
        mcname = (EditText) findViewById(R.id.cname);
        mcphone = (EditText) findViewById(R.id.cphone);
        mbsave = (Button) findViewById(R.id.bsave);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        mbsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mcname.getText().toString();
                String add = mcadd.getText().toString();
                String phone = mcphone.getText().toString();
                if(name.isEmpty() || add.isEmpty() || phone.isEmpty()){
                    Toast.makeText(addcustomerform.this,"Please Enter all the details!",Toast.LENGTH_SHORT).show();
                }
                else if(phone.length() != 10) {
                    Toast.makeText(addcustomerform.this,"Please Enter 10 Digit Mobile NO",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
                    Date date = new Date();
                    final String currentmonth =formatter.format(date).toString();
                  //  mdatabase.child("Customer").child("name").setValue(name);
                    mdatabase.child("Customer").child(name+add+phone).setValue(new customer(name,add,phone));
                    mdatabase.child("Cloths").child(name+add+phone).child("totalno").setValue(0);
                    mdatabase.child("Cloths").child(name+add+phone).child("remaining").setValue(0);
                    mdatabase.child("Cloths").child(name+add+phone).child("amount").setValue(0);
                    mdatabase.child("Cloths").child(name+add+phone).child("ramount").setValue(0);
                    mdatabase.child("Cloths").child(name+add+phone).child("normal").setValue(0);
                    mdatabase.child("Cloths").child(name+add+phone).child("normalr").setValue(0);
                    mdatabase.child("Cloths").child(name+add+phone).child("heavyr").setValue(0);
                    mdatabase.child("Cloths").child(name+add+phone).child("heavy").setValue(0);

                    Toast.makeText(addcustomerform.this,"Data Saved.",Toast.LENGTH_SHORT).show();

                    mcname.setText("");
                    mcadd.setText("");
                    mcphone.setText("");

                }
            }
        });
    }
}
