package com.diplomski.karim.aplikacija;

import java.util.ArrayList;

public class Cilj {
    private String datum;
    private double kalorije, karbohidrati, masti, proteini;
    private double kalorijeC, karbohidratiC, mastiC, proteiniC;

    //METODE
    public void updateProgress(ArrayList<Unos> unosi)
    {
        kalorije = karbohidrati = masti = proteini = 0;
        for (int i = 0; i < unosi.size(); i++)
        {
            kalorije += unosi.get(i).dajUnosKalorija();
            karbohidrati += unosi.get(i).dajUnosKarbohidrata();
            masti += unosi.get(i).dajUnosMasti();
            proteini += unosi.get(i).dajUnosProteina();
        }
    }

    //SETTERI

    public void setKalorije(double kalorije) {
        this.kalorije = kalorije;
    }

    public void setKarbohidrati(double karbohidrati) {
        this.karbohidrati = karbohidrati;
    }

    public void setMasti(double masti) {
        this.masti = masti;
    }

    public void setProteini(double proteini) {
        this.proteini = proteini;
    }

    public void setKalorijeC(double kalorijeC) {
        this.kalorijeC = kalorijeC;
    }

    public void setKarbohidratiC(double karbohidratiC) {
        this.karbohidratiC = karbohidratiC;
    }

    public void setMastiC(double mastiC) {
        this.mastiC = mastiC;
    }

    public void setProteiniC(double proteiniC) {
        this.proteiniC = proteiniC;
    }

    public void setDatum(String datum){ this.datum = datum;}

    //GETTER
    public String getDatum() {
        return datum;
    }

    public double getKalorije() {
        return kalorije;
    }

    public double getKarbohidrati() {
        return karbohidrati;
    }

    public double getMasti() {
        return masti;
    }

    public double getProteini() {
        return proteini;
    }

    public double getKalorijeC() {
        return kalorijeC;
    }

    public double getKarbohidratiC() {
        return karbohidratiC;
    }

    public double getMastiC() {
        return mastiC;
    }

    public double getProteiniC() {
        return proteiniC;
    }

    public Cilj(){
        this.datum = Alati.dajDanasnjiDatum();
        this.kalorijeC = 1500;
        this.karbohidratiC = 100;
        this.mastiC = 100;
        this.proteiniC = 100;

        this.kalorije = 0;
        this.proteini = 0;
        this.masti = 0;
        this.karbohidrati = 0;
    }

    public Cilj(double kalorijeC, double karbohidratiC, double mastiC, double proteiniC){
        this.datum = Alati.dajDanasnjiDatum();
        this.kalorijeC = kalorijeC;
        this.karbohidratiC = karbohidratiC;
        this.mastiC = mastiC;
        this.proteiniC = proteiniC;

        this.kalorije = 0;
        this.proteini = 0;
        this.masti = 0;
        this.karbohidrati = 0;
    }



}
