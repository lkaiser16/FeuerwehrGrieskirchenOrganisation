package com.example.feuerwehrorganisation.NewAuftrag;

import com.example.feuerwehrorganisation.Fahrzeug;

import java.io.Serializable;
import java.util.List;

public class Auftrag implements Serializable
{
    String name;
    List<Fahrzeug> fahrzeuge;
    String d;
    String uhrzeit;
    String erstelltVon;

    public Auftrag()
    {
    }


    public Auftrag(String name, List<Fahrzeug> fahrzeuge, String d, String uhrzeit, String erstelltVon)
    {
        this.name = name;
        this.fahrzeuge = fahrzeuge;
        this.d = d;
        this.uhrzeit = uhrzeit;
        this.erstelltVon = erstelltVon;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Fahrzeug> getFahrzeuge()
    {
        return fahrzeuge;
    }

    public void setFahrzeuge(List<Fahrzeug> fahrzeuge)
    {
        this.fahrzeuge = fahrzeuge;
    }

    public String getD()
    {
        return d;
    }

    public void setD(String d)
    {
        this.d = d;
    }

    public String getUhrzeit()
    {
        return uhrzeit;
    }

    public void setUhrzeit(String uhrzeit)
    {
        this.uhrzeit = uhrzeit;
    }

    public String getErstelltVon()
    {
        return erstelltVon;
    }

    public void setErstelltVon(String erstelltVon)
    {
        this.erstelltVon = erstelltVon;
    }

    @Override
    public String toString()
    {
        return "Auftrag{" + "name='" + name + '\'' + ", fahrzeuge=" + fahrzeuge + ", d='" + d + '\'' + ", uhrzeit='" + uhrzeit + '\'' + ", erstelltVon='" + erstelltVon + '\'' + '}';
    }
}