package org.gudim.android.jogger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


public class CalculatorActivity extends MyActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //injecting the layout into the content_frame of the menu layout
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        View calculatorActivityView = getLayoutInflater().inflate(R.layout.activity_calculator, null);
        frameLayout.addView(calculatorActivityView);

        Button calculateBMIButton = (Button) findViewById(R.id.buttonCalculateBMI);
        calculateBMIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);
                EditText editTextHeight = (EditText) findViewById(R.id.editTextHeight);
                TextView textViewResult = (TextView) findViewById(R.id.textViewBMI);
                if(editTextHeight.getText().length() != 0 && editTextWeight.getText().length() != 0) {
                    Double weight = Double.parseDouble(editTextWeight.getText().toString());
                    Double height = Double.parseDouble(editTextHeight.getText().toString());
                    Double bmi = weight/((height/100)*(height/100));
                    textViewResult.setText("BMI: " +  String.format("%.1f", bmi));
                }
            }
        });
    }
}
