package com.diplomski.karim.aplikacija;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BazaOpenHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "mojaBaza.db";
    private static  final int DATABASE_VERSION = 11;

    private static final String JELO_TABLE = "Jelo";
    private static final String JELO_ID = "_id";
    private static final String JELO_NAZIV = "naziv";
    private static final String JELO_SERVING = "servnig";
    private static final String JELO_KALORIJE = "kalorije";
    private static final String JELO_KARBOHIDRATI = "karbohidrati";
    private static final String JELO_PROTEINI = "proteini";
    private static final String JELO_MASTI = "masti";
    private static final String JELO_DATUM = "datum";
    private static final String JELO_ARHIVIRANO = "arhivirano";
    private static final String JELO_CREATE = "create table if not exists " + JELO_TABLE + " (" +
            JELO_ID + " integer primary key autoincrement, " +
            JELO_NAZIV + " text, " +
            JELO_SERVING + " double, " +
            JELO_KALORIJE + " double, " +
            JELO_KARBOHIDRATI + " double, " +
            JELO_PROTEINI + " double, " +
            JELO_MASTI + " double, " +
            JELO_DATUM + " text, " +
            JELO_ARHIVIRANO + " boolean);";

    private static final String UNOS_TABLE = "Unos";
    private static final String UNOS_ID = "_id";
    private static final String UNOS_IDJELA = "idJela";
    private static final String UNOS_KOLICINA = "kolicina";
    private static final String UNOS_DATUM = "datum";
    private static final String UNOS_CREATE = "create table if not exists " + UNOS_TABLE + " (" +
            UNOS_ID + " integer primary key autoincrement, " +
            UNOS_IDJELA + " integer, " +
            UNOS_KOLICINA + " double, " +
            UNOS_DATUM + " text);";

    private static final String CILJ_TABLE = "Cilj";
    private static final String CILJ_ID = "_id";
    private static final String CILJ_DATUM = "datum";
    private static final String CILJ_KALORIJE = "kalorije";
    private static final String CILJ_KARBOHIDRATI = "karbohidrati";
    private static final String CILJ_PROTEINI = "proteini";
    private static final String CILJ_MASTI = "masti";
    private static final String CILJ_KALORIJE_C = "kalorijeC";
    private static final String CILJ_KARBOHIDRATI_C = "karbohidratiC";
    private static final String CILJ_PROTEINI_C = "proteiniC";
    private static final String CILJ_MASTI_C = "mastiC";
    private static final String CILJ_CREATE = "create table if not exists " + CILJ_TABLE + " (" +
            CILJ_ID + " integer primary key autoincrement, " +
            CILJ_DATUM + " text, " +
            CILJ_KALORIJE + " double, " +
            CILJ_KARBOHIDRATI + " double, " +
            CILJ_PROTEINI + " double, " +
            CILJ_MASTI + " double, " +
            CILJ_KALORIJE_C + " double, " +
            CILJ_KARBOHIDRATI_C + " double, " +
            CILJ_PROTEINI_C + " double, " +
            CILJ_MASTI_C + " double);";


    public BazaOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JELO_CREATE);
        db.execSQL(UNOS_CREATE);
        db.execSQL(CILJ_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + JELO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UNOS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CILJ_TABLE);

        onCreate(db);
    }

    //JELO
    public long dodajJelo(Jelo j)
    {
        ContentValues novoJelo = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        novoJelo.put(JELO_NAZIV, j.getNaziv());
        novoJelo.put(JELO_SERVING, j.getServing());
        novoJelo.put(JELO_KALORIJE, j.getKalorije());
        novoJelo.put(JELO_KARBOHIDRATI, j.getKarbohidrati());
        novoJelo.put(JELO_PROTEINI, j.getProteini());
        novoJelo.put(JELO_MASTI, j.getMasti());
        novoJelo.put(JELO_DATUM, j.getDatumUnosa());
        novoJelo.put(JELO_ARHIVIRANO, false);

        try
        {
            return db.insert(JELO_TABLE, null, novoJelo);
        }
        catch (SQLiteConstraintException e)
        {
            return -1;
        }

    }

    public void obrisiJelo(Jelo j)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(JELO_TABLE, "_id = " + j.getDbId(), null );
    }

    public void arhivirajJelo(Jelo j)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(JELO_ARHIVIRANO, true);
        db.update(JELO_TABLE, cv, "_id = " + j.getDbId(), null);
    }

    public void updateJelo(Jelo j)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(JELO_NAZIV, j.getNaziv());
        cv.put(JELO_SERVING, j.getServing());
        cv.put(JELO_KALORIJE, j.getKalorije());
        cv.put(JELO_KARBOHIDRATI, j.getKarbohidrati());
        cv.put(JELO_PROTEINI, j.getProteini());
        cv.put(JELO_MASTI, j.getMasti());
        db.update(JELO_TABLE, cv, "_id=" + j.getDbId(), null);
    }

    public ArrayList<Jelo> dajJelaPoNazivu(String query)
    {
        if (query == null)
            query = "";
        ArrayList<Jelo> rez = new ArrayList<Jelo>();
        SQLiteDatabase db = getWritableDatabase();
        String[] kolone = {JELO_ID, JELO_NAZIV, JELO_SERVING, JELO_KALORIJE, JELO_KARBOHIDRATI, JELO_PROTEINI, JELO_MASTI};
        Cursor c = db.query(JELO_TABLE, kolone, JELO_NAZIV + " LIKE '%" + query + "%' and " + JELO_ARHIVIRANO + " = 0", null, null, null, null);

        while (c.moveToNext())
        {
            long dbId = c.getLong(c.getColumnIndexOrThrow(JELO_ID));
            String naziv = c.getString(c.getColumnIndexOrThrow(JELO_NAZIV));
            double serving = c.getDouble(c.getColumnIndexOrThrow(JELO_SERVING));
            double kalorije = c.getDouble(c.getColumnIndexOrThrow(JELO_KALORIJE));
            double karbohidrati = c.getDouble(c.getColumnIndexOrThrow(JELO_KARBOHIDRATI));
            double proteini = c.getDouble(c.getColumnIndexOrThrow(JELO_PROTEINI));
            double masti = c.getDouble(c.getColumnIndexOrThrow(JELO_MASTI));

            Jelo j = new Jelo(naziv, serving, kalorije, karbohidrati, proteini, masti);
            j.setDbId(dbId);
            rez.add(j);
        }

        return rez;
    }

    public Jelo dajJeloPoId(long id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] kolone = {JELO_ID, JELO_NAZIV, JELO_SERVING, JELO_KALORIJE, JELO_KARBOHIDRATI, JELO_PROTEINI, JELO_MASTI};
        Cursor c = db.query(JELO_TABLE, kolone, JELO_ID + " = " + id, null, null, null, null);

        if (c != null & c.getCount() > 0) {
            c.moveToNext();
            long dbId = c.getLong(c.getColumnIndexOrThrow(JELO_ID));
            String naziv = c.getString(c.getColumnIndexOrThrow(JELO_NAZIV));
            double serving = c.getDouble(c.getColumnIndexOrThrow(JELO_SERVING));
            double kalorije = c.getDouble(c.getColumnIndexOrThrow(JELO_KALORIJE));
            double karbohidrati = c.getDouble(c.getColumnIndexOrThrow(JELO_KARBOHIDRATI));
            double proteini = c.getDouble(c.getColumnIndexOrThrow(JELO_PROTEINI));
            double masti = c.getDouble(c.getColumnIndexOrThrow(JELO_MASTI));

            Jelo j = new Jelo(naziv, serving, kalorije, karbohidrati, proteini, masti);
            j.setDbId(dbId);
            return j;
        }
        return null;
    }

    //UNOS
    public long dodajUnos(Unos u)
    {
        ContentValues noviUnos = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        noviUnos.put(UNOS_IDJELA, u.getJelo().getDbId());
        noviUnos.put(UNOS_KOLICINA, u.getKolicina());
        noviUnos.put(UNOS_DATUM, u.getDatumUnosa());

        try
        {
            return db.insert(UNOS_TABLE, null, noviUnos);
        }
        catch (SQLiteConstraintException e)
        {
            return -1;
        }
    }

    public void obrisiUnos(Unos u)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(UNOS_TABLE, "_id = " + u.getDbId(), null);
    }

    public ArrayList<Unos> dajUnose(String datum)
    {
        ArrayList<Unos> rez = new ArrayList<Unos>();
        SQLiteDatabase db = getWritableDatabase();
        String[] kolone = {UNOS_ID, UNOS_KOLICINA, UNOS_IDJELA, UNOS_DATUM};
        Cursor c = db.query(UNOS_TABLE, kolone, UNOS_DATUM + " LIKE '" + datum + "'", null, null, null, null);

        while (c.moveToNext())
        {
            long dbId = c.getLong(c.getColumnIndexOrThrow(UNOS_ID));
            double kolicina = c.getDouble(c.getColumnIndexOrThrow(UNOS_KOLICINA));
            long idJela = c.getLong(c.getColumnIndexOrThrow(UNOS_IDJELA));
            String datumUnosa = c.getString(c.getColumnIndexOrThrow(UNOS_DATUM));

            Jelo j = dajJeloPoId(idJela);

            Unos u = new Unos(j, kolicina, datumUnosa);
            u.setDbId(dbId);
            rez.add(u);
        }

        return rez;
    }

    public ArrayList<Unos> dajUnoseSJelom(long jeloId)
    {
        ArrayList<Unos> rez = new ArrayList<Unos>();
        SQLiteDatabase db = getWritableDatabase();
        String[] kolone = {UNOS_ID, UNOS_KOLICINA, UNOS_IDJELA, UNOS_DATUM};
        Cursor c = db.query(UNOS_TABLE, kolone, UNOS_IDJELA + " = " + jeloId, null, null, null, null);

        while (c.moveToNext())
        {
            long dbId = c.getLong(c.getColumnIndexOrThrow(UNOS_ID));
            double kolicina = c.getDouble(c.getColumnIndexOrThrow(UNOS_KOLICINA));
            long idJela = c.getLong(c.getColumnIndexOrThrow(UNOS_IDJELA));
            String datumUnosa = c.getString(c.getColumnIndexOrThrow(UNOS_DATUM));

            Jelo j = dajJeloPoId(idJela);

            Unos u = new Unos(j, kolicina, datumUnosa);
            u.setDbId(dbId);
            rez.add(u);
        }

        return rez;
    }

    public void updateUnos(Unos u)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UNOS_KOLICINA, u.getKolicina());
        db.update(UNOS_TABLE, cv, "_id = " + u.getDbId(), null);
    }

    //CILJ
    public Cilj dajCilj(String datum)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] kolone = {CILJ_DATUM, CILJ_KALORIJE, CILJ_KALORIJE_C, CILJ_KARBOHIDRATI, CILJ_KARBOHIDRATI_C, CILJ_PROTEINI, CILJ_PROTEINI_C, CILJ_MASTI, CILJ_MASTI_C};
        Cursor c = db.query(CILJ_TABLE, kolone, CILJ_DATUM + " LIKE '" + datum + "'", null, null, null, null);
        if (c != null && c.getCount() > 0)
        {
            c.moveToNext();
            String datumCilja = c.getString(c.getColumnIndexOrThrow(CILJ_DATUM));
            double kalorije = c.getDouble(c.getColumnIndexOrThrow(CILJ_KALORIJE));
            double karbohidrati = c.getDouble(c.getColumnIndexOrThrow(CILJ_KARBOHIDRATI));
            double proteini = c.getDouble(c.getColumnIndexOrThrow(CILJ_PROTEINI));
            double masti = c.getDouble(c.getColumnIndexOrThrow(CILJ_MASTI));
            double kalorijeC = c.getDouble(c.getColumnIndexOrThrow(CILJ_KALORIJE_C));
            double karbohidratiC = c.getDouble(c.getColumnIndexOrThrow(CILJ_KARBOHIDRATI_C));
            double proteiniC = c.getDouble(c.getColumnIndexOrThrow(CILJ_PROTEINI_C));
            double mastiC = c.getDouble(c.getColumnIndexOrThrow(CILJ_MASTI_C));

            Cilj cilj = new Cilj(kalorijeC, karbohidratiC, mastiC, proteiniC);
            cilj.setProteini(proteini);
            cilj.setKarbohidrati(karbohidrati);
            cilj.setMasti(masti);
            cilj.setKalorije(kalorije);
            cilj.setDatum(datumCilja);

            return cilj;
        }
        return null;
    }

    public void updateCilj(Cilj c)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] idKolona = {CILJ_ID};
        Cursor cImaLi = db.query(CILJ_TABLE, idKolona, CILJ_DATUM + " LIKE '" + c.getDatum() + "'", null, null, null, null);

        ContentValues cv = new ContentValues();
        cv.put(CILJ_DATUM, c.getDatum());
        cv.put(CILJ_KALORIJE, c.getKalorije());
        cv.put(CILJ_KARBOHIDRATI, c.getKarbohidrati());
        cv.put(CILJ_PROTEINI, c.getProteini());
        cv.put(CILJ_MASTI, c.getMasti());
        cv.put(CILJ_KALORIJE_C, c.getKalorijeC());
        cv.put(CILJ_KARBOHIDRATI_C, c.getKarbohidratiC());
        cv.put(CILJ_PROTEINI_C, c.getProteiniC());
        cv.put(CILJ_MASTI_C, c.getMastiC());

        if (cImaLi != null && cImaLi.getCount() > 0)
            db.update(CILJ_TABLE, cv, CILJ_DATUM + " LIKE '" + c.getDatum() + "'", null);
        else
            db.insert(CILJ_TABLE, null, cv);
    }

    public Cilj dajNajnovijiCilj(){
        SQLiteDatabase db = getWritableDatabase();
        String[] idKolona = {CILJ_DATUM};
        Cursor c = db.query(CILJ_TABLE, idKolona, null, null, null, null,
                "date(substr(datum,7,4) || '-' || substr(datum, 4, 2) || '-' || substr(datum, 1, 2)) DESC");
        if (c != null &c.getCount() > 0)
        {
            c.moveToNext();
            Cilj cilj = dajCilj(c.getString(c.getColumnIndexOrThrow(CILJ_DATUM)));
            return cilj;
        }

        return null;
    }
}
