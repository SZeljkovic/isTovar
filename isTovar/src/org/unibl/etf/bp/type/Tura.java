package org.unibl.etf.bp.type;

import javafx.beans.property.*;
import java.sql.Timestamp;

public class Tura {

    private final IntegerProperty idTure;
    private final ObjectProperty<Timestamp> departureTime;
    private final ObjectProperty<Timestamp> arrivalTime;
    private final StringProperty status;
    private final DoubleProperty price;

    public Tura(int idTure, Timestamp departureTime, Timestamp arrivalTime, String status, double price) {
        this.idTure = new SimpleIntegerProperty(idTure);
        this.departureTime = new SimpleObjectProperty<>(departureTime);
        this.arrivalTime = new SimpleObjectProperty<>(arrivalTime);
        this.status = new SimpleStringProperty(status);
        this.price = new SimpleDoubleProperty(price);
    }

    public IntegerProperty getIdTureProperty() {
        return idTure;
    }

    public ObjectProperty<Timestamp> getDepartureTimeProperty() {
        return departureTime;
    }

    public ObjectProperty<Timestamp> getArrivalTimeProperty() {
        return arrivalTime;
    }

    public StringProperty getStatusProperty() {
        return status;
    }

    public DoubleProperty getPriceProperty() {
        return price;
    }
}
