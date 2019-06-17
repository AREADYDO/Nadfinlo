package com.nsz.Nadfinlo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.nsz.Nadfinlo.*;

import com.nsz.Nadfinlo.util.Constant;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.util.QrCodeGenerator;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        Button bt_click = (Button) findViewById(R.id.button);
        bt_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Main2Activity.class);

                startActivity(intent);
            }
        });

        ImageView button = (ImageView) findViewById(R.id.imageView3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);*/

                Intent toast;
                toast = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(toast);

            }
        });



    }

}
