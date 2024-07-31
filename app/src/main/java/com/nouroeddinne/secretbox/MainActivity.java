package com.nouroeddinne.secretbox;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    public static final String FILE_Text = "SECRET_TEXT";
//    public static final String FILE_IMG = "SECRET_IMG";
//    public static final String FILE_TEXT_EX= "SECRET_TEXT.txt";
//    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_CAMERA_CAPTURE = 2;
    private static final int REQUEST_PERMISSIONS = 3;
    EditText editText;
    Button buttonSave,buttonGet,buttonSaveEx,buttonGetEx;
    ImageView imageView;
    Bitmap bitmap;
//    String allData ="";
//    String line="";

    MyImageViewModel myImageViewModel;


//    private final ActivityResultLauncher<Intent> galleryLauncher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    Uri selectedImageUri = result.getData().getData();
//                    imageView.setImageURI(selectedImageUri);
//                }
//            });

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editTextText);
        buttonSave = findViewById(R.id.button);
        buttonGet = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.baseline_photo_camera_24);


        myImageViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MyImageViewModel.class);
        myImageViewModel.getAllImages().observe(MainActivity.this, new Observer<List<MyImages>>() {
            @Override
            public void onChanged(List<MyImages> imagesList) {
                for (MyImages myImage : imagesList) {
                    Log.d("TAG", "onChanged: "+myImage.getImage_id()+" " + myImage.getImage()+" "+myImage.getImage_description()+" "+myImage.getImage());
                }
            }
        });


//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION) != PackageManager.PERMISSION_GRANTED){
//
//            Toast.makeText(this, "if", Toast.LENGTH_SHORT).show();
//            String [] permissions = {Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION};
//            ActivityCompat.requestPermissions(this,permissions,1);
//
//        }else {
//            Toast.makeText(this, "else", Toast.LENGTH_SHORT).show();
//        }


//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION)) {
//        } else {
//
//            }
//        }



//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try {
//                    FileOutputStream fost = openFileOutput(FILE_Text,MODE_PRIVATE);
//                    //FileOutputStream fosi = openFileOutput(FILE_IMG,MODE_PRIVATE);
//                    PrintWriter pwt = new PrintWriter(fost);
//                    //PrintWriter pwi = new PrintWriter(fosi);
//                    pwt.println(editText.getText().toString());
//                    //pwi.println(b);
//                    editText.setText("");
//
//                    Log.d("Bitmap", "Bitmap "+b.toString());
//
//                    imageView.setImageResource(R.drawable.baseline_photo_camera_24);
//                    b=null;
//                    Toast.makeText(MainActivity.this, "Save", Toast.LENGTH_SHORT).show();
//                    pwt.close();
//                    //pwi.close();
//                    fost.close();
//                    //fosi.close();
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        });


//        buttonGet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try {
//                    FileInputStream fost = openFileInput(FILE_Text);
//                    //FileInputStream fosi = openFileInput(FILE_IMG);
//                    InputStreamReader isrt = new InputStreamReader(fost);
//                    //InputStreamReader isri = new InputStreamReader(fosi);
//                    BufferedReader brt = new BufferedReader(isrt);
//                    //BufferedReader bri = new BufferedReader(isri);
//                    allData ="";
//                    line="";
//                    while ((line=brt.readLine())!=null){
//                        allData+=line;
//                    }
//                    editText.setText(allData);
//
////                    allData ="";
////                    line="";
////                    while ((line=bri.readLine())!=null){
////                        allData+=line;
////                    }
//
//
//                    //byte[] byteArray = android.util.Base64.decode(allData, android.util.Base64.DEFAULT);
//
//                    // Convert byte array to Bitmap
//                    //b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//
////                    Log.d("Bitmap", "Bitmap "+StringToBitMap(allData));
////                    Log.d("Bitmap", "Bitmap "+allData);
//
//
//                    //imageView.setImageBitmap(StringToBitMap(allData));
//
//                    brt.close();
//                    //bri.close();
//                    fost.close();
//                    //fosi.close();
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        });




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
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
                add();
            }
        });



//        buttonSaveEx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                if (isEXternalStoregWritable()){
////                    File es = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
////                    File file = new File(es,FILE_TEXT_EX);
////                    if (!file.exists()){
////                        try {
////                            file.createNewFile();
////                        } catch (IOException e) {
////                            throw new RuntimeException(e);
////                        }
////                    }else {
////
////                    }
////                }
//
//            }
//        });
//
//
//        buttonGetEx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });



    }



    private void pickImageFromGallery() {

        String permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = android.Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, 1);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        }

    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
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
                if (permissions[0].equals(Manifest.permission.READ_MEDIA_IMAGES)) {
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap!=null){
            bitmap.compress(Bitmap.CompressFormat.PNG,50,baos);
            byte[] data = baos.toByteArray();
            //MyImages myImages = new MyImages("title.getText().toString()","description.getText().toString()", data);
            //myImageViewModel.incert(myImages);
            Toast.makeText(this,"Data Saved Successfully",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Please select an image",Toast.LENGTH_SHORT).show();
        }
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ContentResolver contentResolver = getContentResolver();
        try (InputStream inputStream = contentResolver.openInputStream(uri)) {
            return BitmapFactory.decodeStream(inputStream);
        }
    }

//
//    public String BitMapToString(Bitmap bitmap){
//        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
//        byte [] b=baos.toByteArray();
//        String temp=Base64.encodeToString(b, Base64.DEFAULT);
//        return temp;
//    }
//
//
//    public Bitmap StringToBitMap(String encodedString){
//        try{
//            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
//            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//            return bitmap;
//        }catch(Exception e){
//            e.getMessage();
//            return null;
//        }
//    }
//
//
//    public boolean isEXternalStoregWritable(){
//
//        String status= Environment.getExternalStorageState();
//        if(status.equals(Environment.MEDIA_MOUNTED)){
//            return true;
//        }else{
//            return false;
//        }
//
//    }
//
//
//    public boolean isEXternalStoregReadOnly(){
//
//        String status= Environment.getExternalStorageState();
//        if(status.equals(Environment.MEDIA_MOUNTED_READ_ONLY) || status.equals(Environment.MEDIA_MOUNTED) ){
//            return true;
//        }else{
//            return false;
//        }
//
//    }





























}