package org.unibl.etf.bp.type;

import javafx.beans.property.*;

public class Truck {
    private IntegerProperty idKamiona;
    private StringProperty tip;
    private StringProperty marka;
    private IntegerProperty konjskeSnage;
    private IntegerProperty idPrikolice;
    private StringProperty vrstaGoriva;
    private IntegerProperty godinaProizvodnje;
    private StringProperty registarskaOznaka;
    private IntegerProperty kilometraza;

    public Truck(int idKamiona, String tip, String marka, int konjskeSnage, Integer idPrikolice,
                 String vrstaGoriva, int godinaProizvodnje, String registarskaOznaka, int kilometraza) {
        this.idKamiona = new SimpleIntegerProperty(idKamiona);
        this.tip = new SimpleStringProperty(tip);
        this.marka = new SimpleStringProperty(marka);
        this.konjskeSnage = new SimpleIntegerProperty(konjskeSnage);
        this.idPrikolice = new SimpleIntegerProperty(idPrikolice != null ? idPrikolice : 0);
        this.vrstaGoriva = new SimpleStringProperty(vrstaGoriva);
        this.godinaProizvodnje = new SimpleIntegerProperty(godinaProizvodnje);
        this.registarskaOznaka = new SimpleStringProperty(registarskaOznaka);
        this.kilometraza = new SimpleIntegerProperty(kilometraza);
    }

    public IntegerProperty idKamionaProperty() { return idKamiona; }
    public StringProperty tipProperty() { return tip; }
    public StringProperty markaProperty() { return marka; }
    public IntegerProperty konjskeSnageProperty() { return konjskeSnage; }
    public IntegerProperty idPrikoliceProperty() { return idPrikolice; }
    public StringProperty vrstaGorivaProperty() { return vrstaGoriva; }
    public IntegerProperty godinaProizvodnjeProperty() { return godinaProizvodnje; }
    public StringProperty registarskaOznakaProperty() { return registarskaOznaka; }
    public IntegerProperty kilometrazaProperty() { return kilometraza; }
}
