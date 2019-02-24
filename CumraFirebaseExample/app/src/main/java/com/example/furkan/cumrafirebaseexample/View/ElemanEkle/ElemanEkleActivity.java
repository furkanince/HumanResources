package com.example.furkan.cumrafirebaseexample.View.ElemanEkle;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.furkan.cumrafirebaseexample.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ElemanEkleActivity extends AppCompatActivity {


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private EditText editText_Ad_Soyad,editText_Telefon,editText_Email,
            editText_Giris_Tarihi,editText_Departman,editText_Maas,editText_Görev;
    Spinner spinnerGender,spinnerEducation;
    private Uri imgUri;

    public static  final String FB_STORAGE_PATH= "image/";
    public static final String FB_DATABASE_PATH= "Calisanlar";
    public static final int REQUEST_CODE =1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleman_ekle);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        imageView=(ImageView)findViewById(R.id.imageView_foto);

        editText_Ad_Soyad=(EditText)findViewById(R.id.isim);
        editText_Departman=(EditText)findViewById(R.id.departman);
        editText_Email=(EditText)findViewById(R.id.email);
        editText_Telefon=(EditText)findViewById(R.id.telefon);
        editText_Giris_Tarihi=(EditText)findViewById(R.id.isegiris);
        editText_Maas=(EditText)findViewById(R.id.maas);
        editText_Görev=(EditText)findViewById(R.id.isgörevi);

        spinnerGender=(Spinner)findViewById(R.id.spinner_cinsiyet);
        spinnerEducation=(Spinner)findViewById(R.id.spinner_ogrenim);


    }

    public void btnBrowse_Click(View v)
    {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!= null && data.getData() != null){
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getImageExt(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void btnUpload_Click(View v)
    {
        String image=spinnerGender.getSelectedItem().toString();
        if(image.equals("Kadın")){
            image="https://firebasestorage.googleapis.com/v0/b/ik2denemecumra.appspot.com/o/female.png?alt=media&token=1dd8c720-5221-4dd0-83f4-55f0c73c163f";
        }
        else if(image.equals("Erkek")){
            image="https://firebasestorage.googleapis.com/v0/b/ik2denemecumra.appspot.com/o/male.png?alt=media&token=082f2910-b1fd-4674-97b3-2610620726ce";
        }


        final String okuma=spinnerEducation.getSelectedItem().toString();

        if(imgUri != null)
        {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading image");
            dialog.show();

            //Get the storage reference
            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis()+"."+getImageExt(imgUri));

            //Add file to reference

            final String finalImage = image;
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //tasksnapshot.getDownload() özelliği olmadığı için aşağıdaki yerleri ben düzenledim!!
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();
                    //buraya kadar düzenledim.
                    //Dismiss dialog  when succes
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Image upload",Toast.LENGTH_SHORT).show();
                    Eleman ımageUpload = new Eleman(editText_Ad_Soyad.getText().toString(),downloadUrl.toString(),okuma,editText_Telefon.getText().toString(), finalImage,editText_Görev.getText().toString(),editText_Email.getText().toString(),editText_Giris_Tarihi.getText().toString(),editText_Departman.getText().toString(),editText_Maas.getText().toString());//Normalde tasksnapshot.getDownload().toString() olmalıydı.Fakat metod olmadığı için yukarıda belirttiğim yolu izledim ve downloadUrl.toString() 'nu yazdım.Çalıştı :D

                    //Save  image info  in to firebase database;
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(ımageUpload);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Dismiss dialog  when error
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload Progress
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Upload " + (int)progress+"%");
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(),"Please select a photo",Toast.LENGTH_SHORT).show();
        }
    }







}
