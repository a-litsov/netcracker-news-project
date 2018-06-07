--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.7

-- Started on 2018-06-01 12:55:14 MSK

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS auth_db;
--
-- TOC entry 3074 (class 1262 OID 16464)
-- Name: auth_db; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE auth_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';


\connect auth_db

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 13308)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3076 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 16478)
-- Name: operation; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE operation (
    id integer NOT NULL,
    authority text
);


--
-- TOC entry 187 (class 1259 OID 16476)
-- Name: operation_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE operation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3077 (class 0 OID 0)
-- Dependencies: 187
-- Name: operation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE operation_id_seq OWNED BY operation.id;


--
-- TOC entry 186 (class 1259 OID 16467)
-- Name: role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE role (
    id integer NOT NULL,
    authority text
);


--
-- TOC entry 185 (class 1259 OID 16465)
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3078 (class 0 OID 0)
-- Dependencies: 185
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE role_id_seq OWNED BY role.id;


--
-- TOC entry 189 (class 1259 OID 16487)
-- Name: role_operation; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE role_operation (
    role_id integer NOT NULL,
    operation_id integer NOT NULL
);


--
-- TOC entry 191 (class 1259 OID 16504)
-- Name: user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "user" (
    id integer NOT NULL,
    username text,
    password text,
    role_id integer DEFAULT 2,
    email text NOT NULL,
    enabled boolean DEFAULT true,
    email_verified boolean DEFAULT false
);


--
-- TOC entry 190 (class 1259 OID 16502)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3079 (class 0 OID 0)
-- Dependencies: 190
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


--
-- TOC entry 2940 (class 2604 OID 16481)
-- Name: operation id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY operation ALTER COLUMN id SET DEFAULT nextval('operation_id_seq'::regclass);


--
-- TOC entry 2939 (class 2604 OID 16470)
-- Name: role id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY role ALTER COLUMN id SET DEFAULT nextval('role_id_seq'::regclass);


--
-- TOC entry 2941 (class 2604 OID 16507)
-- Name: user id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- TOC entry 2945 (class 2606 OID 16486)
-- Name: operation operation_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY operation
    ADD CONSTRAINT operation_pkey PRIMARY KEY (id);


--
-- TOC entry 2947 (class 2606 OID 16491)
-- Name: role_operation role_operation_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY role_operation
    ADD CONSTRAINT role_operation_pkey PRIMARY KEY (role_id, operation_id);


--
-- TOC entry 2943 (class 2606 OID 16475)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 2949 (class 2606 OID 16512)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2951 (class 2606 OID 16497)
-- Name: role_operation role_operation_operation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY role_operation
    ADD CONSTRAINT role_operation_operation_id_fkey FOREIGN KEY (operation_id) REFERENCES operation(id);


--
-- TOC entry 2950 (class 2606 OID 16492)
-- Name: role_operation role_operation_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY role_operation
    ADD CONSTRAINT role_operation_role_id_fkey FOREIGN KEY (role_id) REFERENCES role(id);


--
-- TOC entry 2952 (class 2606 OID 16513)
-- Name: user user_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_role_id_fkey FOREIGN KEY (role_id) REFERENCES role(id);


-- Completed on 2018-06-01 12:55:24 MSK

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


-- Completed on 2018-06-05 00:50:15 MSK

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
    rating integer DEFAULT 0,
    avatar_url text DEFAULT 'https://cdn3.iconfinder.com/data/icons/pictofoundry-pro-vector-set/512/Avatar-512.png'::text,
    about text NOT NULL DEFAULT 'Этот пользователь предпочёл пока не указывать информации о себе',
    last_online timestamp DEFAULT NOW(),
    reg_date timestamp DEFAULT NOW(),
    rank_id integer DEFAULT 2 NOT NULL
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

ALTER TABLE ONLY user_details
    ADD CONSTRAINT user_details_user_id_fkey FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE;


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
    ADD CONSTRAINT rank_foreign_key FOREIGN KEY (rank_id) REFERENCES rank(id) ON UPDATE CASCADE ON DELETE NO ACTION;


-- Completed on 2018-06-04 01:25:34 MSK


--
-- PostgreSQL database dump complete
--

