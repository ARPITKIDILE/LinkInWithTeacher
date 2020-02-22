package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newapp.ui.main.Notice;
import com.example.newapp.ui.main.NoticeItem;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NoticeActivity extends AppCompatActivity {

    private EditText notice_content1,notice_title1;
    private Button upload1,attachment1;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Uri imageUri;
    private Context context;
    private int image_request = 10,storage_permission  = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ViewsById();
    }

    public void ViewsById(){
        context = NoticeActivity.this;
        notice_title1=(EditText)findViewById(R.id.notice_title1);
        notice_content1=(EditText)findViewById(R.id.notice_content1);
        upload1=(Button)findViewById(R.id.upload1);
        attachment1=(Button)findViewById(R.id.attachment1);
        attachment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadingImage();
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference("Notices");
        databaseReference = FirebaseDatabase.getInstance().getReference("Notices");


    }

    private void UploadingImage(){

        if(imageUri !=null) {
            final String uploadId = databaseReference.push().getKey();
            final StorageReference fileReferences = storageReference.child(uploadId + "." + getFileExtension(imageUri));

            UploadTask uploadTask = fileReferences.putFile(imageUri);


            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Toast.makeText(context,"Download Uri failed",Toast.LENGTH_SHORT).show();
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    Toast.makeText(context,"Download Uri is ready",Toast.LENGTH_SHORT).show();
//                    filepath.setText();
                    return fileReferences.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        NoticeItem doc =  new NoticeItem(notice_title1.getText().toString(),notice_content1.getText().toString() ,""+downloadUri);
                        databaseReference.child(uploadId).setValue(doc);
                        Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }



    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void setImage(){
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),image_request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == image_request && resultCode == RESULT_OK) {

            if (data != null) {
                imageUri = data.getData();

//                image.setImageURI(imageUri);
            }
        }
    }


    //checking storage permission
    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context,"Have permission",Toast.LENGTH_SHORT).show();
            setImage();
        }
        else{

            Toast.makeText(context,"Require Permission",Toast.LENGTH_SHORT).show();

            //permission request
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},storage_permission);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == storage_permission && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context,"Permission Granted",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"Please Give Access Permission",Toast.LENGTH_SHORT).show();
        }
    }

}
