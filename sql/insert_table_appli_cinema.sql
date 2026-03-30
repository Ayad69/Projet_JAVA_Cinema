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