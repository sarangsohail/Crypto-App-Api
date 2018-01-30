package com.example.cryptoappreal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

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

        TextView currencyNameView = (TextView) findViewById(R.id.currencyNameBig);
        TextView currencyPriceView = (TextView) findViewById(R.id.currencyPriceBig);

        currencyNameView.setText(currencyName);
        currencyPriceView.setText(String.format(Locale.UK, "$%.3f",  currencyPrice));
        }
}
