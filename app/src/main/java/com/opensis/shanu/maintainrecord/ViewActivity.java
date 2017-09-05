package com.opensis.shanu.maintainrecord;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    ListView listView;
    DatabaseHandler db;
    SwipeRefreshLayout swipeRefreshLayout;
    String[] id;
    String[] pname;
    String[] pprice;
    String[] pquantity;
    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;
    enum Direction {LEFT, RIGHT;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.synchdatabase);
        swipeRefreshLayout.setOnRefreshListener(this);
        db = new DatabaseHandler(this);
        List<ProductBean> list=db.getProductList();
        Log.d("Values",list.toString());
        id = new String[list.size()];
        pname = new String[list.size()];
        pquantity = new String[list.size()];
        pprice = new String[list.size()];
        int i = 0;
        for(ProductBean bean:list){
            id[i] = bean.getKeyid();
            pname[i] = bean.getProduct_name();
            pquantity[i] = bean.getProduct_quantity();
            pprice[i] = bean.getProduct_price();
            i++;
        }
        listView = (ListView) findViewById(R.id.productlist);
        ProductAdaptor adaptor = new ProductAdaptor(ViewActivity.this,id,pname,pquantity,pprice);
        listView.setAdapter(adaptor);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    swipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String s = listView.getItemAtPosition(i).toString();
                Intent in = new Intent(ViewActivity.this,EditDeleteActivity.class);
                in.putExtra("firebaseid",s);
                startActivity(in);
                overridePendingTransition(R.anim.enter,R.anim.exit);
                finish();
               // Toast.makeText(ViewActivity.this, s, Toast.LENGTH_SHORT).show();
               // adapter.dismiss(); // If you want to close the adapter
            }
        });
    }

    @Override
    public void onRefresh() {

        Thread th=new Thread(new Runnable() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("ProductDatabase");
            @Override
            public void run() {
                if(new TestConnection().isConnectedToServer())
                    getDataFromFirebase(ref);
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ViewActivity.this,"network not available",Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });

                }
            }
        });
        th.start();

    }


    public void getDataFromFirebase(DatabaseReference ref){

        Log.d("rietreve values",ref.child("product").orderByValue().toString());
        ref.child("product").addValueEventListener(new ValueEventListener() {
            ProductBean bean;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bean = new ProductBean();
                for(DataSnapshot s:dataSnapshot.getChildren()){
                   bean.setKeyid( s.child("Id").getValue().toString());
                   bean.setProduct_name(s.child("Name").getValue().toString());
                   bean.setProduct_quantity(s.child("Quantity").getValue().toString());
                   bean.setProduct_price(s.child("Price").getValue().toString());
                    Log.d("ALLDATA",bean.toString());
                   db.addProduct(bean);
                }
                swipeRefreshLayout.setRefreshing(false);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
