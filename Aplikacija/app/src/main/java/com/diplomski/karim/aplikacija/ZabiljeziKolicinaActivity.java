package com.diplomski.karim.aplikacija;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ZabiljeziKolicinaActivity extends AppCompatActivity {

    private  EditText kolicinaEdit;
    private TextView kalorijeText, karbohidratiText, mastiText, proteiniText;

    private void updateVrijednosti(double kalorije, double karbohidrati, double masti, double proteini)
    {
        kalorijeText.setText("Kalorije (" + Double.toString(kalorije) + ")");
        karbohidratiText.setText("Karbohidrati (" + Double.toString(karbohidrati) + ")");
        mastiText.setText("Masti (" +Double.toString(masti) + ")");
        proteiniText.setText("Proteini (" +Double.toString(proteini) + ")");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zabiljezi_kolicina_layout);
        setTitle("Unesi količinu");
        kolicinaEdit = findViewById(R.id.kolicinaEdit);
        kalorijeText = findViewById(R.id.kalorijeZab);
        karbohidratiText = findViewById(R.id.karbohidratiZab);
        mastiText = findViewById(R.id.mastiZab);
        proteiniText = findViewById(R.id.proteiniZab);
        Button zabiljeziButton = findViewById(R.id.dugmeZab);

        final Jelo j = (Jelo) getIntent().getExtras().getSerializable("jelo");

        updateVrijednosti(j.getKalorije(), j.getKarbohidrati(), j.getMasti(), j.getProteini());
        kolicinaEdit.setText(Double.toString(j.getServing()));

        final Intent data = new Intent();

        if (getIntent().hasExtra("zaEdit"))
        {
            setTitle("Izmijeni količinu");
            Unos u = (Unos) getIntent().getExtras().getSerializable("zaEdit");
            zabiljeziButton.setText("Spremi promjene");
            kolicinaEdit.setText(Double.toString(u.getKolicina()));
            updateVrijednosti(u.dajUnosKalorija(), u.dajUnosKarbohidrata(), u.dajUnosMasti(), u.dajUnosProteina());
            data.putExtra("editId", u.getDbId());
        }

        zabiljeziButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textKol = kolicinaEdit.getText().toString();
                if (textKol != "") {
                    double kolicina = Double.parseDouble(textKol);
                    String datumUnosa = getIntent().getExtras().getString("datumUnosa");
                    Unos unos = new Unos(j, kolicina, datumUnosa);

                    data.putExtra("noviUnos", unos);
                    setResult(RESULT_OK, data);
                    finish();
                }
                else
                {
                    Alati.prikaziPoruku(v.getContext(), "Molimo unesite validnu vrijednost za količinu.");
                }
            }
        });

        //TODO ovo ispod
        kolicinaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.length() > 0) {
                    double kol = Double.parseDouble(text);
                    String datumUnosa = getIntent().getExtras().getString("datumUnosa");
                    Unos unos = new Unos(j, kol, datumUnosa);
                    updateVrijednosti(unos.dajUnosKalorija(), unos.dajUnosKarbohidrata(), unos.dajUnosMasti(), unos.dajUnosProteina());

                }
                else
                    updateVrijednosti(0, 0, 0, 0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
