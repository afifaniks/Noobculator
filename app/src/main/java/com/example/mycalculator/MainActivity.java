package com.example.mycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author: Afif Al Mamun
 * @created_in: 2/20/19
 * @project_name: MyCalculator
 **/

public class MainActivity extends AppCompatActivity {

    /**
     * resultShown is true when a result is shown
     * after calculation and false otherwise.
     * Uses: To differentiate between the status of the calculator
     * whether it is a new calculation or not.
     */
    private static boolean resultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView display = findViewById(R.id.display);
        final TextView history = findViewById(R.id.prev);
        Button clear = findViewById(R.id.clear);

        // Setting functionality for clear button to clear
        // both TextViews on holding this button.
        clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                display.setText("0");
                history.setText("");
                return true;
            }
        });
    }

    /**
     * Generating ActionBar option menu.
     * @param menu: default
     * @return: default
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // If about item is clicked showing developer abouts.
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to handle operand buttons like 1, 2, 3... clicks.
     * @param view: default
     */
    public void operandClick(View view) {
        Button btn = findViewById(view.getId());
        TextView display = findViewById(R.id.display);

        String dataOnDisplay = display.getText().toString();
        String btnData = btn.getText().toString();

        // If display data is 0 or a result is shown
        // we set the display value to the value of the
        // operand clicked otherwise we append operand with
        // existing data
        if (dataOnDisplay.equals("0") || resultShown) {
            display.setText(btnData);
            resultShown = false;
        }
        else {
            String res = dataOnDisplay + btn.getText().toString();
            display.setText(res);
        }
    }

    /**
     * Handles operators like +, -, *, / clicks.
     * @param view: default
     */
    public void operatorClick(View view) {
        TextView display = findViewById(R.id.display);
        Button btn = findViewById(view.getId());
        String displayText = display.getText().toString();

        if (resultShown)
            resultShown = false;

        if (displayText.equals("Syntax Error") || displayText.equals("Infinity"))
            display.setText("0" + btn.getText());
        else
            display.setText(displayText + btn.getText());
    }

    /**
     * Method is invoked when C button is pressed. We delete the last
     * character inputted on a single press.
     * For some special cases we set it back to 0.
     * @param view: default
     */
    public void clear(View view) {
        TextView display = findViewById(R.id.display);
        String displayData = display.getText().toString();

        if (displayData.equals("0") || displayData.equals("Syntax Error") || displayData.equals("Infinity"))
            display.setText("0");
        else {
            String backSpacedData = displayData.substring(0, displayData.length() - 1);

            if (backSpacedData.equals(""))
                backSpacedData = "0";

            display.setText(backSpacedData);
        }
    }

    /**
     * Method is called upon equal button is clicked.
     * We calculate the value of the equation by calling ExpressionEvaluation class.
     * if it returns NaN then an error has occured. Otherwise we show the result
     * of calculation.
     * @param view: default
     */
    public void calculate(View view) {
        TextView display = findViewById(R.id.display);
        Animation textTransition = AnimationUtils.loadAnimation(this, R.anim.text_transition);

        TextView history = findViewById(R.id.prev);

        history.startAnimation(textTransition);

        String equation = display.getText().toString().trim();
        Double result = ExpressionEvaluation.expressionEvaluation(equation);

        if (Double.isNaN(result)) {
            display.setText("Syntax Error");
        } else {
            history.setText(equation);

            if (String.valueOf(result).contains("E")) {
                display.setText(result.toString());
            }
            else if (result == Math.round(result))
            {
                String res = result.toString();
                display.setText(res.substring(0, res.length() - 2));;
            } else
                display.setText(result.toString());

        }

        resultShown = true;

    }

    /**
     * Handles decimal point button click.
     * @param view: default
     */
    public void dotClick(View view) {
        TextView display = findViewById(R.id.display);
        String dataOnDisplay = display.getText().toString();
        int len = dataOnDisplay.length();
        char lastChar = dataOnDisplay.charAt(len - 1);

        if (resultShown) {
            resultShown = false;
            display.setText("0.");
        } else {
            if ((lastChar >= '1'
                    && lastChar <= '9')
                    || lastChar == '0')
                display.setText(dataOnDisplay + ".");
            else if (lastChar == '+'
                    || lastChar == '-'
                    || lastChar == '\u00d7' // \u00d7 = multiplication character in unicode
                    || lastChar == '\u00f7') // // \u00f7 = multiplication character in unicode
                display.setText(dataOnDisplay + "0.");

        }
    }
}
