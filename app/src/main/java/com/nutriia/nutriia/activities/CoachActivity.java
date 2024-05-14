package com.nutriia.nutriia.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Message;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.MessageAdapter;
import com.nutriia.nutriia.utils.DrawerMenu;
import com.nutriia.nutriia.utils.NavBarListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CoachActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach);

        // DrawerMenu.init(this);
        NavBarListener.init(this, R.id.navbar_coach);

        messageList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);


        // Set up the RecyclerView
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        sendButton.setOnClickListener((v) -> {
            String question = editText.getText().toString().trim();
            addToChat(question, Message.SENT_BY_ME);
            callAPI(question);
        });
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, Message.SENT_BY_BOT);
    }

    void callAPI(String question) {

        messageList.add(new Message("Écrit...", Message.SENT_BY_BOT));

        JSONObject json = new JSONObject();
        try {
            json.put("model", "text-davinci-003");
            json.put("prompt", question);
            json.put("max_tokens", 4000);
            json.put("temperature", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
