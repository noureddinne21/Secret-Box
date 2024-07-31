package com.nouroeddinne.secretbox;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewImageActivity extends AppCompatActivity {

    ImageView imageView;
    String imageData;
    MyImageViewModel myImageViewModel;


    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imageView2);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.getString("img") != null){

                imageData = extras.getString("img");
                Bitmap bitmap = BitmapFactory.decodeFile(imageData);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }

//                imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageData,0,imageData.length));
            }else {
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            }
        }


//        myImageViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MyImageViewModel.class);
//
//        Bundle extras = getIntent().getExtras();
//
//        if (extras != null) {
//            myImageViewModel.getImageById(extras.getInt("id")).observe(this, new Observer<MyImages>() {
//                @Override
//                public void onChanged(MyImages myImages) {
//                    if (myImages != null){
//                        Thread thread = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                imageView.setImageBitmap(BitmapFactory.decodeByteArray(myImages.getImage(),0,myImages.getImage().length));
//                            }
//                        });
//                        thread.run();
//                    }else {
//                        Toast.makeText(ViewImageActivity.this, "null", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }





    }








































}