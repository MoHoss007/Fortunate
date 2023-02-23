package com.example.fortunate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    String username;
    TextView lbluser, expenditure;
    EditText limit;
    BottomNavigationView bar;
    Button edit, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        username = getIntent().getExtras().getString("User");
        lbluser = (TextView) findViewById(R.id.lbl_username);
        lbluser.setText(username);
        expenditure = (TextView) findViewById(R.id.lbl_total);

        String[] info = DataBase.getRow(username, signup.fis);
        expenditure.setText(String.format("%.2f", Double.parseDouble(info[5])));
        limit = (EditText) findViewById(R.id.lbl_limit);
        limit.setText(String.format("%.2f", Double.parseDouble(info[6])));

        bar = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bar.setOnNavigationItemSelectedListener(this);

        edit = (Button) findViewById(R.id.btn_editLimit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit(Double.parseDouble(limit.getText().toString()));
            }
        });

        add = (Button) findViewById(R.id.btn_action);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

    }

    public void add(){
        Intent intent = new Intent(this, AddExpenditure.class);
        intent.putExtra("User",username);
        startActivity(intent);
    }

    public void edit(double limit){
        String[] info = DataBase.getRow(username, signup.fis);
        DataBase.Update(info[0],info[1],info[2],info[3],info[4],Double.parseDouble(info[5]),limit,signup.fis,this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cat:
                Intent intent = new Intent(this, category.class);
                intent.putExtra("User",username);
                startActivity(intent);
                return true;

            case R.id.home:
                Intent intent2 = new Intent(this, MainPage.class);
                intent2.putExtra("User",username);
                startActivity(intent2);
                return true;

            default:
                return true;
        }
    }
}