package org.unibl.etf.bp.type;

import javafx.beans.property.*;

public class Driver {
    private IntegerProperty idKorisnika;
    private StringProperty korisnickoIme;
    private StringProperty ime;
    private StringProperty prezime;
    private StringProperty email;
    private StringProperty brojTelefona;
    private StringProperty brojDozvole;
    private StringProperty licenca;
    private IntegerProperty idKamiona;
    private StringProperty dostupnost;
    private int idKor;
    private String name;
    private String surname;

    public Driver(int idKorisnika, String korisnickoIme, String ime, String prezime, String email,
                  String brojTelefona, String brojDozvole, String licenca, Integer idKamiona, String dostupnost) {
        this.idKorisnika = new SimpleIntegerProperty(idKorisnika);
        this.korisnickoIme = new SimpleStringProperty(korisnickoIme);
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.email = new SimpleStringProperty(email);
        this.brojTelefona = new SimpleStringProperty(brojTelefona);
        this.brojDozvole = new SimpleStringProperty(brojDozvole);
        this.licenca = new SimpleStringProperty(licenca);
        this.idKamiona = new SimpleIntegerProperty(idKamiona != null ? idKamiona : 0);
        this.dostupnost = new SimpleStringProperty(dostupnost);
        this.idKor = idKorisnika;
        this.name = ime;
        this.surname = prezime;
    }

    public IntegerProperty idKorisnikaProperty() { return idKorisnika; }
    public StringProperty korisnickoImeProperty() { return korisnickoIme; }
    public StringProperty imeProperty() { return ime; }
    public StringProperty prezimeProperty() { return prezime; }
    public StringProperty emailProperty() { return email; }
    public StringProperty brojTelefonaProperty() { return brojTelefona; }
    public StringProperty brojDozvoleProperty() { return brojDozvole; }
    public StringProperty licencaProperty() { return licenca; }
    public IntegerProperty idKamionaProperty() { return idKamiona; }
    public StringProperty dostupnostProperty() { return dostupnost; }
    public int getIdKorisnika() { return this.idKor;}
    public String getIme() { return this.name;}
    public String getPrezime() { return this.surname;}

}

