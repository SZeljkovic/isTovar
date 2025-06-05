DELIMITER $$

CREATE TRIGGER azuriraj_ukupnu_cijenu_ture
AFTER INSERT ON dokument
FOR EACH ROW
BEGIN
    DECLARE ukupno DECIMAL(10,2);

    SELECT SUM(d.cijenaPrevoza) INTO ukupno
    FROM dokument d
    JOIN tovar t ON d.idTovara = t.idTovara
    WHERE t.idTure_Izvorista = (
        SELECT t2.idTure_Izvorista
        FROM tovar t2
        WHERE t2.idTovara = NEW.idTovara
        LIMIT 1
    );

    UPDATE tura
    SET ukupnaCijenaTure = ukupno
    WHERE idTure = (
        SELECT t3.idTure_Izvorista
        FROM tovar t3
        WHERE t3.idTovara = NEW.idTovara
        LIMIT 1
    );
END $$

DELIMITER ;
