package com.diplomski.karim.aplikacija;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class UnosAdapter extends BaseAdapter {

    public interface PocetnaFragmentInterface{
        void editujUnos(Unos u);
        void obrisiUnos(Unos u);
    }

    private Activity activity;
    private ArrayList<Unos> data;
    private Resources res;
    private LayoutInflater inflater;
    private int selectedPosition;
    private PocetnaFragmentInterface parentFragment;

    public UnosAdapter(Activity activity, ArrayList<Unos> data, Resources res, UnosAdapter.PocetnaFragmentInterface parentFragment){
        this.activity = activity;
        this.data = data;
        this.res = res;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.selectedPosition = -1;
        this.parentFragment = parentFragment;
    }

    public void updateSelected(int position)
    {
        if (selectedPosition == position)
            selectedPosition = -1;
        else
            selectedPosition = position;
    }

    public void updateData(ArrayList<Unos> newData)
    {
        this.data = new ArrayList<Unos>();
        this.data.addAll(newData);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Unos unos = data.get(position);
        View vi = inflater.inflate(R.layout.element_lista_jela_layout, null);
        final JeloAdapter.ViewHolder holder = new JeloAdapter.ViewHolder();

        holder.brisiButton = vi.findViewById(R.id.brisiButton);
        holder.editButton = vi.findViewById(R.id.editButton);
        holder.nazivText = vi.findViewById(R.id.nazivText);

        vi.setTag(holder);

        holder.nazivText.setText(unos.getJelo().getNaziv() + "(" + unos.getKolicina() + ")");

        if (selectedPosition == position) {
            holder.brisiButton.setVisibility(View.VISIBLE);
            holder.editButton.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.brisiButton.setVisibility(View.GONE);
            holder.editButton.setVisibility(View.GONE);
        }

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentFragment.editujUnos(unos);
            }
        });

        holder.brisiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                parentFragment.obrisiUnos(unos);
            }
        });


        return vi;
    }
}
