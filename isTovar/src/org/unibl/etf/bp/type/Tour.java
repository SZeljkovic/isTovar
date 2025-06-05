package org.unibl.etf.bp.type;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Tour {

    private final IntegerProperty idTure = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDateTime> vrijemePolaska = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> vrijemeDolaska = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();
    private final IntegerProperty idVozaca = new SimpleIntegerProperty();
    private final IntegerProperty idDispecera = new SimpleIntegerProperty();
    private final DoubleProperty ukupnaCijenaTure = new SimpleDoubleProperty();

    public Tour(int idTure, LocalDateTime vrijemePolaska, LocalDateTime vrijemeDolaska, String status, int idVozaca, int idDispecera, double ukupnaCijenaTure) {
        this.idTure.set(idTure);
        this.vrijemePolaska.set(vrijemePolaska);
        this.vrijemeDolaska.set(vrijemeDolaska);
        this.status.set(status);
        this.idVozaca.set(idVozaca);
        this.idDispecera.set(idDispecera);
        this.ukupnaCijenaTure.set(ukupnaCijenaTure);
    }

    // Getters and Setters
    public IntegerProperty idTureProperty() { return idTure; }
    public ObjectProperty<LocalDateTime> vrijemePolaskaProperty() { return vrijemePolaska; }
    public ObjectProperty<LocalDateTime> vrijemeDolaskaProperty() { return vrijemeDolaska; }
    public StringProperty statusProperty() { return status; }
    public IntegerProperty idVozacaProperty() { return idVozaca; }
    public IntegerProperty idDispeceraProperty() { return idDispecera; }
    public DoubleProperty ukupnaCijenaTureProperty() { return ukupnaCijenaTure; }

}

