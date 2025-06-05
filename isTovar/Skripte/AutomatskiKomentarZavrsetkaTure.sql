DELIMITER $$

CREATE TRIGGER automatski_komentar_zavrsetka_ture
AFTER UPDATE ON Tura
FOR EACH ROW
BEGIN
    IF OLD.status = 'u toku' AND NEW.status = 'zavrseno' THEN
        IF NOT EXISTS (SELECT 1 FROM Komentar WHERE idTure = NEW.idTure) THEN
            INSERT INTO Komentar (idTure, tekst)
            VALUES (NEW.idTure, 'Tura uspješno završena. Nema posebnih napomena.');
        END IF;
    END IF;
END $$

DELIMITER ;