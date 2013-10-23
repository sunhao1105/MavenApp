package com.sun.RobolectricMaven;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork() // 这里可以替换为detectAll() 就包括了磁盘读写和网络I/O
                .penaltyLog() //打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() //探测SQLite数据库操作
                .penaltyLog() //打印logcat
                .penaltyDeath()
                .build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        String url = "http://www.weather.com.cn/data/cityinfo/101010100.html";

        TextView textView = (TextView) findViewById(R.id.weatherString);

        textView.setText(connServerForResult(url));
    }

    private String connServerForResult(String url)
    {
        HttpGet httpResquest = new HttpGet(url);
        String result = "";
        String cityName = "";
        String cityID = "";

        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpResquest);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                result = EntityUtils.toString(httpResponse.getEntity());
            }

            JSONObject jsonObject = new JSONObject(result).getJSONObject("weatherinfo");
            cityName = jsonObject.getString("city");
            cityID = jsonObject.getString("cityid");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return "cityid:" + cityID + ",city:" + cityName;
    }
}
