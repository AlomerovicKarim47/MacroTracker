package com.diplomski.karim.aplikacija;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ListaFragment extends Fragment implements JeloAdapter.ListaFragmentInterface{

    public interface GlavnaAktivnostInterface {
        void dodajJelo(Jelo j);
        void obrisiJelo(Jelo j);
        void editujJelo(long editId, Jelo novoJelo);
        ArrayList<Jelo> dajListuJela(String query);
    }

    View myView;
    GlavnaAktivnostInterface glavnaAktivnostInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.lista_layout, container, false);
        getActivity().setTitle("Uredi listu jela");
        Button dodajButton = myView.findViewById(R.id.dodajButton);
        final ListView jelaList = myView.findViewById(R.id.jelaList);
        SearchView jelaSearch = myView.findViewById(R.id.jelaSearch);

        JeloAdapter jeloAdapter = new JeloAdapter(getActivity(), glavnaAktivnostInterface.dajListuJela(null) , getActivity().getResources(), this);
        jelaList.setAdapter(jeloAdapter);

        jelaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((JeloAdapter)jelaList.getAdapter()).updateSelected(position);
                ((BaseAdapter)jelaList.getAdapter()).notifyDataSetChanged();
            }
        });

        dodajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DodajActivity.class);
                startActivityForResult(i, Alati.RC_DAJ_NOVO_JELO);
            }
        });

        jelaSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Jelo> res = glavnaAktivnostInterface.dajListuJela(newText);

                ((JeloAdapter)jelaList.getAdapter()).updateData(res);
                ((JeloAdapter)jelaList.getAdapter()).updateSelected(-1);
                ((BaseAdapter)jelaList.getAdapter()).notifyDataSetChanged();

                return false;
            }
        });

        return myView;
    }

    @Override
    public void editujJelo(Jelo jelo) {
        Intent i = new Intent(getActivity(), DodajActivity.class);
        i.putExtra("zaEdit", jelo);

        startActivityForResult(i, Alati.RC_EDITUJ_JELO);
    }

    @Override
    public void obrisiJelo(Jelo jelo) {
        glavnaAktivnostInterface.obrisiJelo(jelo);
        ListView jelaList = myView.findViewById(R.id.jelaList);
        ((JeloAdapter)jelaList.getAdapter()).updateSelected(-1);
        ((BaseAdapter)jelaList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Alati.RC_DAJ_NOVO_JELO) {
                Jelo novoJelo = (Jelo) data.getSerializableExtra("novoJelo");
                glavnaAktivnostInterface.dodajJelo(novoJelo);
                ListView jelaList = myView.findViewById(R.id.jelaList);
                ((JeloAdapter)jelaList.getAdapter()).updateSelected(-1);
                ((JeloAdapter)jelaList.getAdapter()).updateData(glavnaAktivnostInterface.dajListuJela(null));
                ((BaseAdapter)jelaList.getAdapter()).notifyDataSetChanged();

            }
            else if (requestCode == Alati.RC_EDITUJ_JELO) {
                Jelo novoJelo = (Jelo) data.getSerializableExtra("novoJelo");
                long def = -1;
                long editId = data.getLongExtra("editId", def);
                glavnaAktivnostInterface.editujJelo(editId, novoJelo);
                ListView jelaList = myView.findViewById(R.id.jelaList);
                ((BaseAdapter)jelaList.getAdapter()).notifyDataSetChanged();
                ((JeloAdapter)jelaList.getAdapter()).updateData(glavnaAktivnostInterface.dajListuJela(null));
            }
        }
    }

    private void onAttachDo(Context context){
        try {
            glavnaAktivnostInterface = (GlavnaAktivnostInterface) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onAttachDo(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachDo(context);
    }

}
