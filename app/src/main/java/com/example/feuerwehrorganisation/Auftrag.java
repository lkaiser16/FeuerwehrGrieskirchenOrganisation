package com.example.feuerwehrorganisation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Auftrag implements Serializable
{
    String name;
    List<Fahrzeug> fahrzeuge;
    String d;
    String uhrzeit;

    public Auftrag()
    {
    }

    public Auftrag(String name, List<Fahrzeug> fahrzeuge, String d, String uhrzeit)
    {
        this.name = name;
        this.fahrzeuge = fahrzeuge;
        this.d = d;
        this.uhrzeit = uhrzeit;
    }

    public String getUhrzeit()
    {
        return uhrzeit;
    }

    public void setUhrzeit(String uhrzeit)
    {
        this.uhrzeit = uhrzeit;
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


}