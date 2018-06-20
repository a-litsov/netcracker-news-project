--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.9
-- Dumped by pg_dump version 9.6.7

-- Started on 2018-06-19 23:43:02 MSK

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
-- TOC entry 2143 (class 0 OID 16454)
-- Dependencies: 185
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO comment VALUES (1, NULL, 2, 1, '2018-05-18 06:23:55.253+00', 'Мой первый комментарий!', 0, 0, false);
INSERT INTO comment VALUES (2, NULL, 3, 1, '2018-05-18 06:26:26.003+00', 'Ещё один комментарий', 0, 0, false);
INSERT INTO comment VALUES (3, NULL, 2, 2, '2018-05-18 06:29:19.335+00', 'Комментарий на другую новость', 0, 0, false);
INSERT INTO comment VALUES (5, NULL, 4, 1, '2018-05-26 15:10:23.815+00', 'Я думаю, что это лёгкий турнир для ВП. Ликвиды же наоборот, по-видимому, не в форме и, скорее всего, очень быстро покинут турнир.', 0, 0, false);
INSERT INTO comment VALUES (6, NULL, 4, 1, '2018-05-26 15:13:27.75+00', 'Не знаю, как по мне Pain Gaming очень сильны!', 0, 0, false);
INSERT INTO comment VALUES (14, NULL, 4, 7, '2018-06-18 23:23:42.286+00', 'Как по мне Fnatic всё же довольно сильная команда и её рано списывать со счетов.', 2, 0, false);
INSERT INTO comment VALUES (16, NULL, 6, 7, '2018-06-18 23:35:15.959+00', 'Возможно, дело действительно было в лучшем драфте со стороны ВП, но я не думаю, что именно он определил исход встречи. ВП банально на голову сильнее оппонента.', 0, 0, false);
INSERT INTO comment VALUES (11, 2, 4, 1, '2018-05-26 17:31:21.828+00', 'Неизвестный автор, а этот второго уровня', 0, 0, false);
INSERT INTO comment VALUES (15, 14, 3, 7, '2018-06-18 23:25:07.997+00', 'editor, а вот я не соглашусь, чего только стоит их недавнее поражение на Дримлиге. Команда явно недотягивает ни по личному скиллу игроков, ни по макро игре.', 1, 1, false);
INSERT INTO comment VALUES (17, 15, 6, 7, '2018-06-18 23:38:53.326+00', 'user, вы просто смотрите на ситуацию с разных сторон. В действительности Virtus.Pro сейчас одна из самых сильных команд в мире и рано делать выводы.', 2, 0, false);

--
-- TOC entry 2151 (class 0 OID 0)
-- Dependencies: 186
-- Name: comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comment_id_seq', 17, true);


--
-- TOC entry 2146 (class 0 OID 16481)
-- Dependencies: 188
-- Data for Name: vote; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO vote VALUES (1, 14, 4, 'LIKE');
INSERT INTO vote VALUES (2, 15, 3, 'DISLIKE');
INSERT INTO vote VALUES (4, 14, 6, 'LIKE');
INSERT INTO vote VALUES (7, 15, 6, 'LIKE');
INSERT INTO vote VALUES (8, 17, 6, 'LIKE');
INSERT INTO vote VALUES (9, 17, 3, 'LIKE');


--
-- TOC entry 2152 (class 0 OID 0)
-- Dependencies: 187
-- Name: vote_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('vote_id_seq', 9, true);


-- Completed on 2018-06-19 23:43:03 MSK

--
-- PostgreSQL database dump complete
--

