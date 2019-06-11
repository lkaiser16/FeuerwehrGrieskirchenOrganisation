package com.example.feuerwehrorganisation;

import java.util.HashMap;
import java.util.Map;

public class Fahrzeug
{
    String bezeichnung;
    Map<String, String> sitzplätze = new HashMap<>();

    public Fahrzeug()
    {
    }

    public Fahrzeug(String bezeichnung, Map<String, String> sitzplätze)
    {
        this.bezeichnung = bezeichnung;
        this.sitzplätze = sitzplätze;
    }

    public String getBezeichnung()
    {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung)
    {
        this.bezeichnung = bezeichnung;
    }

    public Map<String, String> getSitzplätze()
    {
        return sitzplätze;
    }

    public void setSitzplätze(Map<String, String> sitzplätze)
    {
        this.sitzplätze = sitzplätze;
    }
}
