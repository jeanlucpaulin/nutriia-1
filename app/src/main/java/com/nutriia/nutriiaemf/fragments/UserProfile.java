package com.nutriia.nutriiaemf.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.interfaces.OnUserProfileChanged;
import com.nutriia.nutriiaemf.resources.Settings;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserProfile extends AppFragment {
    private View view;
    private Spinner spinnerAge;
    private Spinner spinnerGender;
    private EditText userWeight;
    private EditText userHeight;
    private TextView userBodyMassIndex;
    private TextView userBodyMassIndexUnit;
    private LinearLayout imcContainer;
    private TextView userIdealWeightData;
    private TextView metabolismData;
    private Button validateButton;
    private int positionSpinnerAge = -1;
    private int positionSpinnerGender = -1;

    private OnUserProfileChanged onUserProfileChanged;

    private Context context;

    public UserProfile(OnUserProfileChanged onUserProfileChanged) {
        super();
        this.onUserProfileChanged = onUserProfileChanged;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void create(FrameLayout frameLayout) {
        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.component_informations, frameLayout, false);

        userWeight = view.findViewById(R.id.weight_data);
        userHeight = view.findViewById(R.id.height_data);
        userBodyMassIndex = view.findViewById(R.id.body_mass_index_data);
        userBodyMassIndexUnit = view.findViewById(R.id.body_mass_index_unit);
        imcContainer = view.findViewById(R.id.imc_container);
        userIdealWeightData = view.findViewById(R.id.ideal_weight_data);
        metabolismData = view.findViewById(R.id.basal_metabolism_data);
        validateButton = view.findViewById(R.id.validate_button);

        spinnerAge = view.findViewById(R.id.age_data);
        List<String> ages = new ArrayList<>();
        for (int i = Settings.getMinimumAge(); i <= Settings.getMaximumAge(); i++) {
            ages.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterAge = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, ages);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapterAge);

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionSpinnerAge = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                positionSpinnerAge = -1;
            }
        });

        spinnerGender = view.findViewById(R.id.sex_data);
        List<String> genders = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.genders)));
        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, genders);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGender);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionSpinnerGender = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                positionSpinnerGender = -1;
            }
        });


        UserSharedPreferences sharedPreferences = UserSharedPreferences.getInstance(getContext());

        updateFields(sharedPreferences);

        validateButton.setOnClickListener(v -> {
            closeKeyboard();
            saveFields();
        });
        ImageButton infoButtonIdealWeight = view.findViewById(R.id.info_button_ideal_weight);
        infoButtonIdealWeight.setOnClickListener(v -> {
            String message = getContext().getString(R.string.ideal_weight_formula);
            showCustomDialog(message);
        });

        ImageButton infoButtonBasalMetabolism = view.findViewById(R.id.info_button_basal_metabolism);
        infoButtonBasalMetabolism.setOnClickListener(v -> {
            String message = getContext().getString(R.string.basal_metabolism_formula);
            showCustomDialog(message);
        });

        frameLayout.addView(view);
    }

    private void updateFields(UserSharedPreferences userSharedPreferences)
    {
        if(userSharedPreferences.isUserWeightDefined()) {
            String userWeightText = String.valueOf(userSharedPreferences.getWeight());
            userWeight.setText(userWeightText);
        }

        if(userSharedPreferences.isUserHeightDefined()) {
            String userHeightText = String.valueOf(userSharedPreferences.getHeight());
            userHeight.setText(userHeightText);
        }

        if(userSharedPreferences.isUserAgeDefined()) {
            int userAge = userSharedPreferences.getAge();
            spinnerAge.setSelection(userAge - Settings.getMinimumAge());
        }

        if(userSharedPreferences.isUserGenderDefined()) {
            int userGender = userSharedPreferences.getGender();
            spinnerGender.setSelection(userGender);
        }

        if(userSharedPreferences.isUserWeightDefined() && userSharedPreferences.isUserHeightDefined()) {
            double userHeightInMeters = userSharedPreferences.getHeight() / 100.0;
            double userBodyMassIndexValue = userSharedPreferences.getWeight() / (userHeightInMeters * userHeightInMeters);
            String userBodyMassIndexUnitText = String.valueOf((int) Math.round(userBodyMassIndexValue));

            String userBodyMassIndexText;
            int backgroundColor = R.color.lime;
            String[] imcValues = context.getResources().getStringArray(R.array.imc_indicator);
            if (userBodyMassIndexValue < 16.5) {
                userBodyMassIndexText = imcValues[0];
                backgroundColor = R.color.IMC1;
            } else if (userBodyMassIndexValue < 18.5) {
                userBodyMassIndexText = imcValues[1];
                backgroundColor = R.color.IMC2;
            } else if (userBodyMassIndexValue < 25) {
                userBodyMassIndexText = imcValues[2];
                backgroundColor = R.color.IMC3;
            } else if (userBodyMassIndexValue < 30) {
                userBodyMassIndexText = imcValues[3];
                backgroundColor = R.color.IMC4;
            } else if (userBodyMassIndexValue < 35) {
                userBodyMassIndexText = imcValues[4];
                backgroundColor = R.color.IMC5;
            } else if (userBodyMassIndexValue < 40) {
                userBodyMassIndexText = imcValues[5];
                backgroundColor = R.color.IMC6;
            } else {
                userBodyMassIndexText = imcValues[6];
                backgroundColor = R.color.IMC7;
            }

            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context, backgroundColor));
            imcContainer.setBackgroundTintList(colorStateList);
            userBodyMassIndexUnit.setText(userBodyMassIndexUnitText);
            userBodyMassIndex.setText(userBodyMassIndexText);
        }

        if(userSharedPreferences.isUserGenderDefined() && userSharedPreferences.isUserHeightDefined()) {
            double divier = userSharedPreferences.getGender() == 0 ? 4.0 : 2.5;
            float idealWeight= (float) ((userSharedPreferences.getHeight() - 100) - (userSharedPreferences.getHeight() - 150)/divier);
            idealWeight = Math.round(idealWeight);
            userIdealWeightData.setText(String.valueOf(idealWeight).concat(" kg"));
        }

        if(userSharedPreferences.isUserGenderDefined() && userSharedPreferences.isUserHeightDefined() && userSharedPreferences.isUserAgeDefined()) {
            double multiplier = userSharedPreferences.getGender() == 0 ? 1.083 : 0.963;
            float height = (float) userSharedPreferences.getHeight()/100;
            int weight = userSharedPreferences.getWeight();
            double metabolism = (multiplier * Math.pow(weight, 0.48) * Math.pow(height, 0.5) * Math.pow(userSharedPreferences.getAge(), -0.13)) * 1000 / 4.18;
            metabolismData.setText(String.valueOf((int) metabolism).concat(" kcal"));
        }

    }

    private void saveFields() {
        UserSharedPreferences sharedPreferences = UserSharedPreferences.getInstance(context);

        boolean changed = false;

        if(!userWeight.getText().toString().isEmpty()) {
            try {
                int weight = Integer.parseInt(userWeight.getText().toString());
                if(sharedPreferences.getWeight() != weight) {
                    sharedPreferences.setWeight(weight);
                    changed = true;
                }
            } catch (NumberFormatException ignored) {}
        }

        if(!userHeight.getText().toString().isEmpty()) {
            try {
                int height = Integer.parseInt(userHeight.getText().toString());
                if (sharedPreferences.getHeight() != height) {
                    sharedPreferences.setHeight(height);
                    changed = true;
                }
            } catch (NumberFormatException ignored) {}
        }

        if(positionSpinnerAge != -1) {
            if(positionSpinnerAge + Settings.getMinimumAge() != sharedPreferences.getAge()) {
                sharedPreferences.setAge(positionSpinnerAge + Settings.getMinimumAge());
                changed = true;
            }
        }

        if(positionSpinnerGender != -1) {
            if(positionSpinnerGender != sharedPreferences.getGender()) {
                sharedPreferences.setGender(positionSpinnerGender);
                changed = true;
            }
        }

        if(changed) {
            updateFields(sharedPreferences);
            if(onUserProfileChanged != null) {
                onUserProfileChanged.onUserProfileChanged();
            }
        }
    }

    private void showCustomDialog(String message) {
        ((Activity) context).runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.RoundedAlertDialog);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View layout = inflater.inflate(R.layout.dialog_layout, null);

            TextView textView = layout.findViewById(R.id.dialog_text);
            textView.setText(message);

            Button btnOk = layout.findViewById(R.id.btn_ok);
            btnOk.setVisibility(View.VISIBLE);

            builder.setView(layout);
            AlertDialog dialog = builder.create();

            btnOk.setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.show();
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  // Adjusting the dialog width to match the content width
        });
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
