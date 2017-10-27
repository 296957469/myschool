package com.example.myschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button denglu=(Button)findViewById(R.id.denglu);
        Button zhuce=(Button)findViewById(R.id.zhuce);
        denglu.setOnClickListener(this);
        zhuce.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.denglu:
                Intent intent1=new Intent(MainActivity.this,denglu.class);
                startActivity(intent1);
                break;
            case R.id.zhuce:
                Intent intent2=new Intent(MainActivity.this,zhuce.class);
                startActivity(intent2);
                break;
            default:
                break;
        }

    }
}
