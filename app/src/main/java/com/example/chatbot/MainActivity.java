package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatsrv;
    private EditText userMsgEdt;
    private FloatingActionButton fab;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal> chatsModalArrayList;
    private ChatBotAdapter chatBotAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatsrv = findViewById(R.id.idRVChats);
        userMsgEdt = findViewById(R.id.idEdtMessage);
        fab = findViewById(R.id.idFABSend);
        chatsModalArrayList = new ArrayList<>();
        chatBotAdapter = new ChatBotAdapter(chatsModalArrayList, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatsrv.setLayoutManager(manager);
        chatsrv.setAdapter(chatBotAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userMsgEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });
    }
//    private void getResponse(String message){
//        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
//        chatBotAdapter.notifyDataSetChanged();
//
//        String url = "http://api.brainshop.ai/get?bid=177056&key=rcSxeCvVoSXyUwjg&uid=your_user_id&msg=" + message;
//        String BASE_URL = "http://api.brainshop.ai/";
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
//        Call<MsgModal> call = retrofitAPI.getMessage(url);
//        call.enqueue(new Callback<MsgModal>() {
//            @Override
//            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
//                if(response.isSuccessful()){
//                    MsgModal modal = response.body();
//                    chatsModalArrayList.add(new ChatsModal(modal.cnt, BOT_KEY));
//                    chatBotAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MsgModal> call, Throwable t) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        chatsModalArrayList.add(new ChatsModal("Please revert your question", BOT_KEY));
//                        chatBotAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//
//        });
//
//    }

    private void getResponse(String message) {
        chatsModalArrayList.add(new ChatsModal(message, USER_KEY));
        chatBotAdapter.notifyDataSetChanged();

        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // Construct the proper URL with query parameters
        Call<MsgModal> call = retrofitAPI.getMessage("get"+ "177056"+ "rcSxeCvVoSXyUwjg"+ "your_user_id"+ message);

        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MsgModal modal = response.body();
                    chatsModalArrayList.add(new ChatsModal(modal.cnt, BOT_KEY));
                    chatBotAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatsModalArrayList.add(new ChatsModal("Please revert your question", BOT_KEY));
                        chatBotAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}



