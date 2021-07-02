package com.retrofit.write;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.retrofit.write.retrofit.Call;
import com.retrofit.write.retrofit.Callback;
import com.retrofit.write.retrofit.Response;
import com.retrofit.write.simple.RetrofitClient;
import com.retrofit.write.simple.UserLoginResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        RetrofitClient
                .getServiceApi()
                .userLogin("yd", "123456")
                .enqueue(new Callback<UserLoginResult>() {
                    @Override
                    public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {
                        final String result = response.body.toString();
                        Log.i("TAG",result);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<UserLoginResult> call, Throwable t) {
                        Log.e("TAG",t.getMessage());
                    }
                });
    }
}