package com.example.cryptoappreal;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

class AdapterCurrency extends RecyclerView.Adapter<AdapterCurrency.MyHolder>  {

    Context mContext;
    LayoutInflater layoutInflater;
    List<DataCurrency> currencyList = Collections.emptyList();
    OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener{
        void setOnClickItemListener(int position);
    }

    public void setOnClickListener(OnItemClickListener onClickListener){
        this.mOnItemClickListener = onClickListener;
    }

    public AdapterCurrency(Context context, List<DataCurrency> data) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.currencyList = data;
    }

    @Override
    public AdapterCurrency.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.container_currency, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterCurrency.MyHolder myholder, final int position) {
        final DataCurrency current = currencyList.get(position);
        myholder.textCurrencyName.setText(current.currencyID);
        myholder.textCurrencyPrice.setText("$" + current.currencyPrice);


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


            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    //this checks if there actually is an listener
                    if (mOnItemClickListener != null){
                        int currentPos = getAdapterPosition();
                        //checks if the position clicked is valid
                        if (currentPos != RecyclerView.NO_POSITION){
                            //pass to interface method
                            mOnItemClickListener.setOnClickItemListener(currentPos);
                        }
                    }
                }
            });
        }
    }

}
