package com.example.cryptoappreal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.Locale;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_24H_VOLUME_USD;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_AVAILABLE_SUPPLY;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_ID;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_PRICE;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_RANK;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_SYMBOL;
import static com.example.cryptoappreal.MainActivity.EXTRA_CURRENCY_URL;

/**
 * Created by sarang on 29/01/2018.
 */

public class CurrencyDetail extends AppCompatActivity{

    Context mContext;
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
        int currency24Volume = intentThatCalledThisActivity.getIntExtra(EXTRA_CURRENCY_24H_VOLUME_USD, 0);
        String iconImageURL = intentThatCalledThisActivity.getStringExtra(EXTRA_CURRENCY_URL);

        TextView textCurrencySymbol;
        TextView textCurrencyRank;
        TextView textCurrency24Volume;
        TextView textCurrencyMarketSupply;
        ImageView imageView;

        TextView currencyNameView = (TextView) findViewById(R.id.currencyNameBig);
        TextView currencyPriceView = (TextView) findViewById(R.id.currencyPriceBig);
        textCurrencyMarketSupply = (TextView) findViewById(R.id.currencySupply);
        textCurrencySymbol = (TextView) findViewById(R.id.currencySymbol);
        textCurrencyRank = (TextView) findViewById(R.id.currencyRank);
        textCurrency24Volume = (TextView) findViewById(R.id.currency24Volume);
        imageView = (ImageView) findViewById(R.id.iconImageView);

        Picasso.with(this).load("https://files.coinmarketcap.com/static/img/coins/128x128/"+ iconImageURL + ".png").into(imageView);

        currencyNameView.setText(currencyName);
        textCurrencySymbol.setText(currencySymbol);
        currencyPriceView.setText(String.format(Locale.UK, "$%.3f",currencyPrice));
        textCurrencyMarketSupply.setText(String.format(Locale.UK, "%f", currencyASupply));
        textCurrencyRank.setText(Integer.toString(currencyRank));
        textCurrency24Volume.setText(Integer.toString(currency24Volume));

        }
}

