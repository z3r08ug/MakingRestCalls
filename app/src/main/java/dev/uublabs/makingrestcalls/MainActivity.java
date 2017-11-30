package dev.uublabs.makingrestcalls;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import dev.uublabs.makingrestcalls.model.MyResponse;
import dev.uublabs.makingrestcalls.model.github.GithubProfile;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{
    public static final String URL = "http://www.mocky.io/v2/5a0db7902e0000cb3d3a2ff3";
    private OkHttpClient client;
    private Request request;
    private TextView tvResults;
    private String Response;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //work around for using network call on main thread, DONT USE
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//        .permitNetwork().build());

        tvResults = findViewById(R.id.tvResponse);
    }

    public void makingRestCalls(View view)
    {
        client = new OkHttpClient();

        request = new Request.Builder()
                .url(URL)
                .build();



        switch (view.getId())
        {
            case R.id.btnNativeHttp:
                MyHttpThread myHttpThread = new MyHttpThread(URL);
                myHttpThread.start();
                break;

            case R.id.btnOkHttpSync:
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        try
                        {
                            Response = client.newCall(request).execute().body().string();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                tvResults.setText(Response);
                            }
                        });
                    }
                }).start();

                break;

            case R.id.btnOkHttpASync:

                client.newCall(request).enqueue(new Callback()
                {
                    @Override
                    public void onFailure(Call call, IOException e)
                    {
                        Log.d("MainActivity", "onFailure: "+e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException
                    {
                        String newResponse = response.body().string();
                        //parsing the response
                        Gson gson = new Gson();
                        MyResponse myResponse = gson.fromJson(newResponse, MyResponse.class);

                        //print response
                        Log.d("MainActivity", "onResponse: " + myResponse.getName());
                    }
                });
                break;

            case R.id.btnRetrofitSync:


                break;

            case R.id.btnretrofitAsync:
                RetroFitHelper.getMyProfile("manroopsingh")
                        .enqueue(new retrofit2.Callback<GithubProfile>() {
                            @Override
                            public void onResponse(retrofit2.Call<GithubProfile> call, retrofit2.Response<GithubProfile> response) {
                                Log.d("MainActivity", "onResponse: "+response.body().getName());
                            }

                            @Override
                            public void onFailure(retrofit2.Call<GithubProfile> call, Throwable t) {

                            }
                        });
                break;

        }
    }
}
