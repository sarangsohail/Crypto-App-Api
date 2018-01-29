package com.example.cryptoappreal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterCurrency.OnItemClickListener{

    //done in milliseconds - constants
    public static final int CONNECTION_TIMEOUT = 1000;
    public static final int READ_TIMEOUT = 10000;
    public static final String EXTRA_CURRENCY_ID = "id";

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

    @Override
    public void setOnClickItemListener(int position) {
        Intent intent = new Intent(this, CurrencyDetail.class);
        DataCurrency clickedItem = jsonData.get(position);

        intent.putExtra(EXTRA_CURRENCY_ID, clickedItem.currencyID);
        startActivity(intent);
        //Todo add the xml file for new currencyDetail Class and retrive the id
    }


    private class AsyncFetch extends AsyncTask<String, String, String>  {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
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

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we receive data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
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
            //run on the ui thread
            pdLoading.dismiss();
            List<DataCurrency> data = new ArrayList<>();
            pdLoading.dismiss();

                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i =0; i< jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DataCurrency dataCurrency = new DataCurrency();
                        dataCurrency.currencyID  = jsonObject.getString("name");
                        dataCurrency.currencyPrice = jsonObject.getDouble("price_usd");
                        data.add(dataCurrency);
                    }

                    //setu[ and handover data to the RV
                    mRVCurrencyPrice = (RecyclerView) findViewById(R.id.currencyRV);
                    mAdapter =  new AdapterCurrency(MainActivity.this, data);
                    mRVCurrencyPrice.setAdapter(mAdapter);
                    mAdapter.setOnClickListener(MainActivity.this);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mRVCurrencyPrice.setLayoutManager(new LinearLayoutManager(MainActivity.this));


                } catch (JSONException e) {

                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_items, menu);
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

