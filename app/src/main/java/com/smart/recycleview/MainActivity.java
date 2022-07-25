package com.smart.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataModel> mList;
    private ItemAdapter adapter;
    private SQLiteDatabase database;
    private TextView empty_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recyclerview);
        empty_view = findViewById(R.id.empty_view);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();


        addTradeMark();

        addModel();




    }

    @Override
    protected void onResume() {
        super.onResume();
        //readDatasFromSQLite();

        mList.clear();

        readDatasFromSQLite();


        if ( mList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.GONE);
        }
    }

    private void readDatasFromSQLite() {

        try {
            database = this.openOrCreateDatabase("Cars", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS car_trademark (id INTEGER PRIMARY KEY AUTOINCREMENT, car_name VARCHAR NOT NULL)");
            database.execSQL("CREATE TABLE IF NOT EXISTS car_models (model_id INTEGER PRIMARY KEY AUTOINCREMENT, car_trademark_id INTEGER, car_model VARCHAR NOT NULL, car_hp INTEGER NOT NULL, model_year INTEGER NOT NULL, fuel_type VARCHAR NOT NULL, gearbox_type VARCHAR NOT NULL, FOREIGN KEY (model_id) REFERENCES car_trademark(id))");

            Cursor cursor2 = database.rawQuery("SELECT * FROM car_trademark ", null);
            Cursor cursor = null;
            ArrayList deneme = new ArrayList();
            ArrayList deneme2 = new ArrayList();
            while (cursor2.moveToNext()){
                String carTradeMarkName = cursor2.getString(1);
                // String[] params = new String[]{cursor2.getString(0)};
                deneme.add(cursor2.getString(0));
                deneme2.add(cursor2.getString(1));

            }
            cursor2.close();
            for (int i =0; i<deneme.size(); i++){
                String[] params = new String[]{(String) deneme.get(i)};

                cursor = database.rawQuery("SELECT * FROM car_models WHERE car_trademark_id=?", params,null);
                List<String> nestedList1 = new ArrayList<>();

                while (cursor.moveToNext()){


                    System.out.println(cursor.getString(2) + "ABABAB");
                    nestedList1.add(cursor.getString(2));

                }
                mList.add(new DataModel(nestedList1, (String) deneme2.get(i)));

                adapter = new ItemAdapter(mList);
                recyclerView.setAdapter(adapter);

            }


            cursor.close();
            //adapter.updateList(mList);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addModel() {

        FloatingActionButton btn2 = (FloatingActionButton) findViewById(R.id.floatingButton3);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddModel.class);
                startActivity(intent);

            }
        });
    }

    private void addTradeMark() {


        FloatingActionButton btn1 = (FloatingActionButton) findViewById(R.id.floatingButton2);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,AddTradeMark.class);
                startActivity(intent);
            }
        });

    }
}