package com.example.ken.weatherapp.service;

import com.example.ken.weatherapp.data.Channel;

public interface WeatherServiceCallback {

    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
