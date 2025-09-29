package com.example.quiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
    private ImageView initialDiceImage;
    private Random random = new Random();

    private final int[] diceFaces = {
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        diceCountSpinner = findViewById(R.id.diceCountSpinner);
        diceContainer = findViewById(R.id.diceContainer);
        initialDiceImage = findViewById(R.id.initialDiceImage);

        setupSpinner();
        setupRollButton();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back
            }
        });
    }

    private void setupSpinner() {
        Integer[] options = {1, 2, 3, 4};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diceCountSpinner.setAdapter(adapter);
    }

    private void setupRollButton() {
        Button rollButton = findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int diceCount = (int) diceCountSpinner.getSelectedItem();

                if (initialDiceImage.getVisibility() == View.VISIBLE) {
                    initialDiceImage.setVisibility(View.GONE);
                }

                diceContainer.removeAllViews();
                final TextView diceResult = findViewById(R.id.diceResult);
                diceResult.setText("Rolling...");

                final StringBuilder resultTextBuilder = new StringBuilder();

                for (int i = 0; i < diceCount; i++) {
                    final ImageView diceImage = new ImageView(dice.this);

                    int sizeInDp = 64;
                    float scale = getResources().getDisplayMetrics().density;
                    int sizeInPx = (int) (sizeInDp * scale + 0.5f);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeInPx, sizeInPx);
                    params.setMargins(8, 0, 8, 0);
                    diceImage.setLayoutParams(params);

                    // Set initial general dice image
                    diceImage.setImageResource(R.drawable.dicegeneral);
                    diceContainer.addView(diceImage);

                    // Generate final roll and color
                    final int rolledNumber = random.nextInt(6) + 1;
                    final int tintColor = getRandomColor();

                    // Rotation animation
                    ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(diceImage, "rotation", 0f, 1440f); // 4 full spins
                    rotationAnim.setDuration(800);

                    // When animation ends, show final face
                    final int finalIndex = i;
                    rotationAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) { }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            diceImage.setRotation(0f); // reset rotation
                            diceImage.setImageResource(diceFaces[rolledNumber - 1]);
                            diceImage.setColorFilter(tintColor, PorterDuff.Mode.MULTIPLY);

                            // Update result text
                            resultTextBuilder.append(rolledNumber);
                            if (finalIndex < diceCount - 1) {
                                resultTextBuilder.append(" + ");
                            }
                            if (finalIndex == diceCount - 1) {
                                diceResult.setText(resultTextBuilder.toString());
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) { }

                        @Override
                        public void onAnimationRepeat(Animator animation) { }
                    });

                    // Start rotation animation
                    rotationAnim.start();
                }
            }
        });
    }

    private int getRandomColor() {
        int r = random.nextInt(156) + 100;
        int g = random.nextInt(156) + 100;
        int b = random.nextInt(156) + 100;
        return Color.rgb(r, g, b);
    }
}
