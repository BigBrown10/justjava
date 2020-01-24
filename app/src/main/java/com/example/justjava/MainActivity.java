package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity== 100) {
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view){
        if (quantity==1) {
            Toast.makeText(this, "you can't have less than 1 cup coffee",Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
//        Checks if there is whipped cream.
        CheckBox whippedCreamCheckbox = findViewById(R.id.whippedCreamCheckbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
//        checks if there is chocolate.
        CheckBox choco = findViewById(R.id.chocolateCheckbox);
        boolean extraToppings = choco.isChecked();
//        adds edit text for name.
        EditText editText = findViewById(R.id.nameEditText);
        String theText = editText.getText().toString();
        int price = calculatePrice(hasWhippedCream, extraToppings);
        String priceMessage = createOrderSummary(theText, price, hasWhippedCream, extraToppings);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only works for sending messages to emails
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order for " + editText);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     */
    public int calculatePrice(boolean addWhippedCream, boolean addChocolate){
           int basePrice = 5;
//           add whipped cream topping price
     if(addWhippedCream) {
         basePrice = basePrice + 1;
     }
//     add chocolate topping price
     if (addChocolate) {
         basePrice = basePrice+2;
     }
           return quantity* basePrice;
     }


     private String createOrderSummary(String edit, int price, boolean addWhippedCream,boolean addChocolate){
        String priceMessage ="Name: " + edit;
        priceMessage+= "\nAdd Whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage+= "\n"+getString(R.string.quantity) +": "+ quantity;
        priceMessage +="\nTotal: $" + calculatePrice(addWhippedCream, addChocolate);
      priceMessage +="\n" + getString(R.string.thank_you);
                return priceMessage;
     }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}