package com.nouroeddinne.secretbox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class AddActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 3;
    EditText editText1,editText2;
    Button buttonSave;
    ImageView imageView;
    Bitmap bitmap;
    MyImageViewModel myImageViewModel;
    private ActivityResultLauncher<String[]> requestMultiplePermissionsLauncher;

    int id;
    String titel,description;

    private final ActivityResultLauncher<Intent> showImage =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                }
            });

    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        try {
                            bitmap = getBitmapFromUri(selectedImageUri);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    bitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(bitmap);
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText1 = findViewById(R.id.editTextText);
        editText2 = findViewById(R.id.editTextText2);
        imageView = findViewById(R.id.img);
        buttonSave = findViewById(R.id.buttonSave);

        myImageViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MyImageViewModel.class);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            myImageViewModel.getImageById(extras.getInt("id")).observe(this, new Observer<MyImages>() {
                @Override
                public void onChanged(MyImages myImages) {
                    if (myImages != null){
                        id = myImages.getImage_id();
                        titel = myImages.getImage_titel();
                        description = myImages.getImage_description();

                        Bitmap bitmap = BitmapFactory.decodeFile(myImages.getImage());
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }

                        editText1.setText(titel);
                        editText2.setText(description);
                    }else {
                        Toast.makeText(AddActivity.this, "null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AddActivity.this)
                        .setTitle("Select Image")
                        .setItems(new CharSequence[]{"Choose from Gallery", "Take a Photo"}, (dialog, which) -> {
                            if (which == 0) {
                                pickImageFromGallery();
                            } else {
                                takePhoto();
                            }
                        })
                        .show();
            }
        });



        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras != null){
                    update();
                }else {
                    add();
                }
            }
        });






















    }




    private void pickImageFromGallery() {

        String permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = android.Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permission = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        }
        if (ContextCompat.checkSelfPermission(AddActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddActivity.this, new String[]{permission}, 1);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        }

    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                cameraLauncher.launch(intent);
            }
        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                if (permissions[0].equals(android.Manifest.permission.READ_MEDIA_IMAGES)) {
                    pickImageFromGallery();
                } else if (permissions[0].equals(Manifest.permission.CAMERA)) {
                    takePhoto();
                }
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void add(){
        if (bitmap!=null){
            MyImages myImages = new MyImages(editText1.getText().toString(),editText2.getText().toString(), saveImageToExternalStorage(bitmap));
            myImageViewModel.incert(myImages);
            Toast.makeText(this,"Data Saved Successfully",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this,"Please select an image",Toast.LENGTH_SHORT).show();
        }
    }


    public void update(){

        MyImages myImages;
        if (bitmap!=null){
            myImages = new MyImages(id,editText1.getText().toString(),editText2.getText().toString(), saveImageToExternalStorage(bitmap));
        }else {
            myImages = new MyImages(id,editText1.getText().toString(),editText2.getText().toString(), saveImageToExternalStorage(bitmap));
        }

        myImageViewModel.update(myImages);
        Toast.makeText(this,"Data Updates Successfully",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ContentResolver contentResolver = getContentResolver();
        try (InputStream inputStream = contentResolver.openInputStream(uri)) {
            return BitmapFactory.decodeStream(inputStream);
        }
    }


    private String saveImageToExternalStorage(Bitmap bitmap) {
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        int name = new Random().nextInt(10_000_000);
        File file = new File(directory,String.copyValueOf((name+".png").toCharArray()));

        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }


























}