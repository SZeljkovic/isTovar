DELIMITER $$

CREATE TRIGGER dodaj_na_ukupnu_cijenu_ture
AFTER INSERT ON dokument
FOR EACH ROW
BEGIN
    DECLARE id_ture INT;
    
    SELECT idTure_Izvorista
    INTO id_ture
    FROM tovar
    WHERE idTovara = NEW.idTovara;

    UPDATE tura
    SET ukupnaCijenaTure = IFNULL(ukupnaCijenaTure, 0) + IFNULL(NEW.cijenaPrevoza, 0)
    WHERE idTure = id_ture;
END $$

DELIMITER ;
