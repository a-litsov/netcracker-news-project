--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.9
-- Dumped by pg_dump version 9.6.7

-- Started on 2018-05-26 21:07:50 MSK

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
-- TOC entry 2127 (class 0 OID 16434)
-- Dependencies: 185
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO comment VALUES (1, NULL, 'Алексей Лицов', 1, '2018-05-18 06:23:55.253+00', 'Мой первый комментарий!');
INSERT INTO comment VALUES (2, NULL, 'Александр Смирнов', 1, '2018-05-18 06:26:26.003+00', 'Ещё один комментарий');
INSERT INTO comment VALUES (3, NULL, 'Алексей Лицов', 2, '2018-05-18 06:29:19.335+00', 'Комментарий на другую новость');
INSERT INTO comment VALUES (4, 2, 'Алексей Лицов', 1, '2018-05-18 06:31:16.97+00', 'Побочный комментарий');
INSERT INTO comment VALUES (5, NULL, 'Неизвестный автор', 1, '2018-05-26 15:10:23.815+00', 'Я думаю, что это лёгкий турнир для ВП. Ликвиды же наоборот, по-видимому, не в форме и, скорее всего, очень быстро покинут турнир.');
INSERT INTO comment VALUES (6, NULL, 'Неизвестный автор', 1, '2018-05-26 15:13:27.75+00', 'Не знаю, как по мне Pain Gaming очень сильны!');
INSERT INTO comment VALUES (10, 2, 'Неизвестный автор', 1, '2018-05-26 17:30:50.428+00', 'Александр Смирнов, это ещё один побочный комментарий первого уровня.');
INSERT INTO comment VALUES (11, 10, 'Неизвестный автор', 1, '2018-05-26 17:31:21.828+00', 'Неизвестный автор, а этот второго уровня');


--
-- TOC entry 2133 (class 0 OID 0)
-- Dependencies: 186
-- Name: comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comment_id_seq', 11, true);


-- Completed on 2018-05-26 21:07:50 MSK

--
-- PostgreSQL database dump complete
--

