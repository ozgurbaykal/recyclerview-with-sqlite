package com.smart.recycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class CarInfo extends AppCompatActivity {

    private TextView txtTradeMark, txtModel, txtPower, txtYear, txtFuel, txtGearBox;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        txtTradeMark = findViewById(R.id.txtTradeMark);
        txtModel = findViewById(R.id.txtModel);
        txtPower = findViewById(R.id.txtPower);
        txtYear = findViewById(R.id.txtYear);
        txtFuel = findViewById(R.id.txtFuel);
        txtGearBox = findViewById(R.id.txtGearBox);


        Intent intent = getIntent();
        String modelName = intent.getStringExtra("modelName");
        txtModel.setText(modelName);

        database = this.openOrCreateDatabase("Cars", MODE_PRIVATE, null);
        String[] params = new String[]{modelName};
        Cursor cursor = database.rawQuery("SELECT *, car_trademark.car_name FROM car_models INNER JOIN car_trademark ON car_models.car_trademark_id = car_trademark.id WHERE car_models.car_model=?",params, null);

        while (cursor.moveToNext()){

            txtTradeMark.setText(cursor.getString(8));
            txtPower.setText(cursor.getString(3));
            txtYear.setText(cursor.getString(4));
            txtFuel.setText(cursor.getString(5));
            txtGearBox.setText(cursor.getString(6));

        }
    }
}