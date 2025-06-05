package org.unibl.etf.bp.type;

import javafx.beans.property.*;

public class Trailer {
    private IntegerProperty idPrikolice;
    private StringProperty vrsta;
    private IntegerProperty nosivost;
    private IntegerProperty godinaProizvodnje;
    private StringProperty registarskaOznaka;

    public Trailer(int idPrikolice, String vrsta, int nosivost, int godinaProizvodnje, String registarskaOznaka) {
        this.idPrikolice = new SimpleIntegerProperty(idPrikolice);
        this.vrsta = new SimpleStringProperty(vrsta);
        this.nosivost = new SimpleIntegerProperty(nosivost);
        this.godinaProizvodnje = new SimpleIntegerProperty(godinaProizvodnje);
        this.registarskaOznaka = new SimpleStringProperty(registarskaOznaka);
    }

    public IntegerProperty idPrikoliceProperty() { return idPrikolice; }
    public StringProperty vrstaProperty() { return vrsta; }
    public IntegerProperty nosivostProperty() { return nosivost; }
    public IntegerProperty godinaProizvodnjeProperty() { return godinaProizvodnje; }
    public StringProperty registarskaOznakaProperty() { return registarskaOznaka; }
}
