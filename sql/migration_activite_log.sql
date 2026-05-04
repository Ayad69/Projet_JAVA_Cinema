-- À exécuter sur une base déjà existante si la table n'est pas encore créée.
CREATE TABLE IF NOT EXISTS activite_log (
   id_log SERIAL PRIMARY KEY,
   id_utilisateur INTEGER,
   type_action VARCHAR(30) NOT NULL,
   type_entite VARCHAR(30) NOT NULL,
   id_entite INTEGER,
   detail TEXT,
   date_heure TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_activite_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_activite_date ON activite_log(date_heure);
CREATE INDEX IF NOT EXISTS idx_activite_user ON activite_log(id_utilisateur);
