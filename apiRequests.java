package com.example.currencyconverter;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class apiRequests implements Runnable {

    //get apiKey value from apiKey class
    apiKey key = new apiKey();
    String Key = key.getAccessKey();

    //variable declarations
    String fromCurrency;
    String toCurrency;
    String amount;

    //apiRequest constructor
    public apiRequests(String fromCurrency, String toCurrency, String amount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
    }

    //declare rate variable to be updated from apiRequest result
    private volatile String result;

    //getter to receive result in main activitiy
    public String getValue(){
        return result;
    }

    @Override
    public void run() {

        //instantiate Http client to execute http requests
        //build url for client to call
        OkHttpClient client = new OkHttpClient();
        String baseUrl = String.format("http://api.currencylayer.com/convert?access_key=%s&from=%s&to=%s&amount=%s",
                Key,fromCurrency,toCurrency,amount);
        Request request = new Request.Builder().url(baseUrl).build();

        Response response;
        try {
            //returns http response
            response = client.newCall(request).execute();
            //get response in a string variable
            String result = response.body().string();
            //converts string to jsonObject
            JSONObject jsonObject = new JSONObject(result);
            //gets result in a string from the jsonObject of the http response result
            this.result = String.valueOf(jsonObject.getDouble("result"));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}
