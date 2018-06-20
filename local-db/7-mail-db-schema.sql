--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.9
-- Dumped by pg_dump version 9.6.7

-- Started on 2018-06-13 03:54:38 MSK

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS mail_db;
--
-- TOC entry 2137 (class 1262 OID 16581)
-- Name: mail_db; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE mail_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


\connect mail_db

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
-- TOC entry 2139 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 185 (class 1259 OID 16582)
-- Name: user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "user" (
    id integer NOT NULL,
    username text NOT NULL,
    email text NOT NULL,
    sub_active boolean NOT NULL DEFAULT false
);


--
-- TOC entry 187 (class 1259 OID 16602)
-- Name: user_subscription; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_subscription (
    id integer NOT NULL,
    user_id integer NOT NULL,
    category_id integer NOT NULL
);


--
-- TOC entry 186 (class 1259 OID 16600)
-- Name: user_subscription_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_subscription_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2140 (class 0 OID 0)
-- Dependencies: 186
-- Name: user_subscription_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE user_subscription_id_seq OWNED BY user_subscription.id;


--
-- TOC entry 2010 (class 2604 OID 16605)
-- Name: user_subscription id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_subscription ALTER COLUMN id SET DEFAULT nextval('user_subscription_id_seq'::regclass);


--
-- TOC entry 2012 (class 2606 OID 16589)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2014 (class 2606 OID 16607)
-- Name: user_subscription user_subscription_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_subscription
    ADD CONSTRAINT user_subscription_pkey PRIMARY KEY (id);


--
-- TOC entry 2015 (class 2606 OID 16608)
-- Name: user_subscription user_subscription_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_subscription
    ADD CONSTRAINT user_subscription_user_id_fkey FOREIGN KEY (user_id) REFERENCES "user"(id);


CREATE TABLE verification_token (
    id integer NOT NULL,
    user_id integer,
    token text NOT NULL,
    expiry_date timestamp without time zone
);


--
-- TOC entry 192 (class 1259 OID 16538)
-- Name: verification_token_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE verification_token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2150 (class 0 OID 0)
-- Dependencies: 192
-- Name: verification_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE verification_token_id_seq OWNED BY verification_token.id;


--
-- TOC entry 2026 (class 2604 OID 16543)
-- Name: verification_token id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY verification_token ALTER COLUMN id SET DEFAULT nextval('verification_token_id_seq'::regclass);


--
-- TOC entry 2028 (class 2606 OID 16548)
-- Name: verification_token verification_token_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY verification_token
    ADD CONSTRAINT verification_token_pkey PRIMARY KEY (id);

ALTER TABLE ONLY verification_token
  ADD CONSTRAINT verification_token_user_fk FOREIGN KEY (user_id)
      REFERENCES public."user" (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2018-06-13 03:54:38 MSK

--
-- PostgreSQL database dump complete
--

