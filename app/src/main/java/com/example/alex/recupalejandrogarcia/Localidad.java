package com.example.alex.recupalejandrogarcia;

import java.io.Serializable;

/**
 * Created by Alex on 01/03/2018.
 */

public class Localidad implements Serializable {

    String localidad;
    String latitud;
    String longitud;
    String codigoPais;

    public Localidad() {
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }
}
