DELIMITER $$

CREATE PROCEDURE ProvjeriDostupnostVozaca(
    IN p_idVozaca INT,
    IN p_vrijemePolaska DATETIME,
    IN p_vrijemeDolaska DATETIME,
    OUT p_dostupan TINYINT
)
BEGIN
    DECLARE brojZauzetihTura INT;

    SELECT COUNT(*) INTO brojZauzetihTura
    FROM tura
    WHERE idVozaca = p_idVozaca
      AND (
          (p_vrijemePolaska BETWEEN vrijemePolaska AND vrijemeDolaska)
          OR (p_vrijemeDolaska BETWEEN vrijemePolaska AND vrijemeDolaska)
          OR (vrijemePolaska BETWEEN p_vrijemePolaska AND p_vrijemeDolaska)
      );

    IF brojZauzetihTura = 0 THEN
        SET p_dostupan = 1;
    ELSE
        SET p_dostupan = 0;
    END IF;
END $$

DELIMITER ;