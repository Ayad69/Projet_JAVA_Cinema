

drop table salle;
drop table cinema;
drop table franchise;
drop table if exists activite_log;
drop table utilisateur;



CREATE TABLE utilisateur(
   id_utilisateur serial,
   nom VARCHAR(100) NOT NULL,
   prenom VARCHAR(100),
   login VARCHAR(50) NOT NULL,
   mdp VARCHAR(255) NOT NULL,
   CONSTRAINT utilisateur_PK PRIMARY KEY(id_utilisateur)
);

-- Journal d'activité : qui a ajouté / modifié / supprimé une franchise ou un cinéma
CREATE TABLE activite_log (
   id_log SERIAL PRIMARY KEY,
   id_utilisateur INTEGER,
   type_action VARCHAR(30) NOT NULL,
   type_entite VARCHAR(30) NOT NULL,
   id_entite INTEGER,
   detail TEXT,
   date_heure TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_activite_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur) ON DELETE SET NULL
);

CREATE INDEX idx_activite_date ON activite_log(date_heure);
CREATE INDEX idx_activite_user ON activite_log(id_utilisateur);

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

-- 1. Insertion des Utilisateurs (Gérants potentiels)
INSERT INTO
	utilisateur (nom, prenom, login, mdp)
VALUES
	(
		'Dupont',
		'Jean',
		'jean.dupont@email.com',
		'jean'
	),
	(
		'Martin',
		'Alice',
		'alice.martin@email.com',
		'alice'
	),
	(
		'Bernard',
		'Lucas',
		'lucas.bernard@email.com',
		'lucas'
	);

-- 2. Insertion des Franchises
-- On lie ici les franchises aux utilisateurs créés précédemment
INSERT INTO
	franchise (nom_franchise, siege_social, id_gerant)
VALUES
	('CinéMax', '12 rue de la Paix, Paris', 1),
	('Écran Total', '45 avenue des Arts, Lyon', 2);

-- 3. Insertion des Cinémas
-- Chaque cinéma est rattaché à une franchise via son ID
INSERT INTO
	cinema (denomination, adresse, ville, id_franchise)
VALUES
	(
		'CinéMax Étoile',
		'5 Place de l''Étoile',
		'Paris',
		1
	),
	(
		'CinéMax Rivoli',
		'100 rue de Rivoli',
		'Paris',
		1
	),
	(
		'Le Grand Écran',
		'8 rue de la République',
		'Lyon',
		2
	);

-- 4. Insertion des Salles
-- On crée plusieurs salles pour chaque cinéma
INSERT INTO
	salle (numero, description, nb_places, id_cinema)
VALUES
	-- Salles pour CinéMax Étoile (ID 1)
	(1, 'Salle Prestige', 150, 1),
	(2, 'Salle 2', 80, 1),
	(3, 'Salle 3', 80, 1),
	-- Salles pour CinéMax Rivoli (ID 2)
	(4, 'Grande Salle', 300, 2),
	(5, 'Petite Salle', 50, 2),
	-- Salles pour Le Grand Écran (ID 3)
	(6, 'Salle IMAX', 450, 3),
	(7, 'Salle Horizon', 120, 3);



	-- 1. Droit de se connecter à la base spécifique
    GRANT CONNECT ON DATABASE gestion_cinema TO cinema_usr;

    -- 2. Droit d'utiliser le schéma public
    GRANT USAGE ON SCHEMA public TO cinema_usr;
    -- 1. Droits complets (Select, Insert, Update, Delete) sur les tables actuelles
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO cinema_usr;

    -- 2. TRES IMPORTANT : Droits sur les séquences (pour les ID auto-incrémentés)
    GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO cinema_usr;

    -- 3. Pour que ces droits s'appliquent aussi aux FUTURES tables créées :
    ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO cinema_usr;
    ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT USAGE, SELECT ON SEQUENCES TO cinema_usr;