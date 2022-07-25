package com.smart.recycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddTradeMark extends AppCompatActivity {
    Button addNew;
    TextView editTextTradeMark;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trade_mark);
        database = this.openOrCreateDatabase("Cars", MODE_PRIVATE, null);

        addNew = findViewById(R.id.addNew);
        editTextTradeMark = findViewById(R.id.editTextTradeMark);
    }
    public void addNewClick(View view){
        if (editTextTradeMark.getText().toString().isEmpty()){
            Toast.makeText(AddTradeMark.this,"Lütfen Boş Alan Bırakmayın!",Toast.LENGTH_LONG).show();

        }else{
            try {
                String tradeMark = (String) editTextTradeMark.getText().toString();
                String sqlInsert = "INSERT INTO car_trademark (car_name) VALUES (?)";
                SQLiteStatement sqLiteStatement = database.compileStatement(sqlInsert);
                sqLiteStatement.bindString(1, tradeMark);
                sqLiteStatement.execute();

                Toast.makeText(AddTradeMark.this,"Veri Başarıyla eklendi!",Toast.LENGTH_LONG).show();

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(AddTradeMark.this,"Veri Eklerken Hata Oluştu!",Toast.LENGTH_LONG).show();


            }
        }



        this.finish();
    }
}