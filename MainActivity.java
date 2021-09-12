package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    //declare spinners
    private Spinner fromSpinner;
    private Spinner toSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //instantiate and populate spinners from currencies.xml
        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        toSpinner = (Spinner) findViewById(R.id.toSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Currencies, android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);



    }

    public void convertButton(View view) throws Exception {

        //get strings from current spinner selection
        String from = fromSpinner.getSelectedItem().toString();
        String to = toSpinner.getSelectedItem().toString();

        //declare rate variable to receive result from api request
        double result;

        DecimalFormat df = new DecimalFormat("#.##");


        //declare an editText variable with the current value of the edit text field from UI
        //create new instance of apiRequest class
        EditText amount = (EditText) findViewById(R.id.editCurrency);
        apiRequests request = new apiRequests(from, to, amount.getText().toString());

        //check if the amount to convert field is empty
        //start new thread to return result value of apiRequest
        //also check if the editText field contains only numbers
        if (amount.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
        }else {
            try {
                Thread thread = new Thread(request);
                thread.start();
                thread.join();
                result = Double.parseDouble(request.getValue());
                Toast.makeText(getApplicationContext(), amount.getText().toString() + " " + from + " = " + df.format(result) + " " + to, Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "enter only numbers", Toast.LENGTH_SHORT).show();
            }
        }

        }


    }
