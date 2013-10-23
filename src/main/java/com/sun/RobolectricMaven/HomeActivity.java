package com.sun.RobolectricMaven;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity
{
    private TextView name;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goWeatherBtn = (Button) findViewById(R.id.goWeather);
        goWeatherBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, WeatherActivity.class));
            }
        });

        name = (TextView) findViewById(R.id.editText);
        Button goPersonBtn = (Button) findViewById(R.id.goPerson);
        goPersonBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PersonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", String.valueOf(name.getText()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

