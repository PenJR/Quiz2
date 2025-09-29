package com.example.quiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class dice extends AppCompatActivity {

    private Spinner diceCountSpinner;
    private LinearLayout diceContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        diceCountSpinner = findViewById(R.id.diceCountSpinner);
        diceContainer = findViewById(R.id.diceContainer);

        setupSpinner();

        setupRollButton();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupSpinner() {
        // Spinner options 1 to 4 dice
        Integer[] diceOptions = {1, 2, 3, 4};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, diceOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diceCountSpinner.setAdapter(adapter);
    }

    private void setupRollButton() {
        Button rollButton = findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int diceCount = (int) diceCountSpinner.getSelectedItem();
                Random rand = new Random();

                diceContainer.removeAllViews(); // Clear old dice images

                StringBuilder resultText = new StringBuilder();
                for (int i = 0; i < diceCount; i++) {
                    int roll = rand.nextInt(6) + 1;
                    // Append roll to result string
                    resultText.append(roll);
                    if (i < diceCount - 1) {
                        resultText.append(" + ");
                    }

                    // Create ImageView dynamically
                    ImageView diceImage = new ImageView(dice.this);
                    int sizeInDp = 64;
                    final float scale = getResources().getDisplayMetrics().density;
                    int sizeInPx = (int) (sizeInDp * scale + 0.5f);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeInPx, sizeInPx);
                    params.setMargins(8, 0, 8, 0);
                    diceImage.setLayoutParams(params);

                    // Set dice face image
                    switch (roll) {
                        case 1: diceImage.setImageResource(R.drawable.dice1); break;
                        case 2: diceImage.setImageResource(R.drawable.dice2); break;
                        case 3: diceImage.setImageResource(R.drawable.dice3); break;
                        case 4: diceImage.setImageResource(R.drawable.dice4); break;
                        case 5: diceImage.setImageResource(R.drawable.dice5); break;
                        case 6: diceImage.setImageResource(R.drawable.dice6); break;
                    }

                    diceContainer.addView(diceImage);
                }

                TextView diceResult = findViewById(R.id.diceResult);
                diceResult.setText(resultText.toString());
            }
        });
    }
}
