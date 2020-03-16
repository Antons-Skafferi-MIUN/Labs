package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText txt1, txt2, txt3, txt4, txt5, txt6;
    private TextView placeTxt;
    private ImageView imageView;
    private String key = "9087feb607d1788e0ac1c5ccf5012ca5";
    private String start = "http://api.openweathermap.org/data/2.5/weather?mode=xml&appid=";
    private String api = start + key + "&q=";

    private String imageStart = "http://openweathermap.org/img/w/";
    private String imageId = "04d";
    private String imageEnd = ".png";
    private HandleXML obj;
    private Button btnWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWeather = findViewById(R.id.btn_weather);
        txt1 = findViewById(R.id.txt_location);
        txt2 = findViewById(R.id.txt_temp);
        txt3 = findViewById(R.id.txt_precipitation);
        txt4 = findViewById(R.id.txt_clouds);
        txt5 = findViewById(R.id.txt_wind_speed);
        txt6 = findViewById(R.id.txt_wind_direction);
        imageView = findViewById(R.id.image_weather);

        placeTxt = findViewById(R.id.txt_overView);

        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String qth = txt1.getText().toString();
            String finalUrl = api + qth;
            txt2.setText(finalUrl);
            obj = new HandleXML(finalUrl);
            obj.fetchXML();
            while (obj.parsingComplete);

            String imageApi = imageStart + obj.getWeather() + imageEnd;
            Picasso.get().load(imageApi).into(imageView);

            txt1.setText("");
            txt2.setText(obj.getTemperature());
            txt3.setText(obj.getPrecipitation());
            txt4.setText(obj.getClouds());
            txt5.setText(obj.getSpeed());
            txt6.setText(obj.getDirection());

            String city = obj.getCity();
            String countryCode = obj.getCountry();
            Locale loc = new Locale("", countryCode);
            String country = loc.getDisplayCountry();
            String overViewString = getResources().getString(R.string.weather_report);
            overViewString += " for " + city + ", " + country;
            placeTxt.setText(overViewString);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this add items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
