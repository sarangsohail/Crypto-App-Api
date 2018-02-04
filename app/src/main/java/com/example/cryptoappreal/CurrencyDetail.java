package com.example.cryptoappreal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_24H_VOLUME_USD;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_AVAILABLE_SUPPLY;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_ID;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_PRICE;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_PRICE_BTC;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_RANK;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_SYMBOL;

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
        double currencyASupply = intentThatCalledThisActivity.getDoubleExtra(EXTRA_CURRENCY_AVAILABLE_SUPPLY, 0.0);
        String currencySymbol = intentThatCalledThisActivity.getStringExtra(EXTRA_CURRENCY_SYMBOL);
        int currencyRank = intentThatCalledThisActivity.getIntExtra(EXTRA_CURRENCY_RANK,0);
        double currentPriceBTC = intentThatCalledThisActivity.getDoubleExtra(EXTRA_CURRENCY_PRICE_BTC, 0.0);
        int currency24Volume = intentThatCalledThisActivity.getIntExtra(EXTRA_CURRENCY_24H_VOLUME_USD, 0);

        TextView textCurrencySymbol;
        TextView textCurrencyRank;
        TextView textCurrencyPriceBTC;
        TextView textCurrency24Volume;
        TextView textCurrencyMarketSupply;

        TextView currencyNameView = (TextView) findViewById(R.id.currencyNameBig);
        TextView currencyPriceView = (TextView) findViewById(R.id.currencyPriceBig);
        textCurrencyMarketSupply = (TextView) findViewById(R.id.currencySupply);
        textCurrencySymbol = (TextView) findViewById(R.id.currencySymbol);
        textCurrencyRank = (TextView) findViewById(R.id.currencyRank);
        textCurrencyPriceBTC = (TextView) findViewById(R.id.currencyPriceBTC);
        textCurrency24Volume = (TextView) findViewById(R.id.currency24Volume);



        currencyNameView.setText(currencyName);
        textCurrencySymbol.setText(currencySymbol);
        currencyPriceView.setText(String.format(Locale.UK, "$%.3f",currencyPrice));
        textCurrencyMarketSupply.setText(String.format(Locale.UK, "%f", currencyASupply));
        textCurrencyRank.setText(Integer.toString(currencyRank));
        textCurrencyPriceBTC.setText(String.format(Locale.UK, "%.5f",currentPriceBTC));
        textCurrency24Volume.setText(Integer.toString(currency24Volume));

        }
}

