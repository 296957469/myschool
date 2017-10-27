package com.example.myschool;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.FormBody.*;

public class denglu extends AppCompatActivity implements View.OnClickListener {
    EditText zhanghao;
    EditText mima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        Button button1 = (Button) findViewById(R.id.queren);
        zhanghao = (EditText) findViewById(R.id.zhanghao);
        mima = (EditText) findViewById(R.id.mima);
        button1.setOnClickListener(this);
        zhanghao.setOnClickListener(this);
        mima.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.queren:
               String id = zhanghao.getText().toString();
                String password = mima.getText().toString();
              if(id.equals("")&&password.equals("")) //账号和密码为空
              {
                  AlertDialog.Builder dialog=new AlertDialog.Builder(denglu.this);
                  dialog.setTitle("提示");
                  dialog.setMessage("请输入账号和密码。");
                  dialog.setCancelable(false);
                  dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });
                  dialog.show();
              }
              else if(password.equals(""))//密码为空
              {
                  AlertDialog.Builder dialog=new AlertDialog.Builder(denglu.this);
                  dialog.setTitle("提示");
                  dialog.setMessage("请输入密码。");
                  dialog.setCancelable(false);
                  dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });
                  dialog.show();
              }
              else if(id.equals(""))//账号为空
              {
                  AlertDialog.Builder dialog=new AlertDialog.Builder(denglu.this);
                  dialog.setTitle("提示");
                  dialog.setMessage("请输入账号。");
                  dialog.setCancelable(false);
                  dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });
                  dialog.show();
              }
              else if(!id.equals("")&&!password.equals(""))//账号和密码都不为空，开启网络请求，对比账号和密码
              {
                  sendRequestWithHttpURLConnection(id,password);
              }
                break;
            default:
                break;
        }
    }
    private void sendRequestWithHttpURLConnection(final String id,final String password) {
        // 开启线程来发起网络请求
        final ProgressDialog progressDialog=new ProgressDialog(denglu.this);
        progressDialog.setTitle("账号登录");
        progressDialog.setMessage("登录中......");
        progressDialog.setCancelable(true);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody=new Builder()
                            .add("id",id)
                            .add("password",password)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://120.78.151.153:8080/linyu/app_login.jsp")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                   final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 在这里进行UI操作，将结果显示到界面上
                            progressDialog.dismiss();
                            if (responseData.equals("ok")) {
                                Toast.makeText(denglu.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                Intent  intent1=new Intent(denglu.this,wangluo.class);
                                startActivity(intent1);
                            }
                            else if(responseData.equals("error"))
                            {
                                Toast.makeText(denglu.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                            }
                            else if(responseData.equals("noexit"))
                            {
                                Toast.makeText(denglu.this, "请输入正确的账号！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /*private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                if (response.equals("ok")) {

                    Toast.makeText(denglu.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent  intent1=new Intent(denglu.this,wangluo.class);
                    startActivity(intent1);
                }
                else if(response.equals("error"))
                {

                    Toast.makeText(denglu.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                else if(response.equals("noexit"))
                {

                    Toast.makeText(denglu.this, "请输入正确的账号！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/
}
