package com.example.furkan.cumrafirebaseexample.View.ElemanEkle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.furkan.cumrafirebaseexample.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ElemanDetails extends AppCompatActivity {

    private String mPost_key=null;
    private DatabaseReference mDatabase;

    private ImageView profil_photo;
    private TextView isim,telefon,departman,email,maas,isegiristarihi,egitim,gorev;
    private Button button_delete;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleman_details);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Calisanlar");
        mPost_key = getIntent().getExtras().getString("blog_id");

        profil_photo=(ImageView)findViewById(R.id.eleman_details_photo);
        isim=(TextView)findViewById(R.id.eleman_details_isim);
        telefon=(TextView)findViewById(R.id.eleman_details_telefon);
        departman=(TextView)findViewById(R.id.eleman_details_departman);
        email=(TextView)findViewById(R.id.eleman_details_email);
        maas=(TextView)findViewById(R.id.eleman_details_maas);
        isegiristarihi=(TextView)findViewById(R.id.eleman_details_isegiristarihi);
        egitim=(TextView)findViewById(R.id.eleman_details_egitim);
        gorev=(TextView)findViewById(R.id.eleman_details_gorev);

        button_delete=(Button)findViewById(R.id.silmebutonu);


        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String eleman_adi=(String)dataSnapshot.child("ad_soyad").getValue();
                String profil_fotografi = (String)dataSnapshot.child("fotograf").getValue();
                String eleman_departman =(String)dataSnapshot.child("departman").getValue();
                String eleman_email=(String)dataSnapshot.child("email").getValue() ;
                String eleman_maas=(String)dataSnapshot.child("maas").getValue();
                String eleman_isegiristarihi=(String)dataSnapshot.child("ise_giris_tarihi").getValue();
                String eleman_egitim=(String)dataSnapshot.child("ogrenim").getValue();
                String eleman_telefon=(String)dataSnapshot.child("telefon").getValue();
                String elaman_gorev=(String)dataSnapshot.child("görev").getValue();

                Picasso.with(ElemanDetails.this).load(profil_fotografi).into(profil_photo);
                isim.setText(eleman_adi);
                telefon.setText(eleman_telefon);
                departman.setText(eleman_departman);
                email.setText(eleman_email);
                maas.setText(eleman_maas);
                isegiristarihi.setText(eleman_isegiristarihi);
                egitim.setText(eleman_egitim);
                gorev.setText(elaman_gorev);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(mPost_key).removeValue();
                Intent ıntent = new Intent(ElemanDetails.this,ElemanListeActivity.class);
                startActivity(ıntent);
            }
        });


    }
}
