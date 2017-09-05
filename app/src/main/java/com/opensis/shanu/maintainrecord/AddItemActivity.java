package com.opensis.shanu.maintainrecord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Shanu on 8/22/2017.
 */

public class AddItemActivity extends AppCompatActivity implements View .OnClickListener{
    EditText pname;
    EditText pquantity;
    EditText pprice;
    Button saveproduct;
    Button showview;
    DatabaseHandler db;
    boolean doubleBackToExitPressedOnce=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additem);
        db = new DatabaseHandler(this);
        pname = (EditText) findViewById(R.id.editpname);
        pquantity = (EditText) findViewById(R.id.editpquantity);
        pprice = (EditText) findViewById(R.id.editpprice);
        saveproduct = (Button) findViewById(R.id.update);
        showview = (Button) findViewById(R.id.showview);
        saveproduct.setOnClickListener(this);
        showview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddItemActivity.this,ViewActivity.class));
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
    }

    @Override
    public void onClick(View v) {
        String pname = this.pname.getText().toString();
        String pquantity = this.pquantity.getText().toString();
        String pprice = this.pprice.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("ProductDatabase");
        saveDataFirebase(pname,pquantity,pprice,ref);

        Toast.makeText(AddItemActivity.this,"done...",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddItemActivity.this,AddItemActivity.class));

            finish();
    }


    public void saveDataFirebase(String pname,String pquantity,String pprice,DatabaseReference ref){
         String s=ref.child("product").push().getKey();
            ref.child("product").child(s).child("Id").setValue(s);
            ref.child("product").child(s).child("Name").setValue(pname);
            ref.child("product").child(s).child("Quantity").setValue(pquantity);
            ref.child("product").child(s).child("Price").setValue(pprice);
         db.addProduct(new ProductBean(s,pname,pquantity,pprice));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
