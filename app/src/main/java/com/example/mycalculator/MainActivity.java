package com.example.mycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static boolean resultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void operandClick(View view) {
        Button btn = findViewById(view.getId());
        TextView display = findViewById(R.id.display);

        String dataOnDisplay = display.getText().toString();
        String btnData = btn.getText().toString();

        if (dataOnDisplay.equals("0") || resultShown) {
            display.setText(btnData);
            resultShown = false;
        }
        else {
            String res = dataOnDisplay + btn.getText().toString();
            display.setText(res);
        }
    }

    public void operatorClick(View view) {
        TextView display = findViewById(R.id.display);
        Button btn = findViewById(view.getId());

        if (resultShown)
            resultShown = false;

        display.setText(display.getText() + btn.getText().toString());
    }

    public void clear(View view) {
        TextView display = findViewById(R.id.display);
        display.setText("0");
    }

    public void calculate(View view) {
        TextView display = findViewById(R.id.display);

        String equation = display.getText().toString();
        Double result = ExpressionEvaluation.expressionEvaluation(equation);

        display.setText(result.toString());
        resultShown = true;


    }
}
