package com.example.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void operandClick(View view) {
        Button btn = findViewById(view.getId());
        TextView display = findViewById(R.id.display);

        String dataOnDisplay = display.getText().toString();
        String btnData = btn.getText().toString();

        if (dataOnDisplay.equals("0"))
            display.setText(btnData);
        else
        {
            String res = dataOnDisplay + btn.getText().toString();
            display.setText(res);
        }
    }

    public void operatorClick(View view) {
        TextView display = findViewById(R.id.display);
        Button btn = findViewById(view.getId());

        display.setText(display.getText() + btn.getText().toString());
    }

    public void clear(View view) {
        TextView display = findViewById(R.id.display);
        display.setText("");
    }

    public void calculate(View view) {
        TextView display = findViewById(R.id.display);

        String equation = display.getText().toString();

        Double result = ExpressionEvaluation.expressionEvaluation(equation);

        display.setText(result.toString());


    }
}
