package com.diplomski.karim.aplikacija;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class PostaviFragment extends Fragment {

    public interface GlavnaAktivnostInterface{
        Cilj dajCilj(String datum);
        void updateCilj(Cilj c);
        String dajDatum();
    }

    GlavnaAktivnostInterface glavnaAktivnostInterface;

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.postavi_layout, container, false);
        getActivity().setTitle("Postavi ciljeve");
        final EditText kalorijeText = myView.findViewById(R.id.kalorijeEdit);
        final EditText proteiniText = myView.findViewById(R.id.proteiniEdit);
        final EditText karbohidratiText = myView.findViewById(R.id.karbohidratiEdit);
        final EditText mastiText = myView.findViewById(R.id.mastiEdit);
        Button editButton = myView.findViewById(R.id.editButton);

        final Cilj cilj = glavnaAktivnostInterface.dajCilj(glavnaAktivnostInterface.dajDatum());

        kalorijeText.setText(Double.toString(cilj.getKalorijeC()));
        karbohidratiText.setText(Double.toString(cilj.getKarbohidratiC()));
        proteiniText.setText(Double.toString(cilj.getProteiniC()));
        mastiText.setText(Double.toString(cilj.getMastiC()));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cilj.setKalorijeC(Double.parseDouble(kalorijeText.getText().toString()));
                cilj.setKarbohidratiC(Double.parseDouble(karbohidratiText.getText().toString()));
                cilj.setMastiC(Double.parseDouble(mastiText.getText().toString()));
                cilj.setProteiniC(Double.parseDouble(proteiniText.getText().toString()));

                Alati.prikaziPoruku(getActivity(), "Promjene sačuvane.");

                glavnaAktivnostInterface.updateCilj(cilj);
            }
        });

        return myView;
    }

    private void onAttachDo(Context context){
        try {
            glavnaAktivnostInterface = (PostaviFragment.GlavnaAktivnostInterface) context;
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
