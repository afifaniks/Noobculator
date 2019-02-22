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

        final TextView display = findViewById(R.id.display);
        final TextView history = findViewById(R.id.prev);
        Button clear = findViewById(R.id.clear);

        clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                display.setText("0");
                history.setText("");
                return true;
            }
        });
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
        String displayText = display.getText().toString();

        if (resultShown)
            resultShown = false;

        if (displayText.equals("Error") || displayText.equals("Infinity"))
            display.setText("0" + btn.getText());
        else
            display.setText(displayText + btn.getText());
    }

    public void clear(View view) {
        TextView display = findViewById(R.id.display);
        String displayData = display.getText().toString();

        if (displayData.equals("0") || displayData.equals("Error") || displayData.equals("Infinity"))
            display.setText("0");
        else {
            String backSpacedData = displayData.substring(0, displayData.length() - 1);

            if (backSpacedData.equals(""))
                backSpacedData = "0";

            display.setText(backSpacedData);
        }
    }

    public void calculate(View view) {
        TextView display = findViewById(R.id.display);
        TextView history = findViewById(R.id.prev);

        String equation = display.getText().toString().trim();
        Double result = ExpressionEvaluation.expressionEvaluation(equation);

        if (Double.isNaN(result)) {
            display.setText("Error");
        } else {
            history.setText(equation);
            if (result == Math.round(result))
            {
                String res = result.toString();
                display.setText(res.substring(0, res.length() - 2));;
            } else
                display.setText(result.toString());

        }

        resultShown = true;

    }
}
