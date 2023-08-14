package com.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatBotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ChatsModal> chatsModalArrayList = new ArrayList<>();
    Context context;

    public ChatBotAdapter(ArrayList<ChatsModal> chatsModalArrayList, Context context) {
        this.chatsModalArrayList = chatsModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg, parent, false);
                return new UserViewholder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg, parent, false);
                return new BotViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatsModal chatsModal = chatsModalArrayList.get(position);
        switch (chatsModal.getSender()){
            case "user":
                if (holder instanceof UserViewholder) {
                    ((UserViewholder) holder).idTVUser.setText(chatsModal.getMessage());
                }
                break;
            case "bot":
                if (holder instanceof BotViewHolder) {
                    ((BotViewHolder) holder).idTVBot.setText(chatsModal.getMessage());
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        return chatsModalArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatsModal chatsModal = chatsModalArrayList.get(position);
        return chatsModal.getSender().equals("user") ? 0 : 1;
    }


    public static class UserViewholder extends  RecyclerView.ViewHolder{
        TextView idTVUser;
        public UserViewholder(@NonNull View itemView) {
            super(itemView);
            idTVUser = itemView.findViewById(R.id.idTVUser);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView idTVBot;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            idTVBot = itemView.findViewById(R.id.idTVBot);
        }
    }
}
