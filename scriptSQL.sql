-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ott 05, 2020 alle 18:59
-- Versione del server: 10.4.14-MariaDB
-- Versione PHP: 7.2.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cv19db`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `amministratori`
--

CREATE TABLE `amministratori` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `amministratori`
--

INSERT INTO `amministratori` (`id`, `username`, `password`, `email`) VALUES
(1, 'antov', 'antov', 'antov@live.it');

-- --------------------------------------------------------

--
-- Struttura della tabella `recensioni`
--

CREATE TABLE `recensioni` (
  `id` int(11) NOT NULL,
  `titolo` varchar(40) NOT NULL,
  `nomeStruttura` varchar(40) NOT NULL,
  `testo` varchar(255) NOT NULL,
  `valutazione` int(11) NOT NULL,
  `autore` varchar(30) NOT NULL,
  `data` varchar(30) NOT NULL,
  `visibile` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `recensioni`
--

INSERT INTO `recensioni` (`id`, `titolo`, `nomeStruttura`, `testo`, `valutazione`, `autore`, `data`, `visibile`) VALUES
(1, 'OTTIMO', 'hotel lucia', 'Se questo è un albergo di Primo Levi', 1, 'nome', '2020-09-22', 1),
(2, 'Fantastico', 'hotel lucia', 'Qui le rose sono rosse', 3, 'Mario', '2020-09-22', 1),
(3, 'ottimo!!!', 'hotel rita', 'davvero piacevole come sempre', 4, 'giorgio', '12/05/1993', 1),
(4, 'bellissimo!', 'hotel lucia', ' stato bellissimo!!!!!', 5, 'asd', '14/56/2020', 1),
(5, 'rrrrrrr', 'hotel rita', 'aaaaaaa', 1, 'null', '14/57/2020', 1),
(6, 'Orrendo!', 'hotel lucia', 'non ci torno mai piu', 1, 'asd', '14/08/2020', 0),
(7, 'o cess!!!', 'hotel rita', 'il bano era bianco', 2, 'asd', '14/13/2020', 1),
(9, 'fantastico', 'hotel anguria', 'bellissimo super iper bla bla bla', 4, 'asd', '17/06/2020', 1),
(10, 'bruttissimo', 'hotel anguria', 'non ci torno mai piu ', 2, 'francesco rossi', '17/07/2020', 1),
(11, 'Entusiasmante!', 'hotel india', 'Era molto bello sembrava di stare in india.', 4, 'asd', '18/02/2020', 1),
(12, 'TOP', 'hotel india', 'mi sono divertito un sacco', 5, 'asd', '02/10/2020', 0),
(13, 'DEludente', 'hotel anguria', 'Faceva troppo caldo', 3, 'asd', '02/11/2020', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `strutture`
--

CREATE TABLE `strutture` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `citta` varchar(255) NOT NULL,
  `tariffa` float NOT NULL,
  `descrizione` varchar(250) NOT NULL,
  `indirizzo` varchar(255) NOT NULL,
  `numeroTelefonico` varchar(255) NOT NULL,
  `latitudine` double NOT NULL,
  `longitudine` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `strutture`
--

INSERT INTO `strutture` (`id`, `nome`, `citta`, `tariffa`, `descrizione`, `indirizzo`, `numeroTelefonico`, `latitudine`, `longitudine`) VALUES
(1, 'hotel lucia', 'napoli', 35, 'Il nostro motto è fare sentire l\'ospite a casa sua.\r\nIl nostro Bed & Breakfast a Napoli , si trova su un attico panoramico al 10 piano in una zona molto attrattiva e strategica.\r\nSiamo a pochi minuti dal Lungomare Caracciolo e dalla Spiaggia.\r\n\r\n', 'via da qui 11', '33333333333', 40.830406, 14.195582),
(2, 'hotel rita', 'napoli', 55, 'Il B&B è dotato di tutti i confort\r\nWIFI gatuito nelle camere e nelle aree comuni.\r\nSilenzioso\r\nTutte le camere con bagno interno\r\nAsciugacapelli\r\n', 'via dai binari 131', '222222222222', 40.830669, 14.194206),
(3, 'hotel anguria', 'napoli', 45, 'ampia piscina ', 'via dei pastai 34', '55555555555', 40.830649, 40.830769),
(5, 'hotel india', 'napoli', 450, 'molto esotico', 'via di abdulla 34', '66666666666', 40.705112, 14.515523),
(6, 'Hotel Europa', 'salerno', 67, 'Ampio terrazzo, vista sul mare, piscina coperta.', 'via lepanto 8', '3453798610', 40.62731, 14.480951);

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `anonimo` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`id`, `nome`, `cognome`, `user`, `email`, `pass`, `anonimo`) VALUES
(1, 'antonio', 'vanacore', 'asd', 'asd', 'asd', 1),
(5, 'francesco', 'rossi', 'fra', 'fra', 'fra', 0),
(8, 'anto', 'nella', 'antot', 'b', 'antot', 1),
(9, 'a', 'a', 'a', 'a', 'a', 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `amministratori`
--
ALTER TABLE `amministratori`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `recensioni`
--
ALTER TABLE `recensioni`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `strutture`
--
ALTER TABLE `strutture`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user` (`user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `amministratori`
--
ALTER TABLE `amministratori`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `recensioni`
--
ALTER TABLE `recensioni`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT per la tabella `strutture`
--
ALTER TABLE `strutture`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `utenti`
--
ALTER TABLE `utenti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
