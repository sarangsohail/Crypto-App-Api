package com.example.cryptoappreal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_AVAILABLE_SUPPLY;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_ID;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_PRICE;

/**
 * Created by sarang on 29/01/2018.
 */

public class CurrencyDetail extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_detail_xml);

        Intent intentThatCalledThisActivity = getIntent();
        String currencyName = intentThatCalledThisActivity.getStringExtra(EXTRA_CURRENCY_ID);
        double  currencyPrice = intentThatCalledThisActivity.getDoubleExtra(EXTRA_CURRENCY_PRICE, 0.0);
        int currencyASupply = intentThatCalledThisActivity.getIntExtra(EXTRA_CURRENCY_AVAILABLE_SUPPLY, 0);
        //tODO SORT OUT CURRENCY SUPPLY - EITHER NOT PSASING IN DATA COS THE DATA IS SET AS DEFAULT VALUE - 0
        TextView textCurrencySymbol;
        TextView textCurrencyRank;
        TextView textCurrencyPriceBTC;
        TextView textCurrency24Volume;
        TextView textCurrencyMarketCap;
        TextView textCurrencyMarketSupply;

        TextView currencyNameView = (TextView) findViewById(R.id.currencyNameBig);
        TextView currencyPriceView = (TextView) findViewById(R.id.currencyPriceBig);
        textCurrencySymbol = (TextView) findViewById(R.id.currencySymbol);
        textCurrencyRank = (TextView) findViewById(R.id.currencyRank);
        textCurrencyPriceBTC = (TextView) findViewById(R.id.currencyPriceBTC);
        textCurrency24Volume = (TextView) findViewById(R.id.currency24Volume);
        textCurrencyMarketCap= (TextView) findViewById(R.id.currencyMarketCap);
        textCurrencyMarketSupply = (TextView) findViewById(R.id.currencySupply);



        currencyNameView.setText(currencyName);
        currencyPriceView.setText(String.format(Locale.UK, "$%.3f",  currencyPrice));
       // textCurrencyRank.setText();
         textCurrencyMarketSupply.setText(Integer.toString(currencyASupply));
//        textCurrencyMarketCap.setText(current.currencyMaxSupply);
//        textCurrency24Volume.setText(current.currencyM24H);
//        textCurrencyPriceBTC.setText(current.currencyPriceBTC);
        }
}
