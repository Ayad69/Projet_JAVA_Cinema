CREATE TABLE utilisateur(
   id_utilisateur serial,
   nom VARCHAR(100) NOT NULL,
   prenom VARCHAR(100),
   login VARCHAR(50) NOT NULL,
   mdp VARCHAR(255) NOT NULL,
   CONSTRAINT utilisateur_PK PRIMARY KEY(id_utilisateur)
);

-- Création de la table franchise
-- Un utilisateur peut être gérant d'une franchise
CREATE TABLE franchise (
   id_franchise SERIAL PRIMARY KEY,
   nom_franchise VARCHAR(100) NOT NULL,
   siege_social TEXT,
   id_gerant INTEGER,
   CONSTRAINT fk_gerant_franchise FOREIGN KEY(id_gerant) REFERENCES utilisateur(id_utilisateur) ON DELETE
   SET
      NULL
);

-- Mise à jour/Création de la table cinema
-- Un cinéma appartient à une seule franchise
CREATE TABLE cinema (
   id_cinema SERIAL PRIMARY KEY,
   denomination VARCHAR(100) NOT NULL,
   adresse TEXT,
   ville VARCHAR(100),
   id_franchise INTEGER NOT NULL,
   CONSTRAINT fk_franchise_cinema FOREIGN KEY(id_franchise) REFERENCES franchise(id_franchise) ON DELETE CASCADE
);

-- Création de la table salle
-- La colonne id_cinema sert de clé étrangère pour lier la salle à un cinéma
CREATE TABLE salle (
   id_salle SERIAL PRIMARY KEY,
   numero INTEGER NOT NULL,
   description VARCHAR(255),
   nb_places INTEGER NOT NULL,
   id_cinema INTEGER NOT NULL,
   CONSTRAINT fk_cinema FOREIGN KEY(id_cinema) REFERENCES cinema(id_cinema) ON DELETE CASCADE
);

-- Index pour optimiser les recherches
CREATE INDEX idx_cinema_franchise ON cinema(id_franchise);

CREATE INDEX idx_salle_cinema ON salle(id_cinema);

CREATE INDEX idx_franchise_gerant ON franchise(id_gerant);