--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.9
-- Dumped by pg_dump version 9.6.7

-- Started on 2018-06-19 23:41:40 MSK

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
-- TOC entry 2223 (class 0 OID 16493)
-- Dependencies: 185
-- Data for Name: operation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO operation VALUES (1, 'OP_ADD_ARTICLE');
INSERT INTO operation VALUES (2, 'OP_UPDATE_ARTICLE');
INSERT INTO operation VALUES (3, 'OP_DELETE_ARTICLE');
INSERT INTO operation VALUES (4, 'OP_ADD_COMMENT');
INSERT INTO operation VALUES (5, 'OP_UPDATE_COMMENT');
INSERT INTO operation VALUES (6, 'OP_HIDE_COMMENT');
INSERT INTO operation VALUES (7, 'OP_DELETE_COMMENT');
INSERT INTO operation VALUES (8, 'OP_VOTE_ARTICLE');
INSERT INTO operation VALUES (9, 'OP_VOTE_COMMENT');
INSERT INTO operation VALUES (10, 'OP_VOTE_PROFILE');
INSERT INTO operation VALUES (11, 'OP_MUTE_USER');
INSERT INTO operation VALUES (12, 'OP_BAN_USER');
INSERT INTO operation VALUES (13, 'OP_CREATE_USER');
INSERT INTO operation VALUES (14, 'OP_UPDATE_USER');
INSERT INTO operation VALUES (15, 'OP_DELETE_USER');
INSERT INTO operation VALUES (16, 'OP_UPDATE_PROFILE');
INSERT INTO operation VALUES (17, 'OP_UPDATE_PASSWORD');
INSERT INTO operation VALUES (18, 'OP_UPDATE_EMAIL');
INSERT INTO operation VALUES (19, 'OP_ADD_CATEGORY');
INSERT INTO operation VALUES (20, 'OP_UPDATE_CATEGORY');
INSERT INTO operation VALUES (21, 'OP_DELETE_CATEGORY');
INSERT INTO operation VALUES (22, 'OP_ADD_TAG');
INSERT INTO operation VALUES (23, 'OP_UPDATE_TAG');
INSERT INTO operation VALUES (24, 'OP_DELETE_TAG');


--
-- TOC entry 2244 (class 0 OID 0)
-- Dependencies: 186
-- Name: operation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('operation_id_seq', 18, true);


--
-- TOC entry 2232 (class 0 OID 16564)
-- Dependencies: 194
-- Data for Name: rank; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO rank VALUES (1, 'Плохой', 'FF0000', -10);
INSERT INTO rank VALUES (2, 'Новичок', '6FCF0D', 0);
INSERT INTO rank VALUES (3, 'Бывалый', '00B6C9', 10);


--
-- TOC entry 2225 (class 0 OID 16501)
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
-- TOC entry 2228 (class 0 OID 16512)
-- Dependencies: 190
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "user" VALUES (1, 'banned', '$2a$10$zuRSX8dR4CDVN/luDXHRvus.Bbg4IEAoI01zfODxUKtDoEIGymO56', 1, 'banned@news.com', false);
INSERT INTO "user" VALUES (2, 'muted', '$2a$12$X22QlEKuAoGBZk9KtTg.o.N1huw2YbG3K9XJBXWhHJwBAc.dp/QVG', 2, 'muted@news.com', false);
INSERT INTO "user" VALUES (4, 'editor', '$2a$10$6QByNiAIXRheHYLiGrxmNukqdc1yVJ8MIEJKBzUpTmVQZobnL34w.', 4, 'editor@news.com', false);
INSERT INTO "user" VALUES (5, 'moderator', '$2a$10$q3TszCp5tvnBPGhUTkvvBubu9g/FLnBwgxjxCLoVF1G3kn6loLLVK', 5, 'moderator@news.com', false);
INSERT INTO "user" VALUES (6, 'admin', '$2a$10$wk6L8scdfWH8cjqMMHwoPe33fm/08Z8753Q5Dl.PxZctjdlWyCCDa', 6, 'admin@news.com', false);
INSERT INTO "user" VALUES (7, 'new_user', '$2a$10$8viTRM28E06cBwKPGGKHr.rP.qUvzpLLeIW3FGyQmb9Fie9ZLuXH6', 3, 'new_user@gmail.com', false);
INSERT INTO "user" VALUES (3, 'user', '$2a$10$jN8weyj1/tqxNn0ZfSq1auyJqeaXrdLf/gc9yQxaVu5.r8g6LTCiu', 3, 'user@news.com', false);


--
-- TOC entry 2234 (class 0 OID 16572)
-- Dependencies: 196
-- Data for Name: profile; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO profile VALUES (5, 'Семён', 'Модераторов', 'https://www.worldskills.org/components/angular-worldskills-utils/images/user.png', 'Этот пользователь предпочёл пока не указывать информации о себе', '2018-06-07 19:11:05.230551', '2018-06-07 19:11:05.230551', 3, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO profile VALUES (2, 'Михаил', 'Немногословный', 'https://www.worldskills.org/components/angular-worldskills-utils/images/user.png', 'Я вёл себя плохо и поэтому я теперь muted. Теперь я могу только читать новости и комментарии, а ведь я так хотел ещё писать комментарии, ставить лайки и дизлайки!', '2018-06-07 19:08:19.762367', '2018-06-07 19:08:19.762367', 1, NULL, NULL, NULL, NULL, 3, 1);
INSERT INTO profile VALUES (1, 'Василий', 'Блокированный', 'https://www.worldskills.org/components/angular-worldskills-utils/images/user.png', 'Я вёл себя плохо и поэтому меня заблокировали. Теперь я даже не могу зайти в аккаунт', '2018-06-07 19:08:19.762367', '2018-06-07 19:08:19.762367', 1, NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO profile VALUES (6, 'Арсений', 'Кузнецов', 'http://www-images.theonering.org/torwp/wp-content/uploads/2013/05/samwise-gamgee-300x184.jpg', 'Этот пользователь предпочёл пока не указывать информации о себе', '2018-06-07 19:10:38.782046', '2018-06-07 19:10:38.782046', 3, 'Россия', 'Владивосток', '1993-02-10 00:00:00', 'Мужской', 4, 2);
INSERT INTO profile VALUES (3, 'Игорь', 'Васильев', 'https://busites_www.s3.amazonaws.com/acdccom/content/articles/acdc-back-in-black-album-cover-650.jpg', 'Профессионально занимаюсь стенографией. Участник сборной России по машинописи (клуб Интерстено). Увлекаюсь роком и просто музыкой, а также киберспортом.', '2018-06-07 19:09:11.772853', '2018-06-07 19:09:11.772853', 2, 'Россия', 'Екатеринбург', '2018-06-13 00:00:00', 'Мужской', 1.5, 2);
INSERT INTO profile VALUES (4, 'Владимир', 'Смирнов', 'https://wallpaperbrowse.com/media/images/cat-1285634_960_720.png', 'Этот пользователь предпочёл пока не указывать информации о себе', '2018-06-07 19:09:37.071194', '2018-06-07 19:09:37.071194', 3, 'Россия', 'Нижний Новгород', '1992-06-18 00:00:00', 'Мужской', 5, 1);
INSERT INTO profile VALUES (7, 'Ярослав', 'Кузнецов', 'https://www.worldskills.org/components/angular-worldskills-utils/images/user.png', 'Комментатор, аналитик, киберспортивный журналист.', '2018-06-19 13:12:31.654', '2018-06-19 13:12:31.654', 2, 'Россия', 'Москва', '1987-06-11 00:00:00', 'Мужской', 0, 0);


--
-- TOC entry 2245 (class 0 OID 0)
-- Dependencies: 197
-- Name: profile_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('profile_user_id_seq', 1, false);


--
-- TOC entry 2237 (class 0 OID 16608)
-- Dependencies: 199
-- Data for Name: punishment; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2246 (class 0 OID 0)
-- Dependencies: 198
-- Name: punishment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('punishment_id_seq', 8, true);


--
-- TOC entry 2247 (class 0 OID 0)
-- Dependencies: 195
-- Name: rank_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rank_id_seq', 1, false);


--
-- TOC entry 2248 (class 0 OID 0)
-- Dependencies: 188
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('role_id_seq', 5, true);


--
-- TOC entry 2227 (class 0 OID 16509)
-- Dependencies: 189
-- Data for Name: role_operation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO role_operation VALUES (3, 4);
INSERT INTO role_operation VALUES (3, 8);
INSERT INTO role_operation VALUES (3, 9);
INSERT INTO role_operation VALUES (3, 10);
INSERT INTO role_operation VALUES (4, 1);
INSERT INTO role_operation VALUES (4, 2);
INSERT INTO role_operation VALUES (4, 3);
INSERT INTO role_operation VALUES (4, 4);
INSERT INTO role_operation VALUES (4, 8);
INSERT INTO role_operation VALUES (4, 9);
INSERT INTO role_operation VALUES (4, 10);
INSERT INTO role_operation VALUES (5, 4);
INSERT INTO role_operation VALUES (5, 5);
INSERT INTO role_operation VALUES (5, 6);
INSERT INTO role_operation VALUES (5, 7);
INSERT INTO role_operation VALUES (5, 8);
INSERT INTO role_operation VALUES (5, 9);
INSERT INTO role_operation VALUES (5, 10);
INSERT INTO role_operation VALUES (5, 11);
INSERT INTO role_operation VALUES (5, 16);
INSERT INTO role_operation VALUES (5, 18);
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
INSERT INTO role_operation VALUES (6, 19);
INSERT INTO role_operation VALUES (6, 20);
INSERT INTO role_operation VALUES (6, 21);
INSERT INTO role_operation VALUES (6, 22);
INSERT INTO role_operation VALUES (6, 23);
INSERT INTO role_operation VALUES (6, 24);


--
-- TOC entry 2249 (class 0 OID 0)
-- Dependencies: 191
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_id_seq', 7, true);


--
-- TOC entry 2230 (class 0 OID 16548)
-- Dependencies: 192
-- Data for Name: verification_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO verification_token VALUES (1, 7, '5be188c6-35ac-4b25-ba70-4ecec747686d', '2018-06-20 13:12:31.659');


--
-- TOC entry 2250 (class 0 OID 0)
-- Dependencies: 193
-- Name: verification_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('verification_token_id_seq', 1, true);


--
-- TOC entry 2239 (class 0 OID 16626)
-- Dependencies: 201
-- Data for Name: vote; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO vote VALUES (1, 2, 6, 3);
INSERT INTO vote VALUES (2, 6, 6, 3);
INSERT INTO vote VALUES (3, 1, 6, 1);
INSERT INTO vote VALUES (4, 4, 6, 5);
INSERT INTO vote VALUES (5, 3, 3, 2);
INSERT INTO vote VALUES (6, 3, 6, 1);
INSERT INTO vote VALUES (7, 6, 3, 5);


--
-- TOC entry 2251 (class 0 OID 0)
-- Dependencies: 200
-- Name: vote_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('vote_id_seq', 7, true);


-- Completed on 2018-06-19 23:41:41 MSK

--
-- PostgreSQL database dump complete
--

