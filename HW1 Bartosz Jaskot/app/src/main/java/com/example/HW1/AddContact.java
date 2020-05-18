package com.example.HW1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.HW1.tasks.TaskList;

import java.util.Random;

public class AddContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
    }

    public void Save(View view) {
        EditText name = findViewById    ( R.id.editText );
        EditText surname = findViewById ( R.id.editText2 );
        EditText birthday = findViewById ( R.id.editText3 );
        EditText number = findViewById ( R.id.editText4 );
        if(!name.getText ().toString ().equals ( "" ) && !surname.getText ().toString ().equals ( "" ) && !number.getText ().toString ().equals ( "" ))
        {
            Random random = new Random ( );
            String selectedImage;
            Intent intent = getIntent ();
            selectedImage = intent.getStringExtra ( "photo");
            if (intent.getStringExtra ( "photo" )==null) {
                selectedImage="drawable "+(random.nextInt (3)+1);
            }
            TaskList.addItem ( new TaskList.Task ( "Task." + TaskList.ITEMS.size ()+1, name.getText ().toString (), number.getText ().toString (),surname.getText ().toString (),birthday.getText ().toString () ) );
            Intent main_intent=new Intent ( );
            setResult ( RESULT_OK, main_intent );
            finish ();
        }
    }
}
