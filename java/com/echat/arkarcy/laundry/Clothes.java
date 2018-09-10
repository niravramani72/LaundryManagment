package com.echat.arkarcy.laundry;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyboardShortcutGroup;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Clothes extends AppCompatActivity {

    private TextView mclname,mcladdress,mclphone,mclremaining,mclamout,mclnormalr,mclheavyr,mclramount;
    private Button mbpickup,mbdeliverd,mbreceiveamount,mbshowrercord,mbdeliverdrecord;
    private DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();

    private String customerid="";
    int totalno,remaining,amount,normal,heavy,normalr,heavyr,ramount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        Intent in = getIntent();
        final Bundle b = in.getExtras();

        mclname = (TextView) findViewById(R.id.clname);
        mcladdress = (TextView) findViewById(R.id.claddress);
        mclphone = (TextView) findViewById(R.id.clphone);
        mclremaining = (TextView) findViewById(R.id.clremaining);
        mbpickup = (Button) findViewById(R.id.bpickup);
        mbdeliverd = (Button) findViewById(R.id.bdeliverd);
        mclamout =(TextView) findViewById(R.id.clamount);
        mclnormalr =(TextView) findViewById(R.id.clnormalr);
        mclheavyr = (TextView) findViewById(R.id.clheavyr);
        mbreceiveamount = (Button) findViewById(R.id.breceiveamount);
        mclramount = (TextView) findViewById(R.id.clramount);
        mbshowrercord = (Button) findViewById(R.id.bshowrecord);
        mbdeliverdrecord = (Button) findViewById(R.id.bdeliverdrecord);
        customerid = (String)b.get("name")+(String)b.get("address")+(String)b.get("phone");

        mclname.setText((String)b.get("name"));
        mcladdress.setText((String)b.get("address"));
        mclphone.setText((String)b.get("phone"));

        SimpleDateFormat form = new SimpleDateFormat("MM-yyyy");
        Date date = new Date();
        final String cm =form.format(date).toString();

        mDatabase.child("income").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(cm))
                {
                    mDatabase.child("income").child(cm).child("monthincome").setValue(0);
                    mDatabase.child("income").child(cm).child("month").setValue(cm);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        SimpleDateFormat form1 = new SimpleDateFormat("yyyy");
        final String cm1 =form1.format(date).toString();

        mDatabase.child("yincome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(cm1))
                {
                    mDatabase.child("yincome").child(cm1).child("monthincome").setValue(0);
                    mDatabase.child("yincome").child(cm1).child("month").setValue(cm1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.child("Cloths").child(customerid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    clothDetail cd = dataSnapshot.getValue(clothDetail.class);
                    //totalno = cd.getTotalno();

                    mclamout.setText(String.valueOf(cd.getAmount()));
                    mclremaining.setText(String.valueOf(cd.getRemaining()));
                    mclnormalr.setText(String.valueOf(cd.getNormalr()));
                    mclheavyr.setText(String.valueOf(cd.getHeavyr()));
                    mclramount.setText(String.valueOf(cd.getRamount()));
                }
                catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mbshowrercord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clothes.this,showRecord.class);
                Bundle b = new Bundle();
                b.putString("customerid",customerid);
                b.putString("type","pickupDetail");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        mbdeliverdrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clothes.this,showRecord.class);
                Bundle b = new Bundle();
                b.putString("customerid",customerid);
                b.putString("type","delevierdDetail");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        mbreceiveamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] y = {0},x={0},x1={0};
                LayoutInflater inflater = LayoutInflater.from(Clothes.this);
                final View subView = inflater.inflate(R.layout.dialog_receive, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Clothes.this);
                builder.setView(subView);
                final EditText receivedam = (EditText) subView.findViewById(R.id.receivedam);
                SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
                Date date = new Date();
                final String currentmonth =formatter.format(date).toString();

                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
                final String currentyear =formatter1.format(date).toString();
                builder.setMessage("Enter Amount Paid by Customer: ").setTitle("received");

                builder.setPositiveButton("received", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mDatabase.child("income").child(currentmonth).child("monthincome").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                long amo = (long)dataSnapshot.getValue();

                                String am =  receivedam.getText().toString();

                                final int raamount = Integer.parseInt(am);
                                amo = raamount + amo;
                                if(x[0]==0) {
                                    x[0]=1;
                                    mDatabase.child("income").child(currentmonth).child("monthincome").setValue(amo);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        mDatabase.child("yincome").child(currentyear).child("monthincome").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                long amo = (long)dataSnapshot.getValue();

                                String am =  receivedam.getText().toString();

                                final int raamount = Integer.parseInt(am);
                                amo = raamount + amo;
                                if(x1[0]==0) {
                                    x1[0]=1;
                                    mDatabase.child("yincome").child(currentyear).child("monthincome").setValue(amo);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        mDatabase.child("Cloths").child(customerid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                clothDetail cd = dataSnapshot.getValue(clothDetail.class);
                                amount = cd.getAmount();
                                ramount = cd.getRamount();
                                String am =  receivedam.getText().toString();

                                final int raamount = Integer.parseInt(am);
                                amount = amount -raamount;
                                ramount = ramount+raamount;//ramount=received amount
                                if(y[0] ==0) {
                                    y[0] = 1;
                                    mDatabase.child("Cloths").child(customerid).child("amount").setValue(amount);
                                    mDatabase.child("Cloths").child(customerid).child("ramount").setValue(ramount);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

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

        mbpickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] y = {0};
                LayoutInflater inflater = LayoutInflater.from(Clothes.this);
                final View subView = inflater.inflate(R.layout.dialog_pickup, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Clothes.this);
                builder.setView(subView);
                final EditText enopic = (EditText) subView.findViewById(R.id.nopic);
                final EditText enopic2 = (EditText) subView.findViewById(R.id.nopic2);

                //date

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                Date date = new Date();
                //System.out.println(formatter.format(date));
                final String currentdate =formatter.format(date).toString();
                builder.setMessage(formatter.format(date).toString()+"\nEnter No of Cloths: ").setTitle("PickUP");

                builder.setPositiveButton("Pickup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mDatabase.child("Cloths").child(customerid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                clothDetail cd = dataSnapshot.getValue(clothDetail.class);
                                totalno = cd.getTotalno();
                                amount = cd.getAmount();
                                remaining = cd.getRemaining();
                                normal= cd.getNormal();
                                normalr = cd.getNormalr();
                                heavy = cd.getHeavy();
                                heavyr = cd.getHeavyr();

                                String pick =  enopic.getText().toString();
                                String pick2 =  enopic2.getText().toString();
                                if(pick.equals(""))
                                    pick="0";
                                if(pick2.equals(""))
                                    pick2="0";
                                final int noofpick = Integer.parseInt(pick);
                                final int noofpick2 = Integer.parseInt(pick2);
                                remaining = remaining + noofpick + noofpick2;
                                totalno = totalno+ noofpick + noofpick2;
                                normalr = normalr + noofpick;
                                heavyr = heavyr + noofpick2;
                                normal = normal + noofpick;
                                heavy = heavy + noofpick2;
                                int tpickup = noofpick+ noofpick2;
                                int pickupamount= (noofpick*5) + (noofpick2*10);

                                if(y[0] ==0) {
                                    y[0] = 1;
                                    mDatabase.child("Cloths").child(customerid).child("remaining").setValue(remaining);
                                    mDatabase.child("Cloths").child(customerid).child("totalno").setValue(totalno);
                                    mDatabase.child("Cloths").child(customerid).child("normalr").setValue(normalr);
                                    mDatabase.child("Cloths").child(customerid).child("heavyr").setValue(heavyr);
                                    mDatabase.child("Cloths").child(customerid).child("normal").setValue(normal);
                                    mDatabase.child("Cloths").child(customerid).child("heavy").setValue(heavy);
                                }
                                /*mDatabase.child("ClothPickupDetail").child(customerid).child(currentdate).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        pickupDetails pud =dataSnapshot.getValue(pickupDetails.class);
                                        String normal = pud.getNormalcloth();
                                        String heavy  = pud.getHeavycloth();
                                      //  String
                                        mDatabase.child("ClothPickupDetail").child(customerid).child(currentdate).child("normalcloth").setValue(String.valueOf(noofpick));
                                        mDatabase.child("ClothPickupDetail").child(customerid).child(currentdate).child("heavycloth").setValue(String.valueOf(noofpick2));
                                        mDatabase.child("ClothPickupDetail").child(customerid).child(currentdate).child("pickupamount").setValue(String.valueOf(pickupamount));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });*/
                                if(!pick.equals("0") || !pick2.equals("0")) {
                                    mDatabase.child("ClothPickupDetail").child(customerid).child(currentdate).child("date").setValue(currentdate);
                                    mDatabase.child("ClothPickupDetail").child(customerid).child(currentdate).child("total").setValue(String.valueOf(tpickup));
                                    mDatabase.child("ClothPickupDetail").child(customerid).child(currentdate).child("normalcloth").setValue(String.valueOf(noofpick));
                                    mDatabase.child("ClothPickupDetail").child(customerid).child(currentdate).child("heavycloth").setValue(String.valueOf(noofpick2));
                                    mDatabase.child("ClothPickupDetail").child(customerid).child(currentdate).child("pickupamount").setValue(String.valueOf(pickupamount));
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"No of Cloths not Entered",Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

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


        mbdeliverd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] y = {0};
                LayoutInflater inflater = LayoutInflater.from(Clothes.this);
                final View subView = inflater.inflate(R.layout.dialog_deliverd, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Clothes.this);
                builder.setView(subView);
                final EditText enodeliverd = (EditText) subView.findViewById(R.id.nodeliverd);
                final EditText enodeliverd2 = (EditText) subView.findViewById(R.id.nodeliverd2);
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                Date date = new Date();
                final String currentdate =formatter.format(date).toString();
                builder.setMessage(currentdate+"\nEnter No of Cloths: ").setTitle("Deliver");

                builder.setPositiveButton("Deliver", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mDatabase.child("Cloths").child(customerid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                clothDetail cd = dataSnapshot.getValue(clothDetail.class);
                                totalno = cd.getTotalno();
                                amount = cd.getAmount();
                                remaining = cd.getRemaining();
                                normal= cd.getNormal();
                                normalr = cd.getNormalr();
                                heavy = cd.getHeavy();
                                heavyr = cd.getHeavyr();
                                String deli =  enodeliverd.getText().toString();
                                if(deli.equals(""))
                                    deli="0";
                                final int noofdeli = Integer.parseInt(deli);
                                String deli2 =  enodeliverd2.getText().toString();
                                if(deli2.equals(""))
                                    deli2="0";
                                final int noofdeli2 = Integer.parseInt(deli2);

                                remaining = remaining - noofdeli -noofdeli2;
                                amount = amount +(noofdeli*5) +(noofdeli2*10);
                                int tdeli = noofdeli + noofdeli2;

                                normalr = normalr - noofdeli;
                                heavyr = heavyr - noofdeli2;
                                if(y[0] ==0) {
                                    y[0] = 1;
                                    mDatabase.child("Cloths").child(customerid).child("remaining").setValue(remaining);
                                    mDatabase.child("Cloths").child(customerid).child("amount").setValue(amount);
                                    mDatabase.child("Cloths").child(customerid).child("normalr").setValue(normalr);
                                    mDatabase.child("Cloths").child(customerid).child("heavyr").setValue(heavyr);
                                }
                                if(!deli2.equals("0") || !deli.equals("0")) {
                                    mDatabase.child("ClothDeliverDetail").child(customerid).child(currentdate).child("date").setValue(currentdate);
                                    mDatabase.child("ClothDeliverDetail").child(customerid).child(currentdate).child("normalcloth").setValue(String.valueOf(noofdeli));
                                    mDatabase.child("ClothDeliverDetail").child(customerid).child(currentdate).child("heavycloth").setValue(String.valueOf(noofdeli2));
                                    mDatabase.child("ClothDeliverDetail").child(customerid).child(currentdate).child("total").setValue(String.valueOf(tdeli));
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"No of Cloths not Entered",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



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
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_clothes, menu);


        return super.onCreateOptionsMenu(menu);
    }

    public void onGroupItemClick(MenuItem item) {

        if (item.getItemId() == R.id.removecutomer) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Clothes.this);

            builder.setMessage("Are You Sure you want to remove customer?").setTitle("Remove");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                        mDatabase.child("Customer").child(customerid).removeValue();
                        mDatabase.child("Cloths").child(customerid).removeValue();
                        finish();
                        startActivity(new Intent(getApplicationContext(), customerDetails.class));
                        finish();


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
    }



}
