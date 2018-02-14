package com.example.cryptoappreal;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterCurrency.OnItemClickListener{

    //done in milliseconds - constants
    public static final int CONNECTION_TIMEOUT = 1000;
    public static final int READ_TIMEOUT = 10000;
    public static final String EXTRA_CURRENCY_ID = "id";
    public static final String EXTRA_CURRENCY_PRICE = "price_usd";
    public static final String EXTRA_CURRENCY_SYMBOL = "symbol";
    public static final String EXTRA_CURRENCY_RANK = "rank";
    public static final String EXTRA_CURRENCY_PRICE_BTC = "priceBTC";
    public static final String EXTRA_CURRENCY_24H_VOLUME_USD = "24Volume";
    public static final String EXTRA_CURRENCY_MARKETCAP_USD  = "marketcap";
    public static final String EXTRA_CURRENCY_AVAILABLE_SUPPLY = "supply";
    public static final String EXTRA_CURRENCY_MAX_SUPPLY= "maxSupply";
    public static final String EXTRA_CURRENCY_URL = "URL";


    private RecyclerView mRVCurrencyPrice;
    private AdapterCurrency mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private static final String TAG = "MyActivity";
    List<DataCurrency> jsonData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);


            mSwipeRefreshLayout.setColorSchemeResources(
                    R.color.colorAccent,
                    R.color.colorPrimary,
                    R.color.colorPrimaryDark);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void refreshItemClicked(){
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);
                //get the refresh animation loading and run the Asynfetch method
                onStart();
    }
    @Override
    protected void onStart() {
        super.onStart();

        mSwipeRefreshLayout.post(new Runnable() {
            @Override public void run() {

                mSwipeRefreshLayout.setRefreshing(true);
                // directly call onRefresh() method
                new AsyncFetch().execute();
            }
        });
    }

    @Override
    public void onRefresh() {
          new AsyncFetch().execute();

    }

    //not actually setting the onClick Listener
    @Override
    public void setOnClickItemListener(int position) {
        Intent intent = new Intent(this, CurrencyDetail.class);
        DataCurrency clickedItem = jsonData.get(position);

        intent.putExtra(EXTRA_CURRENCY_URL, clickedItem.icon );

        intent.putExtra(EXTRA_CURRENCY_ID, clickedItem.currencyID);
        intent.putExtra(EXTRA_CURRENCY_PRICE, clickedItem.currencyPrice);
        intent.putExtra(EXTRA_CURRENCY_24H_VOLUME_USD, clickedItem.currencyM24H);
        intent.putExtra(EXTRA_CURRENCY_AVAILABLE_SUPPLY, clickedItem.currencyASupply);
        intent.putExtra(EXTRA_CURRENCY_RANK, clickedItem.currencyRank);
        intent.putExtra(EXTRA_CURRENCY_SYMBOL, clickedItem.currencySymbol);
        intent.putExtra(EXTRA_CURRENCY_MAX_SUPPLY, clickedItem.currencyMaxSupply);

        startActivity(intent);

    }


    private class AsyncFetch extends AsyncTask<String, String, String>  {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
//            pdLoading.setMessage("\tLoading...");
//            pdLoading.setCancelable(false);
//            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

              url = new URL("https://api.coinmarketcap.com/v1/ticker");

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to receive data from api
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                //data is received from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection is made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from api
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (sb.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            jsonData = new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i=0; i< jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DataCurrency dataCurrency = new DataCurrency();

                        //api may return 'null' so check that
                        if(!jsonObject.isNull("max_supply")) {
                            dataCurrency.currencyMaxSupply = jsonObject.getInt("max_supply");
                        }

                        dataCurrency.id = i;
                        dataCurrency.icon = jsonObject.getString("id");
                        dataCurrency.currencyID = jsonObject.getString("name");
                        dataCurrency.currencyPrice = jsonObject.getDouble("price_usd");
                        dataCurrency.currencySymbol = jsonObject.getString("symbol");
                        dataCurrency.currencyASupply = jsonObject.getDouble("available_supply");
                        dataCurrency.currencyRank = jsonObject.getInt("rank");
                        dataCurrency.currencyM24H = jsonObject.getInt("24h_volume_usd");

                        jsonData.add(dataCurrency);
                    }

                    //setu[ and handover data to the RV
                    mRVCurrencyPrice = (RecyclerView) findViewById(R.id.currencyRV);
                    mAdapter =  new AdapterCurrency(MainActivity.this, jsonData);
                    mRVCurrencyPrice.setAdapter(mAdapter);
                    mAdapter.setOnClickListener(MainActivity.this);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mRVCurrencyPrice.setLayoutManager(new LinearLayoutManager(MainActivity.this));


                } catch (JSONException e) {

                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, e.toString());
                }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_items, menu);
        //get actionView is deprecated so i'll use this to locate the search_button
//        MenuItem menuSearchItem = menu.findItem(R.id.);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_button1).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<DataCurrency> newList = new ArrayList<>();
                for (DataCurrency dataCurrency : jsonData){
                    String currency = dataCurrency.currencyID.toLowerCase();
                    if (currency.contains(newText)){
                        newList.add(dataCurrency);
                    }
                    mAdapter.setFilter(newList);
                }
                return true;
            }
        });


        return true;
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh_button){
            //show the refresh animation and load the data again
            refreshItemClicked();
        }

        return super.onOptionsItemSelected(item);
    }





}

