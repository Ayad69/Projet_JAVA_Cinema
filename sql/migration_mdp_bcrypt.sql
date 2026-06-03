-- À exécuter une fois sur une base existante encore en mots de passe en clair.
-- Même hash que insert_table_appli_cinema.sql (plain : jean / alice / lucas).

UPDATE utilisateur SET mdp = '$2a$10$54YeUjKOCGlyCUtQiKeFyuUWfdy/Plle2kprKldrZv0hDFQqbV2Ba'
WHERE login = 'jean.dupont@email.com' AND mdp = 'jean';

UPDATE utilisateur SET mdp = '$2a$10$7wS/fPn5KWh9FNvLXYHwT.3rT//Uq.sruvU1hAIE8XSvp6SGRWgO2'
WHERE login = 'alice.martin@email.com' AND mdp = 'alice';

UPDATE utilisateur SET mdp = '$2a$10$LqkIpqi4jXIo3Uz4cGaKZuc8jALctihxWkvUv85qzJq8u1qcS6DoG'
WHERE login = 'lucas.bernard@email.com' AND mdp = 'lucas';
