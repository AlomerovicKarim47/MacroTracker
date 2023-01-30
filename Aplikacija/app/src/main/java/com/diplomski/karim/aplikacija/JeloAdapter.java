package com.diplomski.karim.aplikacija;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class JeloAdapter extends BaseAdapter {

    public interface ListaFragmentInterface{
        public void editujJelo(Jelo jelo);
        public void obrisiJelo(Jelo jelo);
    }

    Activity activity;
    ArrayList<Jelo> data;
    Resources res;
    LayoutInflater inflater;
    int selectedPosition;
    ListaFragmentInterface parentFragment;
    boolean enableEdit;

    public JeloAdapter(Activity activity, ArrayList data, Resources res, ListaFragmentInterface parentFragment){
        this.activity = activity;
        this.data = data;
        this.res = res;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.selectedPosition = -1;
        this.parentFragment = parentFragment;
        this.enableEdit = true;
    }

    public static class ViewHolder{
        Button editButton, brisiButton;
        TextView nazivText;
    }

    public void setEdit(boolean e)
    {
        this.enableEdit = e;
    }

    public void updateSelected(int position)
    {
        if (selectedPosition == position)
            selectedPosition = -1;
        else
            selectedPosition = position;
    }

    public void updateData(ArrayList<Jelo> newData)
    {
        this.data = new ArrayList<Jelo>();
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
        return data.get(position).getDbId();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final Jelo jelo = data.get(position);
        View vi = inflater.inflate(R.layout.element_lista_jela_layout, null);
        final ViewHolder holder = new ViewHolder();

        holder.brisiButton = vi.findViewById(R.id.brisiButton);
        holder.editButton = vi.findViewById(R.id.editButton);
        holder.nazivText = vi.findViewById(R.id.nazivText);

        vi.setTag(holder);

        holder.nazivText.setText(jelo.getNaziv());

        if (enableEdit && selectedPosition == position) {
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
                parentFragment.editujJelo(jelo);
            }
        });

        holder.brisiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                parentFragment.obrisiJelo(jelo);
            }
        });


        return vi;
    }
}
