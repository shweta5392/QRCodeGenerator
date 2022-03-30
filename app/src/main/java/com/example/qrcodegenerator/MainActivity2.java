package com.example.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {
    ConstraintLayout imageEncrypt, textEncrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageEncrypt = findViewById(R.id.img_encrypt);
        textEncrypt = findViewById(R.id.text_encrypt);

        textEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent password_based_encryption = new Intent(MainActivity2.this, PasswordBasedEncryption.class);
                startActivity(password_based_encryption);
            }
        });

        imageEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image_based_encryption = new Intent(MainActivity2.this, ImageBasedEncryption.class);
                startActivity(image_based_encryption);
            }
        });

    }
}