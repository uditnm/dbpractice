package com.example.dbpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //references to buttons and controls on layout
    Button btn_viewall, btn_add;
    EditText et_name, et_age;
    Switch sw_active;
    ListView lv_custlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewall = findViewById(R.id.btn_viewall);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        sw_active = findViewById(R.id.sw_active);
        lv_custlist = findViewById(R.id.lv_custlist);

        //button listners for the add and view all buttons
        btn_add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                customerModel customermodel = null;
                try{
                    customermodel = new customerModel(-1,et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()), sw_active.isChecked());
                    Toast.makeText(MainActivity.this,customermodel.toString(),Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                }


                dbclass dbclas = new dbclass(MainActivity.this);
                boolean success = dbclas.addOne(customermodel);

                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }
        });

        btn_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbclass dbclas = new dbclass(MainActivity.this);
                List<customerModel> all = dbclas.getall();

                ArrayAdapter customerArrayAdapter = new ArrayAdapter<customerModel>(MainActivity.this, android.R.layout.simple_list_item_1, all);
                lv_custlist.setAdapter(customerArrayAdapter);

                //Toast.makeText(MainActivity.this, all.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}