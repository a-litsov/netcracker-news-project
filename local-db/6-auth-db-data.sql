--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.9
-- Dumped by pg_dump version 9.6.7

-- Started on 2018-06-03 20:17:46 MSK

\connect auth_db

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
-- TOC entry 2155 (class 0 OID 16451)
-- Dependencies: 185
-- Data for Name: operation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO operation VALUES (1, 'OP_ADD_ARTICLE');
INSERT INTO operation VALUES (2, 'OP_UPDATE_ARTICLE');
INSERT INTO operation VALUES (3, 'OP_DELETE_ARTICLE');
INSERT INTO operation VALUES (4, 'OP_ADD_COMMENT');
INSERT INTO operation VALUES (5, 'OP_UPDATE_COMMENT');
INSERT INTO operation VALUES (6, 'OP_DELETE_COMMENT');
INSERT INTO operation VALUES (7, 'OP_VOTE_ARTICLE');
INSERT INTO operation VALUES (8, 'OP_VOTE_COMMENT');
INSERT INTO operation VALUES (9, 'OP_BAN_USER');
INSERT INTO operation VALUES (10, 'OP_CREATE_USER');
INSERT INTO operation VALUES (11, 'OP_UPDATE_USER');
INSERT INTO operation VALUES (12, 'OP_DELETE_USER');
INSERT INTO operation VALUES (13, 'OP_ADD_CATEGORY');
INSERT INTO operation VALUES (14, 'OP_UPDATE_CATEGORY');
INSERT INTO operation VALUES (15, 'OP_DELETE_CATEGORY');
INSERT INTO operation VALUES (16, 'OP_ADD_TAG');
INSERT INTO operation VALUES (17, 'OP_UPDATE_TAG');
INSERT INTO operation VALUES (18, 'OP_DELETE_TAG');


--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 186
-- Name: operation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('operation_id_seq', 18, true);


--
-- TOC entry 2157 (class 0 OID 16459)
-- Dependencies: 187
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO role VALUES (1, 'ROLE_BANNED');
INSERT INTO role VALUES (2, 'ROLE_MUTED');
INSERT INTO role VALUES (3, 'ROLE_USER');
INSERT INTO role VALUES (4, 'ROLE_EDITOR');
INSERT INTO role VALUES (5, 'ROLE_MODERATOR');
INSERT INTO role VALUES (6, 'ROLE_ADMIN');


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 188
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('role_id_seq', 5, true);


--
-- TOC entry 2159 (class 0 OID 16467)
-- Dependencies: 189
-- Data for Name: role_operation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO role_operation VALUES (3, 4);
INSERT INTO role_operation VALUES (3, 7);
INSERT INTO role_operation VALUES (3, 8);
INSERT INTO role_operation VALUES (4, 1);
INSERT INTO role_operation VALUES (4, 2);
INSERT INTO role_operation VALUES (4, 3);
INSERT INTO role_operation VALUES (4, 4);
INSERT INTO role_operation VALUES (4, 7);
INSERT INTO role_operation VALUES (4, 8);
INSERT INTO role_operation VALUES (5, 4);
INSERT INTO role_operation VALUES (5, 5);
INSERT INTO role_operation VALUES (5, 6);
INSERT INTO role_operation VALUES (5, 7);
INSERT INTO role_operation VALUES (5, 8);
INSERT INTO role_operation VALUES (5, 9);
INSERT INTO role_operation VALUES (6, 1);
INSERT INTO role_operation VALUES (6, 2);
INSERT INTO role_operation VALUES (6, 3);
INSERT INTO role_operation VALUES (6, 4);
INSERT INTO role_operation VALUES (6, 5);
INSERT INTO role_operation VALUES (6, 6);
INSERT INTO role_operation VALUES (6, 7);
INSERT INTO role_operation VALUES (6, 8);
INSERT INTO role_operation VALUES (6, 9);
INSERT INTO role_operation VALUES (6, 10);
INSERT INTO role_operation VALUES (6, 11);
INSERT INTO role_operation VALUES (6, 12);
INSERT INTO role_operation VALUES (6, 13);
INSERT INTO role_operation VALUES (6, 14);
INSERT INTO role_operation VALUES (6, 15);
INSERT INTO role_operation VALUES (6, 16);
INSERT INTO role_operation VALUES (6, 17);
INSERT INTO role_operation VALUES (6, 18);


--
-- TOC entry 2160 (class 0 OID 16470)
-- Dependencies: 190
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "user" VALUES (1, 'banned', '$2a$10$zuRSX8dR4CDVN/luDXHRvus.Bbg4IEAoI01zfODxUKtDoEIGymO56', 1, 'banned@news.com');
INSERT INTO "user" VALUES (2, 'muted', '$2a$12$X22QlEKuAoGBZk9KtTg.o.N1huw2YbG3K9XJBXWhHJwBAc.dp/QVG', 2, 'muted@news.com');
INSERT INTO "user" VALUES (3, 'user', '$2a$10$jN8weyj1/tqxNn0ZfSq1auyJqeaXrdLf/gc9yQxaVu5.r8g6LTCiu', 3, 'user@news.com');
INSERT INTO "user" VALUES (4, 'editor', '$2a$10$6QByNiAIXRheHYLiGrxmNukqdc1yVJ8MIEJKBzUpTmVQZobnL34w.', 4, 'editor@news.com');
INSERT INTO "user" VALUES (5, 'moderator', '$2a$10$q3TszCp5tvnBPGhUTkvvBubu9g/FLnBwgxjxCLoVF1G3kn6loLLVK', 5, 'moderator@news.com');
INSERT INTO "user" VALUES (6, 'admin', '$2a$10$wk6L8scdfWH8cjqMMHwoPe33fm/08Z8753Q5Dl.PxZctjdlWyCCDa', 6, 'admin@news.com');


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 191
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--


SELECT pg_catalog.setval('user_id_seq', 6, true);


INSERT INTO rank VALUES (1, 'Плохой', 'FF0000', -10);
INSERT INTO rank VALUES (2, 'Новичок', '6FCF0D', 0);
INSERT INTO rank VALUES (3, 'Бывалый', '00B6C9', 10);



INSERT INTO profile VALUES (1, 'Василий', 'Блокированный', -20, 'https://cdn3.iconfinder.com/data/icons/pictofoundry-pro-vector-set/512/Avatar-512.png', 'Я вёл себя плохо и поэтому меня заблокировали. Теперь я даже не могу зайти в аккаунт', '2018-06-07 19:08:19.762367', '2018-06-07 19:08:19.762367', 1);
INSERT INTO profile VALUES (2, 'Михаил', 'Немногословный', -20, 'https://cdn3.iconfinder.com/data/icons/pictofoundry-pro-vector-set/512/Avatar-512.png', 'Я вёл себя плохо и поэтому я теперь muted. Теперь я могу только читать новости и комментарии, а ведь я так хотел ещё писать комментарии, ставить лайки и дизлайки!', '2018-06-07 19:08:19.762367', '2018-06-07 19:08:19.762367', 1);
INSERT INTO profile VALUES (3, 'Игорь', 'Пользователев', 4, 'https://cdn3.iconfinder.com/data/icons/pictofoundry-pro-vector-set/512/Avatar-512.png', 'Этот пользователь предпочёл пока не указывать информации о себе', '2018-06-07 19:09:11.772853', '2018-06-07 19:09:11.772853', 2);
INSERT INTO profile VALUES (4, 'Владимир', 'Редакторов', 15, 'https://cdn3.iconfinder.com/data/icons/pictofoundry-pro-vector-set/512/Avatar-512.png', 'Этот пользователь предпочёл пока не указывать информации о себе', '2018-06-07 19:09:37.071194', '2018-06-07 19:09:37.071194', 3);
INSERT INTO profile VALUES (5, 'Альберт', 'Администраторов', 42, 'https://cdn3.iconfinder.com/data/icons/pictofoundry-pro-vector-set/512/Avatar-512.png', 'Этот пользователь предпочёл пока не указывать информации о себе', '2018-06-07 19:11:05.230551', '2018-06-07 19:11:05.230551', 3);
INSERT INTO profile VALUES (6, 'Семён', 'Модераторов', 23, 'https://cdn3.iconfinder.com/data/icons/pictofoundry-pro-vector-set/512/Avatar-512.png', 'Этот пользователь предпочёл пока не указывать информации о себе', '2018-06-07 19:10:38.782046', '2018-06-07 19:10:38.782046', 3);



-- Completed on 2018-06-03 20:17:47 MSK

--
-- PostgreSQL database dump complete
--

