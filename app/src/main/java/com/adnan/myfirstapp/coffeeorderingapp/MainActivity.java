package com.adnan.myfirstapp.coffeeorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }


    public void decrement(View view) {
        if(quantity == 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "You cannot order less than 1 cup of coffee!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    private void displayQuantity(int quantity) {
        TextView displayQuantityTextView = findViewById(R.id.quantity_text_view);
        displayQuantityTextView.setText(String.valueOf(quantity));
    }

    public void increment(View view) {
        if(quantity == 50) {
            Toast toast = Toast.makeText(getApplicationContext(), "You cannot order more than 50 cup of coffee!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    public void submitOrder(View view) {

        EditText nameField = findViewById(R.id.name_field);
        EditText addressField = findViewById(R.id.address_field);
        String name = nameField.getText().toString();
        String address = addressField.getText().toString();
        CheckBox whippledCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean isWhippedCreamChecked = whippledCreamCheckBox.isChecked();
        boolean isChocolateChecked = chocolateCheckBox.isChecked();

        int price = calculatePrice(isWhippedCreamChecked, isChocolateChecked);

        String orderSummary = createOrderSummary(name, address, isWhippedCreamChecked, isChocolateChecked, price);

        displayOrderSummary(orderSummary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);

        if(intent.resolveActivity(getPackageManager())!=null) {
            startActivity(intent);
        }

    }

    private void displayOrderSummary(String orderSummary) {
        TextView summaryTextView = findViewById(R.id.summary_text_view);
        summaryTextView.setText(orderSummary);
    }

    private String createOrderSummary(String name, String address, boolean isWhippedCreamChecked, boolean isChocolateChecked, int price) {

        String summary = "Name: " + name + "\n" +
                         "Address: " + address + "\n" +
                         "Is Whipped Cream Checked! " + isWhippedCreamChecked + "\n" +
                         "Is Chocolate Checked! " + isChocolateChecked + "\n" +
                         "Total Price = " + price;
        return  summary;
    }


    private int calculatePrice(boolean isWhippedCreamChecked, boolean isChocolateChecked) {
        int initialPrice = 25;
        if(isWhippedCreamChecked) {
            initialPrice = initialPrice + 10;
        }
        if(isChocolateChecked) {
            initialPrice = initialPrice + 5;
        }
        initialPrice = initialPrice * quantity;
        return initialPrice;
    }
}