package com.nutriia.nutriia.fragments;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nutriia.nutriia.Day;
import com.nutriia.nutriia.Meal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.builders.DayBuilder;
import com.nutriia.nutriia.interfaces.OnValidateDay;
import com.nutriia.nutriia.resources.Translator;
import com.nutriia.nutriia.user.Saver;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MyRealDay extends AppFragment implements OnValidateDay {

    List<Meal> meals;

    private View view;

    private Button validateButton;

    private OnValidateDay onValidateDay;

    private Context context;



    public MyRealDay(OnValidateDay onValidateDay) {
        this.onValidateDay = onValidateDay;
    }

    @Override
    public void create(FrameLayout frameLayout) {
        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.componenent_my_real_day, frameLayout, false);

        meals = new ArrayList<>(Arrays.asList(new Meal("Breakfast"), new Meal("Lunch"), new Meal("Dinner"), new Meal("Snack")));

        int[] viewIds = new int[] {R.id.breakfast, R.id.lunch, R.id.dinner, R.id.snack};

        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(context);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        String date = userSharedPreferences.getMRDDate();

        if(!currentDate.equals(date) && !date.isEmpty()) userSharedPreferences.clearMRD();

        for(int i = 0; i < viewIds.length; i++) {
            TextView textView = view.findViewById(viewIds[i]).findViewById(R.id.textView);
            EditText editText = view.findViewById(viewIds[i]).findViewById(R.id.editText);

            StringBuilder content = new StringBuilder();

            List<String> dishes = getDishes(viewIds[i]);

            for (int j = 0; j < dishes.size(); j++) {
                content.append(dishes.get(j)).append(j == dishes.size()-1 ? "" : "\n");
            }

            editText.setText(content.toString());
            textView.setText(Translator.translate(meals.get(i).getName()));

        }

        validateButton = view.findViewById(R.id.validateButton);

        for(int i = 0; i < viewIds.length; i++) {

            EditText editText = view.findViewById(viewIds[i]).findViewById(R.id.editText);
            ImageButton deleteButton = view.findViewById(viewIds[i]).findViewById(R.id.imageButton);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        deleteButton.setImageResource(R.drawable.baseline_close_30_red);
                        deleteButton.setEnabled(true);
                    } else {
                        deleteButton.setEnabled(false);
                        deleteButton.setImageResource(R.drawable.baseline_close_30);
                    }
                }
            });

            deleteButton.setOnClickListener(v -> {
                editText.setText("");
            });
        }

        validateButton.setOnClickListener(v -> {
            validateButton.setEnabled(false);
            closeKeyboard();
            Map<String, Set<String>> userInput = new HashMap<>();
            boolean send = false;
            int empty = 0;

            for(int viewId : viewIds) {
                TextView textView = view.findViewById(viewId).findViewById(R.id.textView);
                EditText editText = view.findViewById(viewId).findViewById(R.id.editText); // Define EditText here

                Set<String> inputs = new HashSet<>();
                List<String> dishes = getDishes(viewId);

                if(viewId == R.id.breakfast) {
                    String text = editText.getText().toString();
                    if(!text.isEmpty()) inputs = Saver.saveMRDInputBreakfast(context, text);
                    else empty++;
                }
                else if(viewId == R.id.lunch) {
                    String text = editText.getText().toString();
                    if(!text.isEmpty()) inputs = Saver.saveMRDInputLunch(context, text);
                    else empty++;
                }
                else if(viewId == R.id.dinner) {
                    String text = editText.getText().toString();
                    if(!text.isEmpty()) inputs = Saver.saveMRDInputDinner(context, text);
                    else empty++;
                }
                else if(viewId == R.id.snack){
                    String text = editText.getText().toString();
                    if(!text.isEmpty()) inputs = Saver.saveMRDInputSnack(context, text);
                    else empty++;
                }

                userInput.put(textView.getText().toString().toLowerCase(), inputs);

                if(!send && !inputEquals(dishes, new ArrayList<>(inputs))) send = true;
            }

            if(empty == 4) {
                showCustomToast("Veuillez renseigner au moins un repas", Toast.LENGTH_SHORT);
                validateButton.setEnabled(true);
                userSharedPreferences.clearMRD();
                onValidateDay.onValidateDayResponse(new DayBuilder().buildOnlyWithGoal(userSharedPreferences));
            }
            else if(send) {

                if(onValidateDay != null) {
                    showCustomToast("Comparaison de votre journée en cours...", Toast.LENGTH_LONG);
                    onValidateDay.onValidateDayButtonClick(userInput);
                }
            }
            else {
                Log.d("MyRealDay", "No changes to send");
                showCustomToast("Journée déjà validée...", Toast.LENGTH_SHORT);
                validateButton.setEnabled(true);
            }
        });

        frameLayout.addView(view);
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private List<String> getDishes(int id) {
        List<String> dishes = new ArrayList<>();
        if(id == R.id.breakfast) {
            dishes = new ArrayList<>(UserSharedPreferences.getInstance(context).getMRDABreakfast());
        }
        else if(id == R.id.lunch) {
            dishes = new ArrayList<>(UserSharedPreferences.getInstance(context).getMRDALunch());
        }
        else if(id == R.id.dinner) {
            dishes = new ArrayList<>(UserSharedPreferences.getInstance(context).getMRDADinner());
        }
        else if(id == R.id.snack) {
            dishes = new ArrayList<>(UserSharedPreferences.getInstance(context).getMRDASnack());
        }
        return dishes;
    }

    private boolean inputEquals(List<String> list1, List<String> list2) {
        if(list1.size() != list2.size()) return false;
        for(int i = 0; i < list1.size(); i++) {
            if(!list1.get(i).equals(list2.get(i))) return false;
        }
        return true;
    }

    private void showCustomToast(String message, int duration) {
        ((Activity) context).runOnUiThread(() -> {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, ((Activity) context).findViewById(R.id.toast_layout_root));

            TextView textView = layout.findViewById(R.id.toast_text);
            textView.setText(message);

            Toast toast = new Toast(context);
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();
        });
    }

    @Override
    public void onValidateDayButtonClick(Map<String, Set<String>> userInput) {
        return;
    }

    @Override
    public void onValidateDayResponse(Day day) {
        if(day == null) {
            showCustomToast("Erreur lors de la comparaison de votre journée", Toast.LENGTH_SHORT);
        }
        validateButton.setEnabled(true);
    }
}
