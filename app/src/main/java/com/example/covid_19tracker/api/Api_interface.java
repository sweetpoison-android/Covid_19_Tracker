package com.example.covid_19tracker.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api_interface {

    public static final String Base_url = "https://corona.lmao.ninja/v2/";

    @GET("countries")
    Call<List<Countrydata_beanclass>> getCountryData();
}
