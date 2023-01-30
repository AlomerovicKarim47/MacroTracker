package com.diplomski.karim.aplikacija;

import android.net.Uri;
import android.os.Parcel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Jelo implements Serializable {

    private String naziv, datumUnosa;
    private double serving, kalorije, karbohidrati, proteini, masti;
    private long dbId;

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public Jelo() {
    }

    public Jelo(String naziv, double serving, double kalorije, double karbohidrati, double proteini, double masti) {
        this.datumUnosa = Alati.dajDanasnjiDatum();
        this.naziv = naziv;
        this.serving = serving;
        this.kalorije = kalorije;
        this.karbohidrati = karbohidrati;
        this.proteini = proteini;
        this.masti = masti;
        this.dbId = -1;
    }

    public void update(String naziv, double serving, double kalorije, double karbohidrati, double proteini, double masti)
    {
        this.naziv = naziv;
        this.serving = serving;
        this.kalorije = kalorije;
        this.karbohidrati = karbohidrati;
        this.proteini = proteini;
        this.masti = masti;
    }

    public String getNaziv() {
        return naziv;
    }

    public double getServing() {
        return serving;
    }

    public double getKalorije() {
        return kalorije;
    }

    public double getKarbohidrati() {
        return karbohidrati;
    }

    public double getProteini() {
        return proteini;
    }

    public double getMasti() {
        return masti;
    }

    public String getDatumUnosa() {
        return datumUnosa;
    }

    //SETTERI
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setServing(double serving) {
        this.serving = serving;
    }

    public void setKalorije(double kalorije) {
        this.kalorije = kalorije;
    }

    public void setKarbohidrati(double karbohidrati) {
        this.karbohidrati = karbohidrati;
    }

    public void setProteini(double proteini) {
        this.proteini = proteini;
    }

    public void setMasti(double masti) {
        this.masti = masti;
    }


}
