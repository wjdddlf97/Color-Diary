package com.example.colordiaryexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DiaryDetailActivity extends AppCompatActivity {

    ImageView diaryImage;
    TextView text_title;
    TextView text_emotion;
    TextView text_date;
    TextView text_content;
    Button btn_change;
    Button btn_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_diary);
        Intent intent = getIntent();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();

        String title = intent.getExtras().getString("title");
        String time = intent.getExtras().getString("time");
        String emotion = intent.getExtras().getString("emotion");
        String content = intent.getExtras().getString("content");
        String date = intent.getExtras().getString("date");
        String birthday = intent.getExtras().getString("birthday");

        text_title = findViewById(R.id.text_title);
        text_emotion = findViewById(R.id.text_emotion);
        text_date = findViewById(R.id.text_date);
        text_content = findViewById(R.id.text_content);
        diaryImage = findViewById(R.id.diaryImage);
        btn_change =findViewById(R.id.btn_change);
        btn_end =findViewById(R.id.btn_end);

        text_title.setText(title);
        text_emotion.setText(emotion);
        text_date.setText(time);
        text_content.setText(content);

        StorageReference islandRef = storageRef.child("diarys/"+user.getUid() + "/"+ title);
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                diaryImage.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });


        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndActivity();
            }
        });


    }


    private void startToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void ChangeActivity() {
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        String time = intent.getExtras().getString("time");
        String emotion = intent.getExtras().getString("emotion");
        String content = intent.getExtras().getString("content");
        String date = intent.getExtras().getString("date");
        String birthday = intent.getExtras().getString("birthday");
        Intent intent2 = new Intent(this, ChangeUpdateActivity.class);
        intent2.putExtra("title", title);
        intent2.putExtra("emotion", emotion);
        intent2.putExtra("content", content);
        intent2.putExtra("time", time);
        intent2.putExtra("date",date);
        intent2.putExtra("birthday",birthday);
        startActivity(intent2);
        finish();
    }
    private void EndActivity() {
        finish();
    }
}