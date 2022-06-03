package com.example.mypractic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    List<Client> listClients = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Clients");

        
        Intent intent = new Intent(this, ListItems.class);
        findViewById(R.id.viewClient).setOnClickListener(v -> startActivity(intent));

        EditText name = findViewById(R.id.Name);
        EditText email = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);

        Map<String, Client> items = new HashMap<>();

        final long[] totalRecords = {0};

        myRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalRecords[0] = dataSnapshot.getChildrenCount();

                for (DataSnapshot ds : dataSnapshot.getChildren())
                    items.put(ds.getKey().toString(), ds.getValue(Client.class));
            }

            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        findViewById(R.id.addClient).setOnClickListener(v -> {
            items.put("Client" + totalRecords[0], new Client(name.getText().toString(),
                    email.getText().toString(), password.getText().toString()));

            myRef.setValue(items);

            name.setText("");
            email.setText("");
            password.setText("");

            Toast.makeText(MainActivity.this, "Данные добавлены!", Toast.LENGTH_LONG).show();
        });
    }
}