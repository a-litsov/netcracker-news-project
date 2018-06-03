--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.9
-- Dumped by pg_dump version 9.6.7

-- Started on 2018-06-04 01:25:34 MSK

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS users_db;
--
-- TOC entry 2145 (class 1262 OID 16504)
-- Name: users_db; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE users_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


\connect users_db

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12393)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2147 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 16520)
-- Name: rank; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE rank (
    id integer NOT NULL,
    name text,
    color character(6),
    rating_threshold integer
);


--
-- TOC entry 187 (class 1259 OID 16518)
-- Name: rank_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE rank_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2148 (class 0 OID 0)
-- Dependencies: 187
-- Name: rank_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE rank_id_seq OWNED BY rank.id;


--
-- TOC entry 186 (class 1259 OID 16507)
-- Name: user_details; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_details (
    user_id integer NOT NULL,
    firstname text NOT NULL,
    lastname text NOT NULL,
    email text NOT NULL,
    rating integer DEFAULT 0,
    avatar_url text DEFAULT 'https://cdn3.iconfinder.com/data/icons/pictofoundry-pro-vector-set/512/Avatar-512.png'::text,
    reg_date timestamp without time zone,
    rank_id integer DEFAULT 1 NOT NULL
);


--
-- TOC entry 185 (class 1259 OID 16505)
-- Name: user_details_user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_details_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2149 (class 0 OID 0)
-- Dependencies: 185
-- Name: user_details_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE user_details_user_id_seq OWNED BY user_details.user_id;


--
-- TOC entry 2017 (class 2604 OID 16523)
-- Name: rank id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY rank ALTER COLUMN id SET DEFAULT nextval('rank_id_seq'::regclass);


--
-- TOC entry 2013 (class 2604 OID 16510)
-- Name: user_details user_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_details ALTER COLUMN user_id SET DEFAULT nextval('user_details_user_id_seq'::regclass);


--
-- TOC entry 2022 (class 2606 OID 16528)
-- Name: rank rank_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY rank
    ADD CONSTRAINT rank_pkey PRIMARY KEY (id);


--
-- TOC entry 2020 (class 2606 OID 16517)
-- Name: user_details user_details_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_details
    ADD CONSTRAINT user_details_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2018 (class 1259 OID 16542)
-- Name: fki_rank_foreign_key; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_rank_foreign_key ON public.user_details USING btree (rank_id);


--
-- TOC entry 2023 (class 2606 OID 16537)
-- Name: user_details rank_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_details
    ADD CONSTRAINT rank_foreign_key FOREIGN KEY (rank_id) REFERENCES rank(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2018-06-04 01:25:34 MSK

--
-- PostgreSQL database dump complete
--

