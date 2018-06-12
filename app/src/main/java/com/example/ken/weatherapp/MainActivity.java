package com.example.ken.weatherapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ken.weatherapp.data.Channel;
import com.example.ken.weatherapp.data.Item;
import com.example.ken.weatherapp.service.WeatherServiceCallback;
import com.example.ken.weatherapp.service.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImageView =(ImageView)findViewById(R.id.WeatherIconImageView);
        temperatureTextView =(TextView)findViewById(R.id.TemperatureTextView);
        conditionTextView =(TextView)findViewById(R.id.ConditionTextView);
        locationTextView =(TextView)findViewById(R.id.LocationTextView);

       service = new YahooWeatherService(this);
       dialog = new ProgressDialog(this);
       dialog.setMessage("Loading...");
       dialog.show();

       service.refreshWeather("Philadelphia, PA");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_"+ item.getCondition().getCode(),null,getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());

    }



    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
