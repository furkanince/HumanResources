package com.example.furkan.cumrafirebaseexample.View.ElemanEkle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.furkan.cumrafirebaseexample.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ElemanListeActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    private ProgressDialog progressDialog;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleman_liste);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Calisanlar");

        mSearchBtn=(ImageButton)findViewById(R.id.search_btn);
        mSearchField=(EditText)findViewById(R.id.search_field);
        mResultList=(RecyclerView)findViewById(R.id.result_list);

        mResultList.setHasFixedSize(true);

        mResultList.setLayoutManager(new LinearLayoutManager(this));
        ProgressDialog progressDialog = new ProgressDialog(ElemanListeActivity.this);
        progressDialog.setMessage("Kişiler Getirliyor..");
        firebaseUserSearch2();

        progressDialog.dismiss();
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);
            }
        });

    }

    private void deleteItem(String id)
    {
        DatabaseReference drArtist = FirebaseDatabase.getInstance().getReference("Calisanlar");
        drArtist.removeValue();
    }



    private void firebaseUserSearch2(){


        FirebaseRecyclerAdapter<Eleman,ElemanListeActivity.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Eleman, ElemanListeActivity.UsersViewHolder>(
                Eleman.class,
                R.layout.list_layout_eleman,
                ElemanListeActivity.UsersViewHolder.class,
                mUserDatabase

        )

        {
            @Override
            protected void populateViewHolder(ElemanListeActivity.UsersViewHolder viewHolder, Eleman model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setDetails(getApplicationContext(),model.getAd_soyad(),model.getDepartman(),model.getGörev(),model.getIse_giris_tarihi(),model.getCinsiyet());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(Hasta_Ara.this,post_key,Toast.LENGTH_SHORT).show();
                        Intent ıntent = new Intent(ElemanListeActivity.this,ElemanDetails.class);
                        ıntent.putExtra("blog_id",post_key);
                        startActivity(ıntent);

                    }
                });

            }
        };
        mResultList.setAdapter(firebaseRecyclerAdapter);

    }
    private void firebaseUserSearch(String searchTex){



        Query firebaseSearchQuery = mUserDatabase.orderByChild("ad_soyad").startAt(searchTex).endAt(searchTex + "\uf8ff");
        FirebaseRecyclerAdapter<Eleman,ElemanListeActivity.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Eleman,ElemanListeActivity.UsersViewHolder>(
                Eleman.class,
                R.layout.list_layout_eleman,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(ElemanListeActivity.UsersViewHolder viewHolder, Eleman model,final int position) {

                final String post_key = getRef(position).getKey();
                viewHolder.setDetails(getApplicationContext(),model.getAd_soyad(),model.getGörev(),model.getTelefon(),model.getIse_giris_tarihi(),model.getCinsiyet());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(Hasta_Ara.this,post_key,Toast.LENGTH_SHORT).show();
                        Intent ıntent = new Intent(ElemanListeActivity.this,ElemanDetails.class);
                        ıntent.putExtra("blog_id",post_key);
                        startActivity(ıntent);

                    }
                });

            }
        };
        mResultList.setAdapter(firebaseRecyclerAdapter);

    }



    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setDetails(Context ctx, String name, String meslek, String ilac, String ilac_basslama_tarihi, String userImage){

            TextView user_name = (TextView)mView.findViewById(R.id.name_text);
            TextView user_departman =(TextView)mView.findViewById(R.id.departman_text);
            TextView user_telefon = (TextView)mView.findViewById(R.id.telefon_text);
            TextView user_isegiristarihi = (TextView)mView.findViewById(R.id.isegiristarihi_text);
            ImageView user_image = (ImageView)mView.findViewById(R.id.profile_image);


            user_name.setText(name);
            user_departman.setText(meslek);
            user_telefon.setText(ilac);
            user_isegiristarihi.setText(ilac_basslama_tarihi);

            Glide.with(ctx).load(userImage).into(user_image);


        }

    }

}
