-- Création de la base de données
CREATE DATABASE gestion_cinema;

-- Optionnel : Création d'un utilisateur dédié pour plus de sécurité
CREATE USER cinema_usr WITH PASSWORD 'cinema_pwd';

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