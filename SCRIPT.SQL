DROP DATABASE IF EXISTS BiblioSIO;

CREATE DATABASE IF NOT EXISTS BiblioSIO;
USE BiblioSIO;
# -----------------------------------------------------------------------------
#       TABLE : EXEMPLAIRE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS EXEMPLAIRE
 (
   IDRE CHAR(32) NOT NULL  ,
   NUMEX CHAR(32) NOT NULL  ,
   TITREEX CHAR(32) NULL  ,
   DATEPARUEX TEXT NULL  ,
   STATUTEX CHAR(32) NULL  
   , PRIMARY KEY (IDRE,NUMEX) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE EXEMPLAIRE
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_EXEMPLAIRE_REVUE
     ON EXEMPLAIRE (IDRE ASC);

# -----------------------------------------------------------------------------
#       TABLE : EMPRUNT
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS EMPRUNT
 (
   IDEMPRUNT CHAR(32) NOT NULL  ,
   NUMEX CHAR(32) NOT NULL  ,
   IDRE CHAR(32) NOT NULL  ,
   NUMEROEMP CHAR(32) NOT NULL  ,
   DATEDEBEMPRUNT CHAR(32) NULL  ,
   DATERETOUREMPRUNT CHAR(32) NULL  ,
   STATUTEMPRUNT CHAR(32) NULL  
   , PRIMARY KEY (IDEMPRUNT) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE EMPRUNT
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_EMPRUNT_EXEMPLAIRE
     ON EMPRUNT (IDRE ASC,NUMEX ASC);

CREATE  INDEX I_FK_EMPRUNT_EMPRUNTEUR
     ON EMPRUNT (NUMEROEMP ASC);

# -----------------------------------------------------------------------------
#       TABLE : REVUE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS REVUE
 (
   IDRE CHAR(32) NOT NULL  ,
   TITRERE CHAR(32) NULL  
   , PRIMARY KEY (IDRE) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : EMPRUNTEUR
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS EMPRUNTEUR
 (
   NUMEROEMP CHAR(32) NOT NULL  ,
   NOMEMP CHAR(32) NULL  ,
   PRENOMEMP CHAR(32) NULL  ,
   MAILEMP CHAR(32) NULL  
   , PRIMARY KEY (NUMEROEMP) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : ARTICLE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ARTICLE
 (
   IDART CHAR(32) NOT NULL  ,
   IDRE CHAR(32) NOT NULL  ,
   NUMEX CHAR(32) NOT NULL  ,
   TITREART CHAR(32) NULL  ,
   DESCART CHAR(32) NULL  
   , PRIMARY KEY (IDART) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE ARTICLE
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_ARTICLE_EXEMPLAIRE
     ON ARTICLE (IDRE ASC,NUMEX ASC);


# -----------------------------------------------------------------------------
#       CREATION DES REFERENCES DE TABLE
# -----------------------------------------------------------------------------


ALTER TABLE EXEMPLAIRE 
  ADD FOREIGN KEY FK_EXEMPLAIRE_REVUE (IDRE)
      REFERENCES REVUE (IDRE) ;


ALTER TABLE EMPRUNT 
  ADD FOREIGN KEY FK_EMPRUNT_EXEMPLAIRE (IDRE,NUMEX)
      REFERENCES EXEMPLAIRE (IDRE,NUMEX) ;


ALTER TABLE EMPRUNT 
  ADD FOREIGN KEY FK_EMPRUNT_EMPRUNTEUR (NUMEROEMP)
      REFERENCES EMPRUNTEUR (NUMEROEMP) ;


ALTER TABLE ARTICLE 
  ADD FOREIGN KEY FK_ARTICLE_EXEMPLAIRE (IDRE,NUMEX)
      REFERENCES EXEMPLAIRE (IDRE,NUMEX) ;

