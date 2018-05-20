--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.7
-- Dumped by pg_dump version 9.6.7

-- Started on 2018-05-20 07:26:31 MSK

\connect articles_db

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
-- TOC entry 2160 (class 0 OID 24649)
-- Dependencies: 188
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO category (id, name) VALUES (1, 'Главное');
INSERT INTO category (id, name) VALUES (4, 'Политика');
INSERT INTO category (id, name) VALUES (5, 'Общество');
INSERT INTO category (id, name) VALUES (6, 'Экономика');
INSERT INTO category (id, name) VALUES (7, 'В мире');
INSERT INTO category (id, name) VALUES (8, 'Происшествия');
INSERT INTO category (id, name) VALUES (9, 'Спорт');
INSERT INTO category (id, name) VALUES (10, 'Наука');
INSERT INTO category (id, name) VALUES (11, 'Культура');
INSERT INTO category (id, name) VALUES (12, 'Религия');
INSERT INTO category (id, name) VALUES (13, 'Туризм');


--
-- TOC entry 2158 (class 0 OID 24638)
-- Dependencies: 186
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tag (id, name) VALUES (1, 'Совещания');
INSERT INTO tag (id, name) VALUES (4, 'Реформы');
INSERT INTO tag (id, name) VALUES (5, 'Здравоохранение');
INSERT INTO tag (id, name) VALUES (6, 'Промышленность');
INSERT INTO tag (id, name) VALUES (7, 'Бизнес');
INSERT INTO tag (id, name) VALUES (8, 'Теракт');
INSERT INTO tag (id, name) VALUES (9, 'Интересное');
INSERT INTO tag (id, name) VALUES (10, 'История');
INSERT INTO tag (id, name) VALUES (11, 'Мнение');
INSERT INTO tag (id, name) VALUES (12, 'Россия');
INSERT INTO tag (id, name) VALUES (13, 'США');
INSERT INTO tag (id, name) VALUES (14, 'Украина');
INSERT INTO tag (id, name) VALUES (15, 'Матч');
INSERT INTO tag (id, name) VALUES (16, 'Интервью');
INSERT INTO tag (id, name) VALUES (17, 'Аналитика');


--
-- TOC entry 2162 (class 0 OID 24660)
-- Dependencies: 190
-- Data for Name: article; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO article (id, category_id, tag_id, title, logo_src, content, author_name, add_date) VALUES (4, 1, 1, 'Моя четвёртая новость', 'Пока без картинки', 'Контент', 'Алексей Лицов', '2018-05-18 03:36:00.127+03');
INSERT INTO article (id, category_id, tag_id, title, logo_src, content, author_name, add_date) VALUES (5, 1, 1, 'Моя четвёртая новость', 'Пока без картинки', 'Контент', 'Алексей Лицов', '2018-05-18 03:36:33.449+03');
INSERT INTO article (id, category_id, tag_id, title, logo_src, content, author_name, add_date) VALUES (1, 4, 6, 'Моя первая новость', 'Пока без картинки', 'Контент', 'Алексей Лицов', '2018-05-18 03:33:02.18+03');


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 189
-- Name: article_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('article_id_seq', 6, true);


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 187
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('category_id_seq', 13, true);


--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 185
-- Name: tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tag_id_seq', 17, true);


-- Completed on 2018-05-20 07:26:31 MSK

--
-- PostgreSQL database dump complete
--
