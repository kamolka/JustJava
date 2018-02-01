/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */


    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);}

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        // Figure out if the user wants whipped cream topping
       CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
       boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
       CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
      boolean hasChocolate = chocolateCheckBox.isChecked();

       EditText name = findViewById(R.id.plain_text_input);
       Editable getText = name.getText();

        // Calculate the price

      int price = calculatePrice(hasWhippedCream,hasChocolate);
        // Display the order summary on the screen
       String message = createOrderSummary(getText, price, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + getText);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }





        }



    /**
     * Calculates the price of the order.
     *
     * @return total price
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
// Price for 1 cup of coffee
        int basePrice = 5;

        // Add 1$ if the user wants whipped cream
        if (addWhippedCream) {

            basePrice = basePrice + 1;
        }

        //Add 2$ if the user wants chocolate
        if (addChocolate) {

            basePrice = basePrice + 2;
        }
        //Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(Editable name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name)+" " + name;
        priceMessage +="\n" + getString(R.string.order_summary_whipped_cream)+" " + addWhippedCream;
        priceMessage += "\n "+ getString(R.string.order_summary_chocolate)+" " + addChocolate;
        priceMessage += "\n"+ getString(R.string.order_summary_quantity)+ " "  + quantity;
        priceMessage += "\n" + getString(R.string.order_summary_price)+" " + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

}