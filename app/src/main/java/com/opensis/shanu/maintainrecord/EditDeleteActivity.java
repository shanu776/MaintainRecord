package com.opensis.shanu.maintainrecord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDeleteActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHandler db;
    EditText editname;
    EditText editquantity;
    EditText editprice;
    Button update;
    Button delete;
    String fid;
    Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);
        db = new DatabaseHandler(this);
        editname = (EditText) findViewById(R.id.editpname);
        editquantity = (EditText) findViewById(R.id.editpquantity);
        editprice = (EditText) findViewById(R.id.editpprice);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        Intent in=getIntent();
        fid = in.getStringExtra("firebaseid");
        Log.d("product Info",fid);
        ProductBean product = db.getProduct(fid);
        id = product.getId();
        editname.setText(product.getProduct_name());
        editquantity.setText(product.getProduct_quantity());
        editprice.setText(product.getProduct_price());
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        Log.d("product Info",product.toString());
    }

    @Override
    public void onClick(View v) {
        String pname = editname.getText().toString();
        String pquantity = editquantity.getText().toString();
        String pprice = editprice.getText().toString();
        if(v.getId()== R.id.update){
            db.updateProduct(new ProductBean(id,fid,pname,pquantity,pprice));
            startActivity(new Intent(EditDeleteActivity.this,ViewActivity.class));
            overridePendingTransition(R.anim.enter,R.anim.exit);
            finish();
        }
        if(v.getId() == R.id.update){

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
