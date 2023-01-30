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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class PocetnaFragment extends Fragment implements UnosAdapter.PocetnaFragmentInterface{

    public interface GlavnaAktivnostInterface{
        Cilj dajCilj(String datum);
        void updateCiljProgress(String datum);
        void updateDatum(String datum);
        ArrayList<Unos> dajListuUnosa(String datum);
        void dodajUnos(Unos u);
        void obrisiUnos(Unos u);
        void editUnos(Unos u);
        String dajDatum();
    }

    public void updateProgressBarGoals(double kalorije, double karbohidrati, double proteini, double masti)
    {
        kalorijeProgress.setMax((int)kalorije);
        karbohidratiProgress.setMax((int)karbohidrati);
        proteiniProgress.setMax((int)proteini);
        mastiProgress.setMax((int)masti);
    }

    private void updateProgressBarProgress(double kalorije, double karbohidrati, double proteini, double masti)
    {
        Cilj cilj = glavnaAktivnostInterface.dajCilj(datumText.getText().toString());
        kalorijeProgress.setProgress((int)kalorije);
        karbohidratiProgress.setProgress((int)karbohidrati);
        proteiniProgress.setProgress((int)proteini);
        mastiProgress.setProgress((int)masti);
        kalorijeText.setText("Kalorije (" + String.format("%.2f", kalorije) + "/" + String.format("%.2f", cilj.getKalorijeC())+")");
        karbohidratiText.setText("Karbohidrati (" + String.format("%.2f", karbohidrati) + "/" + String.format("%.2f", cilj.getKarbohidratiC())+")");
        mastiText.setText("Masti (" + String.format("%.2f", masti) + "/" + String.format("%.2f", cilj.getMastiC())+")");
        proteiniText.setText("Proteini (" + String.format("%.2f", proteini) + "/" + String.format("%.2f", cilj.getProteiniC())+")");
    }

    View myView;
    GlavnaAktivnostInterface glavnaAktivnostInterface;
    ProgressBar kalorijeProgress, karbohidratiProgress, proteiniProgress, mastiProgress;
    TextView kalorijeText, karbohidratiText, proteiniText, mastiText, datumText;
    ListView unosiList;

    private void idiNaDatum(String datumString) {
        ArrayList<Unos> unosi = glavnaAktivnostInterface.dajListuUnosa(datumString);
        Cilj c = glavnaAktivnostInterface.dajCilj(datumString);

        c.updateProgress(unosi);
        ((UnosAdapter)unosiList.getAdapter()).updateData(unosi);
        ((UnosAdapter)unosiList.getAdapter()).updateSelected(-1);
        ((BaseAdapter)unosiList.getAdapter()).notifyDataSetChanged();

        updateProgressBarGoals(c.getKalorijeC(), c.getKarbohidratiC(), c.getProteiniC(), c.getMastiC());
        updateProgressBarProgress(c.getKalorije(), c.getKarbohidrati(), c.getProteini(), c.getMasti());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pocetna_layout, container, false);
        getActivity().setTitle("Početna");
        kalorijeProgress = myView.findViewById(R.id.kalorijeProgres);
        karbohidratiProgress = myView.findViewById(R.id.karbohidratiProgres);
        proteiniProgress = myView.findViewById(R.id.proteiniProgres);
        mastiProgress = myView.findViewById(R.id.mastiProgres);
        kalorijeText = myView.findViewById(R.id.kalorijeTextV);
        karbohidratiText = myView.findViewById(R.id.karbohidratiTextV);
        proteiniText = myView.findViewById(R.id.proteiniTextV);
        mastiText = myView.findViewById(R.id.mastiTextV);
        datumText = myView.findViewById(R.id.datumText);
        Button jucerButton = myView.findViewById(R.id.jucerButton);
        Button sutraButton = myView.findViewById(R.id.sutraButton);
        Button zabiljeziButton = myView.findViewById(R.id.zabiljeziButton);
        unosiList = myView.findViewById(R.id.dnevnikList);

        datumText.setText(glavnaAktivnostInterface.dajDatum());
        String datumString = datumText.getText().toString();

        glavnaAktivnostInterface.updateDatum(datumString);
        ArrayList<Unos> unosi = glavnaAktivnostInterface.dajListuUnosa(datumString);

        glavnaAktivnostInterface.updateCiljProgress(datumString);
        Cilj c = glavnaAktivnostInterface.dajCilj(datumString);

        UnosAdapter unosAdapter = new UnosAdapter(getActivity(), unosi, getActivity().getResources(), this);
        unosiList.setAdapter(unosAdapter);

        updateProgressBarGoals(c.getKalorijeC(), c.getKarbohidratiC(), c.getProteiniC(), c.getMastiC());
        updateProgressBarProgress(c.getKalorije(), c.getKarbohidrati(), c.getProteini(), c.getMasti());


        unosiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((UnosAdapter)unosiList.getAdapter()).updateSelected(position);
                ((BaseAdapter)unosiList.getAdapter()).notifyDataSetChanged();
            }
        });

        zabiljeziButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ZabiljeziListaActivity.class);
                i.putExtra("datumUnosa", glavnaAktivnostInterface.dajDatum());
                startActivityForResult(i, Alati.RC_ZABILJEZI_JELO);
            }
        });

        jucerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    datumText.setText(Alati.dajDatumPrije(glavnaAktivnostInterface.dajDatum()));
                    idiNaDatum(datumText.getText().toString());
                    glavnaAktivnostInterface.updateDatum(datumText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        sutraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    datumText.setText(Alati.dajDatumPoslije(glavnaAktivnostInterface.dajDatum()));
                    idiNaDatum(datumText.getText().toString());
                    glavnaAktivnostInterface.updateDatum(datumText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return myView;
    }

    @Override
    public void editujUnos(Unos u) {

        Intent i = new Intent(getActivity(), ZabiljeziKolicinaActivity.class);
        i.putExtra("jelo", u.getJelo());
        i.putExtra("zaEdit", u);

        startActivityForResult(i, Alati.RC_EDITUJ_UNOS);
    }

    @Override
    public void obrisiUnos(Unos u) {
        glavnaAktivnostInterface.obrisiUnos(u);
        ListView unosiList = myView.findViewById(R.id.dnevnikList);
        ((UnosAdapter)unosiList.getAdapter()).updateSelected(-1);
        ((BaseAdapter)unosiList.getAdapter()).notifyDataSetChanged();

        String datumString = datumText.getText().toString();
        ArrayList<Unos> unosi = glavnaAktivnostInterface.dajListuUnosa(datumString);
        Cilj c = glavnaAktivnostInterface.dajCilj(datumString);
        c.updateProgress(unosi);
        glavnaAktivnostInterface.updateCiljProgress(datumString);
        updateProgressBarProgress(c.getKalorije(), c.getKarbohidrati(), c.getProteini(), c.getMasti());
    }

    private void onAttachDo(Context context){
        try {
            glavnaAktivnostInterface = (PocetnaFragment.GlavnaAktivnostInterface) context;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String datumString = datumText.getText().toString();
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Alati.RC_ZABILJEZI_JELO) {
                Unos unos = (Unos) data.getSerializableExtra("noviUnos");

                glavnaAktivnostInterface.dodajUnos(unos);
                glavnaAktivnostInterface.updateCiljProgress(datumString);
                Cilj c = glavnaAktivnostInterface.dajCilj(datumString);
                updateProgressBarProgress(c.getKalorije(), c.getKarbohidrati(), c.getProteini(), c.getMasti());
                ListView unosiList = myView.findViewById(R.id.dnevnikList);
                ((UnosAdapter)unosiList.getAdapter()).updateSelected(-1);
                ((UnosAdapter)unosiList.getAdapter()).updateData(glavnaAktivnostInterface.dajListuUnosa(datumString));
                ((BaseAdapter)unosiList.getAdapter()).notifyDataSetChanged();
            }
            else if (requestCode == Alati.RC_EDITUJ_UNOS)
            {
                Unos unos = (Unos) data.getSerializableExtra("noviUnos");
                long dbId = data.getLongExtra("editId", -1);
                unos.setDbId(dbId);
                glavnaAktivnostInterface.editUnos(unos);
                glavnaAktivnostInterface.updateCiljProgress(datumString);
                Cilj c = glavnaAktivnostInterface.dajCilj(datumString);
                updateProgressBarProgress(c.getKalorije(), c.getKarbohidrati(), c.getProteini(), c.getMasti());
                ((UnosAdapter)unosiList.getAdapter()).updateSelected(-1);
                ((UnosAdapter)unosiList.getAdapter()).updateData(glavnaAktivnostInterface.dajListuUnosa(datumString));
                ((BaseAdapter)unosiList.getAdapter()).notifyDataSetChanged();
            }
        }
    }
}
