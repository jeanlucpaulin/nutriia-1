package com.example.nutriia1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IMCActivity extends AppCompatActivity {

    private EditText weightEditText;
    private EditText heightEditText;
    private Button calculateButton;
    private TextView imcResultTextView;
    private TextView bmiDescriptionTextView;
    private RadioGroup genderRadioGroup;
    private Button idealWeightButton;
    private TextView idealWeightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        calculateButton = findViewById(R.id.calculateButton);
        imcResultTextView = findViewById(R.id.imcResultTextView);
        bmiDescriptionTextView = findViewById(R.id.bmiDescriptionTextView);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        idealWeightButton = findViewById(R.id.idealWeightButton);
        idealWeightTextView = findViewById(R.id.idealWeightTextView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weightText = weightEditText.getText().toString();
                String heightText = heightEditText.getText().toString();

                double weight = Double.parseDouble(weightText);
                double height = Double.parseDouble(heightText);

                double bmi = weight / (height * height);

                // Changez la couleur du texte en fonction de l'IMC
                if (bmi < 25) {
                    imcResultTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    bmiDescriptionTextView.setText(getString(R.string.bmi_description_low));
                } else if (bmi >= 25 && bmi <= 30) {
                    imcResultTextView.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                    bmiDescriptionTextView.setText(getString(R.string.bmi_description_medium));
                } else {
                    imcResultTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    bmiDescriptionTextView.setText(getString(R.string.bmi_description_high));
                }

                imcResultTextView.setText(String.format("%.2f", bmi));
            }
        });

        idealWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedGender = findViewById(selectedGenderId);

                double height = Double.parseDouble(heightEditText.getText().toString());

                double idealWeight;

                if (selectedGender == null || height == 0) {
                    idealWeight = 0;
                } else if (selectedGender.getText().equals("Homme")) {
                    idealWeight = 22 * height * height;
                } else {
                    idealWeight = 21 * height * height;
                }

                idealWeightTextView.setText(String.format(getString(R.string.ideal_weight_result), idealWeight));
            }
        });
    }
}
