package com.smart.recycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddModel extends AppCompatActivity {
    private ArrayAdapter<String> adapterCars;
    private ArrayList<String> carList;
    private SQLiteDatabase database;
    private String[] getTradeMark;

    Spinner spinner;
    EditText editTextModelYear, editTextModelName, editTextPower;
    RadioGroup RadioGroupFuel, RadioGroupGearBox;
    String selectedCarTradeMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_model);
        database = this.openOrCreateDatabase("Cars", MODE_PRIVATE, null);
        Cursor cursor2 = database.rawQuery("SELECT * FROM car_trademark ", null);

        spinner = findViewById(R.id.spinner2);
        editTextModelYear = findViewById(R.id.editTextModelYear);
        editTextModelName = findViewById(R.id.editTextModelName);
        editTextPower = findViewById(R.id.editTextPower);
        RadioGroupFuel = findViewById(R.id.RadioGroupFuel);
        RadioGroupGearBox = findViewById(R.id.RadioGroupGearBox);

        carList = new ArrayList<String>();
        while (cursor2.moveToNext()){
            carList.add(cursor2.getString(0) + "-" + cursor2.getString(1));

        }


        adapterCars = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carList);
        adapterCars.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCars);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedCarTradeMark = (parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                 selectedCarTradeMark = (parent.getItemAtPosition(0).toString());
            }
        });
    }
    public void addModelClick(View view){



        if (editTextModelYear.getText().toString().isEmpty() || editTextModelName.getText().toString().isEmpty() || editTextPower.getText().toString().isEmpty() || RadioGroupGearBox.getCheckedRadioButtonId() == -1 || RadioGroupFuel.getCheckedRadioButtonId() == -1){
            Toast.makeText(AddModel.this,"Lütfen Boş Alan Bırakmayın!",Toast.LENGTH_LONG).show();

        }else{
            getTradeMark = selectedCarTradeMark.split("-");


            int index = RadioGroupGearBox.indexOfChild(findViewById(RadioGroupGearBox.getCheckedRadioButtonId()));
            RadioButton r = (RadioButton) RadioGroupGearBox.getChildAt(index);

            int index2 = RadioGroupFuel.indexOfChild(findViewById(RadioGroupGearBox.getCheckedRadioButtonId()));
            RadioButton r2 = (RadioButton) RadioGroupFuel.getChildAt(index);
            try {
                String tradeMark = (String) editTextModelYear.getText().toString();
                String sqlInsert = "INSERT INTO car_models (car_trademark_id, car_model, car_hp, model_year, fuel_type, gearbox_type) VALUES (?,?,?,?,?,?)";
                SQLiteStatement sqLiteStatement = database.compileStatement(sqlInsert);
                sqLiteStatement.bindString(1, getTradeMark[0]);
                sqLiteStatement.bindString(2, editTextModelName.getText().toString());
                sqLiteStatement.bindString(3, editTextPower.getText().toString());
                sqLiteStatement.bindString(4, editTextModelYear.getText().toString());
                sqLiteStatement.bindString(5, r2.getText().toString());
                sqLiteStatement.bindString(6, r.getText().toString());

                sqLiteStatement.execute();
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        this.finish();



    }
}