package com.diplomski.karim.aplikacija;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import static android.provider.CalendarContract.CalendarCache.URI;

public class GlavnaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListaFragment.GlavnaAktivnostInterface, PocetnaFragment.GlavnaAktivnostInterface, PostaviFragment.GlavnaAktivnostInterface {

    private BazaOpenHelper boh = new BazaOpenHelper(this);
    private String datum = Alati.dajDanasnjiDatum();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_glavna);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new PocetnaFragment(), "frag_pocetna").commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.glavna, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(URI.parse("mailto:"));
            emailIntent.setType("text/plain");
            String[] TO = new String[1];
            TO[0] = "kalomerovi1@etf.unsa.ba";
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Zdravo,\n\npronasao sam sljedeći bug u aplikaciji:\n\n<opišite bug ovdje>\n\nLP");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "BUG REPORT");
            if (emailIntent.resolveActivity(getPackageManager()) != null)
                startActivity(emailIntent);
            else
                Alati.prikaziPoruku(this, "Nemoguće izvršiti akciju.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_pocetna) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new PocetnaFragment(), "frag_pocetna").commit();
        } else if (id == R.id.nav_postavi) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new PostaviFragment(), "frag_postavi").commit();
        } else if (id == R.id.nav_lista) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ListaFragment(), "frag_lista").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public ArrayList<Jelo> dajListuJela(String query) {

        return boh.dajJelaPoNazivu(query);
    }

    @Override
    public void dodajJelo(Jelo j) {
        long dbId = boh.dodajJelo(j);
        j.setDbId(dbId);
    }

    @Override
    public void obrisiJelo(Jelo j) {
        boh.arhivirajJelo(j);
    }

    @Override
    public void editujJelo(long editId, Jelo novoJelo) {
        novoJelo.setDbId(editId);
        boh.updateJelo(novoJelo);
    }

    @Override
    public Cilj dajCilj(String datum) {
        Cilj c = boh.dajCilj(datum);
        if (c == null)
        {
            c = boh.dajNajnovijiCilj();
            if (c == null)
            {
                c = new Cilj();
                boh.updateCilj(c);
            }
            else
            {
                c.setDatum(datum);
                boh.updateCilj(c);
            }
        }
        return c;
    }

    @Override
    public void updateCilj(Cilj c) {
        boh.updateCilj(c);
    }

    @Override
    public void updateCiljProgress(String datum) {
        ArrayList unosi = boh.dajUnose(datum);
        Cilj c = dajCilj(datum);
        c.setDatum(datum);
        c.updateProgress(unosi);
        boh.updateCilj(c);
    }

    @Override
    public void dodajUnos(Unos u) {
        long dbId = boh.dodajUnos(u);
        u.setDbId(dbId);
    }

    @Override
    public ArrayList<Unos> dajListuUnosa(String datum) {
        return boh.dajUnose(datum);
    }

    @Override
    public void obrisiUnos(Unos u) {
        boh.obrisiUnos(u);
        Jelo j = u.getJelo();
        ArrayList<Unos> unosiSIstimJelom = boh.dajUnoseSJelom(j.getDbId());
        if (unosiSIstimJelom.size() == 0)
            boh.obrisiJelo(j);
    }

    @Override
    public void editUnos(Unos u) {
        boh.updateUnos(u);
    }

    @Override
    public void updateDatum(String datum) {
        this.datum = datum;
    }

    @Override
    public String dajDatum() {
        return datum;
    }
}
