package com.diplomski.karim.aplikacija;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DodajActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_layout);
        setTitle("Dodaj novo jelo");
        final Button dodajButton = findViewById(R.id.dodajButton);
        final EditText nazivText = findViewById(R.id.nazivText);
        final EditText servingText = findViewById(R.id.servingText);
        final EditText karbohidratiText = findViewById(R.id.karbohidratiText);
        final EditText proteiniText = findViewById(R.id.proteiniText);
        final EditText mastiText = findViewById(R.id.mastiText);
        final EditText kalorijeText = findViewById(R.id.kalorijeText);

        final Intent data = new Intent();
        final Bundle b = getIntent().getExtras();


        if (b != null)
        {
            setTitle("Izmijeni jelo");
            Jelo jelo = (Jelo) b.getSerializable("zaEdit");
            nazivText.setText(jelo.getNaziv());
            servingText.setText(Double.toString(jelo.getServing()));
            karbohidratiText.setText(Double.toString(jelo.getKarbohidrati()));
            proteiniText.setText(Double.toString(jelo.getProteini()));
            mastiText.setText(Double.toString(jelo.getMasti()));
            kalorijeText.setText(Double.toString(jelo.getKalorije()));
            dodajButton.setText("Sačuvaj izmjene");
            data.putExtra("editId", jelo.getDbId());
        }

        dodajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String naziv = nazivText.getText().toString();
                    double serving = Double.parseDouble(servingText.getText().toString());
                    double karbohidrati = Double.parseDouble(karbohidratiText.getText().toString());
                    double proteini = Double.parseDouble(proteiniText.getText().toString());
                    double masti = Double.parseDouble(mastiText.getText().toString());
                    double kalorije = Double.parseDouble(kalorijeText.getText().toString());

                    Jelo j = new Jelo(naziv, serving, kalorije, karbohidrati, proteini, masti);

                    data.putExtra("novoJelo", j);

                    setResult(RESULT_OK, data);
                    finish();
                }
                catch (Exception e)
                {
                    Alati.prikaziPoruku(v.getContext(), "Molimo unesite sve nutritivne vrijednosti!");
                }
            }
        });

    }
}
