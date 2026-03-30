CREATE TABLE log(
    idlog SERIAL PRIMARY KEY,
    tableName VARCHAR(50),
    operation VARCHAR(50),
    dateAction TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ancienContenu TEXT,
    nouveauContenu TEXT
);

CREATE
OR REPLACE FUNCTION insert_log_function(
    p_tableName VARCHAR,
    p_operation VARCHAR,
    p_ancienContenu TEXT,
    p_nouveauContenu TEXT
) RETURNS void LANGUAGE plpgsql AS $ $ BEGIN
INSERT INTO
    log(
        tableName,
        operation,
        ancienContenu,
        nouveauContenu
    )
VALUES
    (
        p_tableName,
        p_operation,
        p_ancienContenu,
        p_nouveauContenu
    );

END;

$ $;

-- SECTION --
-- INSERT
CREATE
OR REPLACE FUNCTION trigger_section_create() RETURNS TRIGGER AS $ $ BEGIN PERFORM insert_log_function(
    'section',
    'INSERT',
    '',
    'ID: ' || NEW.idSection || ', Libelle: ' || NEW.libelleSection
);

RETURN NEW;

END;

$ $ LANGUAGE plpgsql;

CREATE TRIGGER section_create
AFTER
INSERT
    ON Section FOR EACH ROW EXECUTE FUNCTION trigger_section_create();

-- UPDATE
CREATE
OR REPLACE FUNCTION trigger_section_update() RETURNS TRIGGER AS $ $ BEGIN PERFORM insert_log_function(
    'section',
    'UPDATE',
    'ID: ' || OLD.idSection || ', Libelle: ' || OLD.libelleSection,
    'ID: ' || NEW.idSection || ', Libelle: ' || NEW.libelleSection
);

RETURN NEW;

END;

$ $ LANGUAGE plpgsql;

CREATE TRIGGER section_update
AFTER
UPDATE
    ON Section FOR EACH ROW EXECUTE FUNCTION trigger_section_update();

-- DELETE
CREATE
OR REPLACE FUNCTION trigger_section_delete() RETURNS TRIGGER AS $ $ BEGIN PERFORM insert_log_function(
    'section',
    'DELETE',
    'ID: ' || OLD.idSection || ', Libelle: ' || OLD.libelleSection,
    ''
);

RETURN OLD;

END;

$ $ LANGUAGE plpgsql;

CREATE TRIGGER section_delete
AFTER
    DELETE ON Section FOR EACH ROW EXECUTE FUNCTION trigger_section_delete();

-- COURS -- 
-- INSERT
CREATE
OR REPLACE FUNCTION trigger_cours_create() RETURNS TRIGGER AS $ $ BEGIN PERFORM insert_log_function(
    'cours',
    'INSERT',
    '',
    'ID: ' || NEW.idCours || ', Libelle: ' || NEW.libelleCours || ', Description: ' || NEW.DescriptionCours || ', IDSection: ' || NEW.idSection
);

RETURN NEW;

END;

$ $ LANGUAGE plpgsql;

CREATE TRIGGER cours_create
AFTER
INSERT
    ON cours FOR EACH ROW EXECUTE FUNCTION trigger_cours_create();

-- UPDATE
CREATE
OR REPLACE FUNCTION trigger_cours_update() RETURNS TRIGGER AS $ $ BEGIN PERFORM insert_log_function(
    'cours',
    'UPDATE',
    'ID: ' || OLD.idCours || ', Libelle: ' || OLD.libelleCours || ', Description: ' || OLD.DescriptionCours || ', IDSection: ' || OLD.idSection,
    'ID: ' || NEW.idCours || ', Libelle: ' || NEW.libelleCours || ', Description: ' || NEW.DescriptionCours || ', IDSection: ' || NEW.idSection
);

RETURN NEW;

END;

$ $ LANGUAGE plpgsql;

CREATE TRIGGER cours_update
AFTER
UPDATE
    ON cours FOR EACH ROW EXECUTE FUNCTION trigger_cours_update();

-- DELETE
CREATE
OR REPLACE FUNCTION trigger_cours_delete() RETURNS TRIGGER AS $ $ BEGIN PERFORM insert_log_function(
    'cours',
    'DELETE',
    'ID: ' || OLD.idCours || ', Libelle: ' || OLD.libelleCours || ', Description: ' || OLD.DescriptionCours || ', IDSection: ' || OLD.idSection,
    ''
);

RETURN OLD;

END;

$ $ LANGUAGE plpgsql;

CREATE TRIGGER cours_delete
AFTER
    DELETE ON cours FOR EACH ROW EXECUTE FUNCTION trigger_cours_delete();

-- ETUDIANT --
-- INSERT
CREATE
OR REPLACE FUNCTION trigger_etudiant_create() RETURNS TRIGGER AS $ $ BEGIN PERFORM insert_log_function(
    'etudiant',
    'INSERT',
    '',
    'ID: ' || NEW.idEtudiant || ', Nom: ' || NEW.nomEtudiant || ', Prenom: ' || NEW.prenomEtudiant || ', IDSection: ' || NEW.idSection
);

RETURN NEW;

END;

$ $ LANGUAGE plpgsql;

CREATE TRIGGER etudiant_create
AFTER
INSERT
    ON etudiant FOR EACH ROW EXECUTE FUNCTION trigger_etudiant_create();

-- UPDATE
CREATE
OR REPLACE FUNCTION trigger_etudiant_update() RETURNS TRIGGER AS $ $ BEGIN PERFORM insert_log_function(
    'etudiant',
    'UPDATE',
    'ID: ' || OLD.idEtudiant || ', Nom: ' || OLD.nomEtudiant || ', Prenom: ' || OLD.prenomEtudiant || ', IDSection: ' || OLD.idSection,
    'ID: ' || NEW.idEtudiant || ', Nom: ' || NEW.nomEtudiant || ', Prenom: ' || NEW.prenomEtudiant || ', IDSection: ' || NEW.idSection
);

RETURN NEW;

END;

$ $ LANGUAGE plpgsql;

CREATE TRIGGER etudiant_update
AFTER
UPDATE
    ON etudiant FOR EACH ROW EXECUTE FUNCTION trigger_etudiant_update();

-- DELETE
CREATE
OR REPLACE FUNCTION trigger_etudiant_delete() RETURNS TRIGGER AS $ $ BEGIN PERFORM insert_log_function(
    'etudiant',
    'DELETE',
    'ID: ' || OLD.idEtudiant || ', Nom: ' || OLD.nomEtudiant || ', Prenom: ' || OLD.prenomEtudiant || ', IDSection: ' || OLD.idSection,
    ''
);

RETURN OLD;

END;

$ $ LANGUAGE plpgsql;

CREATE TRIGGER etudiant_delete
AFTER
    DELETE ON etudiant FOR EACH ROW EXECUTE FUNCTION trigger_etudiant_delete();