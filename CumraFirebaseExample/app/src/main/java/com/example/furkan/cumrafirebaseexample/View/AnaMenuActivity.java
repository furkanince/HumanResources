package com.example.furkan.cumrafirebaseexample.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.furkan.cumrafirebaseexample.R;
import com.example.furkan.cumrafirebaseexample.View.ElemanEkle.ElemanEkleActivity;
import com.example.furkan.cumrafirebaseexample.View.ElemanEkle.ElemanListeActivity;

public class AnaMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView bankCard,ideasCard,addCard,linksCard,wifiCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_menu);


        bankCard=(CardView)findViewById(R.id.bank_card);
        ideasCard=(CardView)findViewById(R.id.ideas_card);
        addCard=(CardView)findViewById(R.id.add_card);
        linksCard=(CardView)findViewById(R.id.links_card);
        wifiCard=(CardView)findViewById(R.id.wifi_card);

        //Add click listener to the cards
        bankCard.setOnClickListener(this);
        ideasCard.setOnClickListener(this);
        addCard.setOnClickListener(this);
        linksCard.setOnClickListener(this);
        wifiCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch (v.getId()) {

            case R.id.bank_card : i = new Intent(this,ElemanEkleActivity.class);startActivity(i); break;
            case R.id.ideas_card : i = new Intent(this,ElemanListeActivity.class);startActivity(i); break;
            case R.id.add_card : i = new Intent(this,AnaMenuActivity.class);startActivity(i); break;
            case R.id.links_card : i = new Intent(this,AnaMenuActivity.class);startActivity(i); break;
            case R.id.wifi_card : i = new Intent(this,AnaMenuActivity.class);startActivity(i); break;
            default:break;

        }
    }
}
