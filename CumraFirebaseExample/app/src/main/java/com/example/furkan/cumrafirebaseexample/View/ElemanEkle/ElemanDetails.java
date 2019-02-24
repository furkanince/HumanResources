package com.example.furkan.cumrafirebaseexample.View.ElemanEkle;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.furkan.cumrafirebaseexample.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ElemanDetails extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private String key;
    private ImageView profil_photo;
    private TextView isim, telefon, departman, email, maas, isegiristarihi, egitim, gorev;
    private Button button_delete, button_update;

    String profil_foto = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleman_details);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Calisanlar");
        mPost_key = getIntent().getExtras().getString("blog_id");

        profil_photo = (ImageView) findViewById(R.id.eleman_details_photo);
        isim = (TextView) findViewById(R.id.eleman_details_isim);
        telefon = (TextView) findViewById(R.id.eleman_details_telefon);
        departman = (TextView) findViewById(R.id.eleman_details_departman);
        email = (TextView) findViewById(R.id.eleman_details_email);
        maas = (TextView) findViewById(R.id.eleman_details_maas);
        isegiristarihi = (TextView) findViewById(R.id.eleman_details_isegiristarihi);
        egitim = (TextView) findViewById(R.id.eleman_details_egitim);
        gorev = (TextView) findViewById(R.id.eleman_details_gorev);

        button_delete = (Button) findViewById(R.id.silmebutonu);
        button_update = (Button) findViewById(R.id.guncellemebutonu);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String eleman_adi = (String) dataSnapshot.child("ad_soyad").getValue();
                profil_foto = (String) dataSnapshot.child("fotograf").getValue();
                String eleman_departman = (String) dataSnapshot.child("departman").getValue();
                String eleman_email = (String) dataSnapshot.child("email").getValue();
                String eleman_maas = (String) dataSnapshot.child("maas").getValue();
                String eleman_isegiristarihi = (String) dataSnapshot.child("ise_giris_tarihi").getValue();
                String eleman_egitim = (String) dataSnapshot.child("ogrenim").getValue();
                String eleman_telefon = (String) dataSnapshot.child("telefon").getValue();
                String elaman_gorev = (String) dataSnapshot.child("görev").getValue();

                Picasso.with(ElemanDetails.this).load(profil_foto).into(profil_photo);
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
                Intent ıntent = new Intent(ElemanDetails.this, ElemanListeActivity.class);
                startActivity(ıntent);
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ıntent = new Intent(ElemanDetails.this,Eleman_Guncelle.class);

                ıntent.putExtra("foto",profil_foto);
                ıntent.putExtra("Adisoyadi",isim.getText().toString());
                ıntent.putExtra("Telefonu",telefon.getText().toString());
                ıntent.putExtra("Departmani",departman.getText().toString());
                ıntent.putExtra("Emaili",email.getText().toString());
                ıntent.putExtra("Maasi",maas.getText().toString());
                ıntent.putExtra("IseGirisTarihi",isegiristarihi.getText().toString());
                ıntent.putExtra("Egitimi",egitim.getText().toString());
                ıntent.putExtra("Gorevi",gorev.getText().toString());
                startActivity(ıntent);


            }
        });


    }
}



