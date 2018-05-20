--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.7

-- Started on 2018-05-20 14:51:35 MSK

\connect comments_db

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 3043 (class 0 OID 16447)
-- Dependencies: 186
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: NewsSiteMaster
--

INSERT INTO comment (id, parent_id, author_name, article_id, add_date, content) VALUES (1, NULL, 'Алексей Лицов', 1, '2018-05-18 06:23:55.253+00', 'Мой первый комментарий!');
INSERT INTO comment (id, parent_id, author_name, article_id, add_date, content) VALUES (2, NULL, 'Александр Смирнов', 1, '2018-05-18 06:26:26.003+00', 'Ещё один комментарий');
INSERT INTO comment (id, parent_id, author_name, article_id, add_date, content) VALUES (3, NULL, 'Алексей Лицов', 2, '2018-05-18 06:29:19.335+00', 'Комментарий на другую новость');
INSERT INTO comment (id, parent_id, author_name, article_id, add_date, content) VALUES (4, 2, 'Алексей Лицов', 1, '2018-05-18 06:31:16.97+00', 'Побочный комментарий');


--
-- TOC entry 3048 (class 0 OID 0)
-- Dependencies: 185
-- Name: comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: NewsSiteMaster
--

SELECT pg_catalog.setval('comment_id_seq', 4, true);


-- Completed on 2018-05-20 14:51:42 MSK

--
-- PostgreSQL database dump complete
--
