package com.example.covid_19tracker;

import android.content.Context;
import android.content.Intent;
import android.icu.number.NumberFormatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.covid_19tracker.api.Countrydata_beanclass;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Countrydata_recyclerviewAdapter extends RecyclerView.Adapter<Countrydata_recyclerviewAdapter.myinner> {

    Context con;
    List<Countrydata_beanclass> list;

    public Countrydata_recyclerviewAdapter(Context con, List<Countrydata_beanclass> list) {
        this.con = con;
        this.list = list;
    }

    @NonNull
    @Override
    public myinner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(con).inflate(R.layout.recyclerview_item, parent, false);
        return new myinner(v);
    }

    public void countrysearch(List<Countrydata_beanclass> filterlist)
    {
        list = filterlist;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull myinner holder, int position) {

     holder.serialnumber.setText(Integer.valueOf(position+1)+".");
        Map<String, String> countryinfo = list.get(position).getCountryInfo();
        Glide.with(con).load(countryinfo.get("flag")).into(holder.countryflag);
        holder.countryname.setText(list.get(position).getCountry());
        holder.countrypopulation.setText(NumberFormat.getInstance(new Locale("hi", "IN")).format(Integer.parseInt(list.get(position).getPopulation())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(con, MainActivity.class);
                in.putExtra("countryName", list.get(position).getCountry());
                con.startActivity(in);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class myinner extends RecyclerView.ViewHolder {

        TextView serialnumber,countryname,countrypopulation;
        ImageView countryflag;

        public myinner(@NonNull View itemView) {
            super(itemView);

            serialnumber = itemView.findViewById(R.id.recyclerviewitem_serialnumber);
            countryflag = itemView.findViewById(R.id.recyclerviewitem_flagimage);
            countryname = itemView.findViewById(R.id.recyclerviewitem_countryname);
            countrypopulation = itemView.findViewById(R.id.recyclerviewitem_countrypopunlation);


        }
    }
}
