package com.diplomski.karim.aplikacija;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class ZabiljeziListaActivity extends AppCompatActivity {
    private  BazaOpenHelper boh = new BazaOpenHelper(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zabiljezi_layout);
        setTitle("Zabilježi jelo");
        final ListView zabiljeziList = findViewById(R.id.zabiljeziList);
        SearchView zabiljeziSearch = findViewById(R.id.zabiljeziSearch);

        final ArrayList<Jelo> jela = boh.dajJelaPoNazivu(null);


        JeloAdapter jeloAdapter = new JeloAdapter(this, jela, getResources(), null);
        jeloAdapter.setEdit(false);

        zabiljeziList.setAdapter(jeloAdapter);

        zabiljeziList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), ZabiljeziKolicinaActivity.class);
                Jelo j = (Jelo) zabiljeziList.getAdapter().getItem(position);
                String datumUnosa = getIntent().getExtras().getString("datumUnosa");
                i.putExtra("jelo",j);
                i.putExtra("datumUnosa", datumUnosa);
                ((ZabiljeziListaActivity)view.getContext()).startActivityForResult(i, Alati.RC_DAJ_KOLICINU);
            }
        });

        zabiljeziSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                ArrayList<Jelo> res = boh.dajJelaPoNazivu(newText);

                ListView zabiljeziList = findViewById(R.id.zabiljeziList);

                ((JeloAdapter)zabiljeziList.getAdapter()).updateData(res);
                ((JeloAdapter)zabiljeziList.getAdapter()).updateSelected(-1);
                ((BaseAdapter)zabiljeziList.getAdapter()).notifyDataSetChanged();

                return false;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Alati.RC_DAJ_KOLICINU) {
                Unos unos = (Unos) data.getSerializableExtra("noviUnos");
                Intent d = new Intent();
                d.putExtra("noviUnos", unos);
                setResult(RESULT_OK, d);
                finish();
            }
        }
    }
}
