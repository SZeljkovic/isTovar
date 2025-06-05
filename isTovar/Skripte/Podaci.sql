#JAKO JE BITNO NE POKRENUTI SVE ODJEDNOM NEGO POJEDINACNO !!!!!!



INSERT INTO firma (idFirme, naziv, mejl, ziroRacun) VALUES
(1, 'Shell', 'shell@mail.com', '56278887'),
(2, 'Aldi', 'aldi@mail.com', '87762667488'),
(3, 'ZJ', 'zj@gmail.com', '562-888756349070-76');

INSERT INTO adresa (idAdrese, ulica, broj, grad, postanskiBroj, drzava, idFirme) VALUES
(1, 'Viale Tommaso Edison', '110', 'Sesto San Giovanni', '20099', 'Italija', 1),
(2, 'Webergasse', '1', 'Dresden', '1067', 'Njemacka', 2),
(3, 'Cara Lazara', '33', 'Banja Luka', '78000', 'BiH', 3);

INSERT INTO dispecer (idKorisnika, status) VALUES
(1, 1),
(7, 1);

INSERT INTO dokument (idDokumenta, tekst, cijenaPrevoza, idTovara) VALUES
(1, 'Ovo je tekst prvog dokumenta', 1750.00, 1),
(3, 'Tekst dva.', 1000.00, 2);

INSERT INTO kamion (idKamiona, tip, marka, konjskeSnage, idPrikolice, vrstaGoriva, godinaProizvodnje, registarskaOznaka, kilometraza) VALUES
(1, 'šleper', 'Daf', 480, 1, 'dizel', 2016, 'T87-M-987', 312000),
(2, 'Actros', 'Mercedes', 510, NULL, 'dizel', 2020, 'DD MI 110', 123000);

INSERT INTO korisnik (idKorisnika, korisnickoIme, ime, prezime, email, lozinka, uloga, brojTelefona) VALUES
(1, 'marija', 'Marija', 'Jo', 'mjo@gmail.com', 'marija', '0', '065/029-357'),
(2, 'isidora', 'Isidora', 'Ra', 'ir@gmail.com', 'isidora', '1', '065/721/857'),
(6, 'timij', 'timi', 'j', 'timi', 'afaa07aebe58d68a73b095364c615bc4837281ecc836f6d34be10ea37cd0e2f6', '1', 'timi'),
(7, 'srdjan', 'srdjan', 'srdjan', 'srdjan', 'eba52f5125645cc61c8d95263d3d972b114a529709703628d220f8b61262eaba', '0', 'srdjan'),
(8, 'marko', 'Marko', 'Markovic', 'markom@mail.com', '8c5faf36ce0dae48351f5e09c5133fdaddcf52d9baf4369db027766a12c1742f', '1', '063/111-115');

INSERT INTO prikolica (idPrikolice, vrsta, nosivost, godinaProizvodnje, registarskaOznaka) VALUES
(1, 'cerada', 32000, 2018, 'M45-J-101'),
(2, 'cisterna', 23000, 2014, 'V65-N-545');


INSERT INTO problem (idProblem, idKorisnika, tekstProblema, datum, status) VALUES
(1, 6, 'Prvi problem', '2025-05-14', 0);


INSERT INTO telefon (brojTelefona, fax, idFirme) VALUES
('051/111-115', 1, 3);

INSERT INTO tura (idTure, vrijemePolaska, vrijemeDolaska, status, idVozaca, idDispecera, ukupnaCijenaTure) VALUES
(3, '2025-07-01 15:30:00', '2025-07-04 18:00:00', 'u toku', 6, 7, 2750),
(4, '2025-05-29 00:00:00', '2025-05-31 00:00:00', 'u toku', 2, 7, 5583),
(5, '2025-05-21 12:10:00', '2025-05-23 04:15:00', 'u toku', 8, 7, 5645),
(6, '2025-05-13 12:10:00', '2025-05-17 04:15:00', 'u toku', 8, 7, 7650);


INSERT INTO tura_firma_izvorista (idTure, idFirme, vrijemeUtovara) VALUES
(3, 1, '2025-07-02 12:00:00');


INSERT INTO tura_firma_odredista (idFirme, idTure, vrijemeIstovara) VALUES
(2, 3, '2025-07-03 08:30:00');


INSERT INTO tovar (idTovara, opis, masa, opasan, idTure_Izvorista, idFirme_Izvorista, idFirme_Odredista, idTure_Odredista) VALUES
(1, 'Euro Paleta', 1044, 0, 3, 1, 2, 3),
(2, 'Paleta2', 999, 0, 3, 1, 2, 3);


INSERT INTO vozac (idKorisnika, idKamiona, brojDozvole, licenca, dostupnost) VALUES
(2, NULL, 'B9258756', 'KOD95', 1),
(6, 1, 'abcd', 'abcd', 1),
(8, NULL, 'B2598276', 'VT', 1);

