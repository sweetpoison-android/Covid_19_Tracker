package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covid_19tracker.api.ApiUtilities;
import com.example.covid_19tracker.api.Countrydata_beanclass;

import java.util.ArrayList;
import java.util.List;

public class CountryData extends AppCompatActivity {

    List<Countrydata_beanclass> list;
    RecyclerView recyclerView;
    Countrydata_recyclerviewAdapter adapter;

    ProgressDialog progressDialog;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_data);

        search = findViewById(R.id.countrydata_edittextsearch);
        recyclerView = findViewById(R.id.countrydata_recyclerview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }


        list = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        adapter = new Countrydata_recyclerviewAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<Countrydata_beanclass>>() {
            @Override
            public void onResponse(Call<List<Countrydata_beanclass>> call, Response<List<Countrydata_beanclass>> response) {
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Countrydata_beanclass>> call, Throwable t) {
                Toast.makeText(CountryData.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

      search.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {
              List<Countrydata_beanclass> arrayList = new ArrayList<>();
              for (Countrydata_beanclass item : list)
              {
                  if (item.getCountry().toLowerCase().contains(s.toString().toLowerCase()))
                  {
                      arrayList.add(item);
                  }
              }
              adapter.countrysearch(arrayList);

          }
      });

    }
}