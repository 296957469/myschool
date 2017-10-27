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
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class zhuce extends AppCompatActivity implements View.OnClickListener {
    EditText zhanghao;
    EditText mima;
    EditText remima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        Button button1 = (Button) findViewById(R.id.queren);
        zhanghao = (EditText) findViewById(R.id.zhanghao);
        mima = (EditText) findViewById(R.id.mima);
        remima = (EditText) findViewById(R.id.remima);
        button1.setOnClickListener(this);
        zhanghao.setOnClickListener(this);
        mima.setOnClickListener(this);
        remima.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.queren:
                String id = zhanghao.getText().toString();
                String password = mima.getText().toString();
                String password2 = remima.getText().toString();
                if(id.equals("")) //账号为空
                {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(zhuce.this);
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
                else if(password.equals(""))//密码为空
                {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(zhuce.this);
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
                else if(password2.equals(""))//确认密码为空
                {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(zhuce.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("请再次输入密码。");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
                else if(!id.equals("")&&!password.equals("")&&!password2.equals(""))//账号和密码都不为空，开启网络请求，对比账号和密码
                {
                    if(!password.equals(password2))
                    {
                        AlertDialog.Builder dialog=new AlertDialog.Builder(zhuce.this);
                        dialog.setTitle("提示");
                        dialog.setMessage("两次密码输入不一致。");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();
                    }
                        else
                    sendRequestWithHttpURLConnection(id,password);
                }
                break;
            default:
                break;
        }
    }
    private void sendRequestWithHttpURLConnection(final String id,final String password) {
        // 开启线程来发起网络请求
        final ProgressDialog progressDialog=new ProgressDialog(zhuce.this);
        progressDialog.setTitle("账号注册");
        progressDialog.setMessage("注册中......");
        progressDialog.setCancelable(true);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("id",id)
                            .add("password",password)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://120.78.151.153:8080/linyu/app_sign_in.jsp")
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
                                Toast.makeText(zhuce.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                Intent intent1=new Intent(zhuce.this,MainActivity.class);
                                startActivity(intent1);
                            }
                            else if(responseData.equals("error"))
                            {
                                Toast.makeText(zhuce.this, "发生未知错误！", Toast.LENGTH_SHORT).show();
                            }
                            else if(responseData.equals("exit"))
                            {
                                Toast.makeText(zhuce.this, "账号已经存在，请重新注册！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}