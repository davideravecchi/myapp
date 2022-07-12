package com.drave.mioporcino.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.drave.mioporcino.R;
import com.drave.mioporcino.database.Contatto;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Contatto> {

    public CustomAdapter(Context context, int textViewResourceId, List<Contatto> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.rowcustom, null);

        TextView myidrow  = (TextView)convertView.findViewById(R.id.txt_row_id);
        TextView myloc = (TextView)convertView.findViewById(R.id.txt_row_location);
        TextView mynote = (TextView)convertView.findViewById(R.id.txt_row_note);
        TextView myval = (TextView)convertView.findViewById(R.id.txt_row_valutazione);
        TextView mydata = (TextView)convertView.findViewById(R.id.txt_row_data);
        ImageView mytype = (ImageView)convertView.findViewById(R.id.img_row_type);

        Contatto f = getItem(position);

        myidrow.setText(f.getIdrow().toString());
        myloc.setText(f.getLocalita());
        mynote.setText(f.getNote());
        myval.setText(f.getValutazione());
        mydata.setText(f.getData());
        setIm(mytype,f.getTipologia());

        return convertView;
    }

    //--------------------------------------------------------------
    // setta l'immagine in abse alla tiupologia
    //--------------------------------------------------------------
    private void setIm (ImageView iv, Integer i) {

        if (i==1) iv.setImageResource(R.mipmap.type_mushroom);
        if (i==2) iv.setImageResource(R.mipmap.type_auto);
        if (i==3) iv.setImageResource(R.mipmap.type_park);
        if (i==4) iv.setImageResource(R.mipmap.type_restourant);
    }
    //--------------------------------------------------------------
}
