package com.example.cryptoappreal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

/**
 * Created by sarang on 23/01/2018.
 */

class AdapterCurrency extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    LayoutInflater layoutInflater;
    List<DataCurrency> currencyList = Collections.emptyList();
    DataCurrency current;

    public AdapterCurrency(Context context, List<DataCurrency> data) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.currencyList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.container_currency, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myholder = (MyHolder)holder;
        DataCurrency current = currencyList.get(position);
        myholder.textCurrencyName.setText(current.currencyID);



    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textCurrencyName;
        TextView textCurrencyPrice;

        public MyHolder(View itemView) {
            super(itemView);
            textCurrencyName = (TextView) itemView.findViewById(R.id.currencyName);
            textCurrencyPrice = (TextView) itemView.findViewById(R.id.currencyPrice);
        }
    }

}
