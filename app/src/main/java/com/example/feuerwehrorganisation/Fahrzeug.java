package com.example.feuerwehrorganisation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Fahrzeug implements Serializable
{
    String bezeichnung;
    Map<String, String> sitzplätze = new HashMap<>();
    boolean isSelected = false;

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

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    @Override
    public String toString()
    {
        return "Fahrzeug{" + "bezeichnung='" + bezeichnung + '\'' + ", sitzplätze=" + sitzplätze + ", isSelected=" + isSelected + '}';
    }
}
