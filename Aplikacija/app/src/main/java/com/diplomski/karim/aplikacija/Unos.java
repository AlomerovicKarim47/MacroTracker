package com.diplomski.karim.aplikacija;

import java.io.Serializable;

public class Unos implements Serializable{
    private static int idGen = 0;
    private int id;
    private long dbId;
    private Jelo jelo;
    private double kolicina;
    private String datumUnosa;
    private double procenat;

    //GETTERI
    public Jelo getJelo() {
        return jelo;
    }

    public double getKolicina() {
        return kolicina;
    }

    public String getDatumUnosa() {
        return datumUnosa;
    }

    public int getId() {
        return id;
    }

    public long getDbId(){return dbId;}

    //SETTERI
    public void setDbId(long dbId){this.dbId = dbId;}

    //METODE
    public double dajUnosKalorija()
    {
        return procenat * jelo.getKalorije();
    }
    public double dajUnosKarbohidrata()
    {
        return procenat * jelo.getKarbohidrati();
    }
    public double dajUnosProteina()
    {
        return procenat * jelo.getProteini();
    }
    public double dajUnosMasti()
    {
        return procenat * jelo.getMasti();
    }

    //KONSTRUKTOR
    public Unos(Jelo jelo, double kolicinaJela, String datumUnosa) {
        this.id = idGen++;
        this.jelo = jelo;
        this.kolicina = kolicinaJela;
        this.datumUnosa = datumUnosa;
        this.procenat = kolicina / jelo.getServing();
    }
}
