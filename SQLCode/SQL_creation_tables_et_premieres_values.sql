USE securaudit;

CREATE TABLE Civilite(
   idCivilite INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   nomCivilite VARCHAR(3) NOT NULL
);

CREATE TABLE CategorieFrais(
   idCategorieFrais INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   nomCategorieFrais VARCHAR(50) NOT NULL
);

CREATE TABLE Industrie(
   idIndustrie INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   raisonSocialeIndustrie VARCHAR(50) NOT NULL,
   siretIndustrie CHAR(14) NOT NULL
);

CREATE TABLE Auditeur(
   idAuditeur INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   nomAuditeur VARCHAR(50) NOT NULL,
   prenomAuditeur VARCHAR(50),
   idCivilite INT NOT NULL,
   FOREIGN KEY(idCivilite) REFERENCES Civilite(idCivilite)
);

CREATE TABLE Audit(
   idAudit INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   dateDebutAudit DATE NOT NULL,
   dureeAudit INT NOT NULL,
   coutJournalierAudit FLOAT NOT NULL,
   idIndustrie INT NOT NULL,
   idAuditeur INT NOT NULL,
   FOREIGN KEY(idIndustrie) REFERENCES Industrie(idIndustrie),
   FOREIGN KEY(idAuditeur) REFERENCES Auditeur(idAuditeur)
);

CREATE TABLE Frais(
   idFrais INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   dateFrais DATE NOT NULL,
   montantFrais FLOAT NOT NULL,
   rembourseFrais BOOLEAN NOT NULL,
   idAudit INT NOT NULL,
   idCategorieFrais INT NOT NULL,
   FOREIGN KEY(idAudit) REFERENCES Audit(idAudit),
   FOREIGN KEY(idCategorieFrais) REFERENCES CategorieFrais(idCategorieFrais)
);


INSERT INTO Civilite (nomCivilite) VALUES
("Mme"),
("M.");


INSERT INTO Auditeur (nomAuditeur, prenomAuditeur,idCivilite) VALUES
   ("Bardet", "Antoine", 2),
   ("Delorme", "Christelle", 1),
   ("Foulard", "Eric", 2),
   ("Hoquart", "Gérard", 2),
   ("Jacquet", "Isabelle", 1),
   ("Lopez", "Kévin", 2);


INSERT INTO CategorieFrais (nomCategorieFrais) VALUES
("Restaurant"),
("Hôtel"),
("Trajet"),
("Matériel");