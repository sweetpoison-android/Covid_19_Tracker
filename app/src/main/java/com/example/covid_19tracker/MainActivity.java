package com.example.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.number.FormattedNumber;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19tracker.api.ApiUtilities;
import com.example.covid_19tracker.api.Countrydata_beanclass;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView country,update;
    AnimatedPieView piechart;

    TextView total_confirm, total_active, total_recovered, total_death, total_test;
    TextView tody_confirm, today_recovered, today_death;
    LinearLayout linearLayout;

    List<Countrydata_beanclass> coutrydata;

    ProgressDialog progressDialog;

    String countryname = "India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        country = findViewById(R.id.main_textview2);
        update = findViewById(R.id.main_textview3);
        piechart = findViewById(R.id.main_piechart);

        linearLayout = findViewById(R.id.mainactivity_maindatalinearlayout);
        total_confirm = findViewById(R.id.main_textview_confirm);
        total_active = findViewById(R.id.main_textview_active);
        total_recovered = findViewById(R.id.main_textview_recovered);
        total_death = findViewById(R.id.main_textview_death);
        total_test= findViewById(R.id.main_textview_test);
        tody_confirm = findViewById(R.id.main_textview_todayconfirm);
        today_recovered = findViewById(R.id.main_textview_todayrecovered);
        today_death = findViewById(R.id.main_textview_todaydeath);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        coutrydata = new ArrayList<>();

        if (getIntent().getStringExtra("countryName") != null)
        {
            countryname = getIntent().getStringExtra("countryName");
        }
      country.setText(countryname);
        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CountryData.class));
                finish();
            }
        });

        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<Countrydata_beanclass>>() {
                    @Override
                    public void onResponse(Call<List<Countrydata_beanclass>> call, Response<List<Countrydata_beanclass>> response) {
                      coutrydata.addAll(response.body());
                      progressDialog.dismiss();
                      linearLayout.setVisibility(View.VISIBLE);
                      for (int i=0; i<coutrydata.size(); i++)
                      {
                          if (coutrydata.get(i).getCountry().equalsIgnoreCase(countryname))
                          {
                              int cases = Integer.parseInt(coutrydata.get(i).getCases());
                              int active = Integer.parseInt(coutrydata.get(i).getActive());
                              int recovered = Integer.parseInt(coutrydata.get(i).getRecovered());
                              int death = Integer.parseInt(coutrydata.get(i).getDeaths());
                              int test = Integer.parseInt(coutrydata.get(i).getTests());
                              int todaycases = Integer.parseInt(coutrydata.get(i).getTodayCases());
                              int todatrecovered = Integer.parseInt(coutrydata.get(i).getTodayRecovered());
                              int todaydeath = Integer.parseInt(coutrydata.get(i).getTodayDeaths());
                              String updated = coutrydata.get(i).getUpdate();

                              total_confirm.setText(NumberFormat.getInstance().format(cases));
                              total_active.setText(NumberFormat.getInstance().format(active));
                              total_recovered.setText(NumberFormat.getInstance().format(recovered));
                              total_death.setText(NumberFormat.getInstance().format(death));
                              total_test.setText(NumberFormat.getInstance().format(test));
                              tody_confirm.setText(NumberFormat.getInstance().format(todaycases));
                              today_recovered.setText(NumberFormat.getInstance().format(todatrecovered));
                              today_death.setText(NumberFormat.getInstance().format(todaydeath));

                              DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a ");
                              long milliseconds = Long.parseLong(updated);

                              Calendar calendar = Calendar.getInstance();
                              calendar.setTimeInMillis(milliseconds);
                              update.setText("Updated at : "+format.format(calendar.getTime()));

                              piechart_status(cases,active,recovered,death);

                          }

                      }



                    }

                    @Override
                    public void onFailure(Call<List<Countrydata_beanclass>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void piechart_status(int a, int b, int c, int d)
    {
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.addData(new SimplePieInfo(a, getResources().getColor(R.color.yellow), "Confirm"));
        config.addData(new SimplePieInfo(b, getResources().getColor(R.color.blue), "Active"));
        config.addData(new SimplePieInfo(c, getResources().getColor(R.color.green), "Recovered"));
        config.addData(new SimplePieInfo(d, getResources().getColor(R.color.red), "Death"));
        config.duration(1000);
        // config.drawText(true);
        //  config.textSize(50f);
        config.strokeMode(false);

        piechart.applyConfig(config);
        piechart.start();

        config.selectListener(new OnPieSelectListener<IPieInfo>() {
            @Override
            public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isFloatUp) {
                Toast.makeText(MainActivity.this, pieInfo.getDesc() + "  : " +NumberFormat.getInstance(new Locale("hi","IN")).format(((int)pieInfo.getValue())), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}