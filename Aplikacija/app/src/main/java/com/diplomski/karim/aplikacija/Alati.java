package com.diplomski.karim.aplikacija;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alati {
    public static int RC_DAJ_NOVO_JELO = 1;
    public static int RC_EDITUJ_JELO = 2;
    public static int RC_ZABILJEZI_JELO = 3;
    public static int RC_DAJ_KOLICINU = 4;
    public static int RC_EDITUJ_UNOS = 5;
    private static String FORMAT_DATUM = "dd.MM.yyyy.";
    public static void prikaziPoruku(Context c, String p)
    {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(c);
        dlgAlert.setMessage(p);
        dlgAlert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dlgAlert.create().show();
    }
    public static String dajDanasnjiDatum()
    {
        Calendar c = Calendar.getInstance();
        Date danas = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATUM);
        String danasString = sdf.format(danas);
        return danasString;
    }
    public static String dajDatumPrije(String d) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATUM);
        Date date = dateFormat.parse(d);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        String jucerString = dateFormat.format(calendar.getTime());
        return jucerString;
    }
    public static String dajDatumPoslije(String d) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATUM);
        Date date = dateFormat.parse(d);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String sutraString = dateFormat.format(calendar.getTime());
        return sutraString;
    }
}
