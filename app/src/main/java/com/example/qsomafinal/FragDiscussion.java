package com.example.qsomafinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Date;


public class FragDiscussion extends Fragment{
    private static  int SIGN_IN_REQUEST_CODE = 1;
    private Button btnTEST;
    private FirebaseListAdapter<ChatMessage> adapter;
    private FirebaseAuth mAuth;
    public EditText message;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discussion, container, false);
        message = view.findViewById(R.id.input);

       if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            // User is already signed in. Therefore, display


            // Load chat room contents
           // ListView listOfMessages = (ListView) view.findViewById(R.id.list_of_messages);

           // adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
             //       R.layout.messages, FirebaseDatabase.getInstance().getReference())
            Query query = FirebaseDatabase.getInstance().getReference().child("chats");

            FirebaseListOptions<ChatMessage> options =
                    new FirebaseListOptions.Builder<ChatMessage>()
                            .setQuery(query, ChatMessage.class)
                            .setLayout(android.R.layout.simple_list_item_1)
                            .build();
            adapter = new FirebaseListAdapter<ChatMessage>(options){
                @Override
                protected void populateView(View v, ChatMessage model, int position) {
                    // Get references to the views of message.xml
                    TextView messageText = (TextView)v.findViewById(R.id.message_text);
                    TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                    // Set their text
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());

                    // Format the date before showing it
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            model.getMessageTime()));

                }


            //listOfMessages.setAdapter(adapter);

        };


        @SuppressLint("WrongViewCast") ImageButton fab = (ImageButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(message.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                // Clear the input
                message.setText("");
            }
        });



    }

        return view;
    }



}

