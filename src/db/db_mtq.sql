-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 19, 2025 at 07:09 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `u725894752_app_mtq`
--

-- --------------------------------------------------------

--
-- Table structure for table `aspek_penilaian`
--

CREATE TABLE `aspek_penilaian` (
  `id_aspek` int(11) NOT NULL,
  `id_lomba` int(11) NOT NULL,
  `nama_aspek` varchar(50) NOT NULL,
  `presentase` int(11) NOT NULL CHECK (`presentase` >= 0 and `presentase` <= 100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `aspek_penilaian`
--

INSERT INTO `aspek_penilaian` (`id_aspek`, `id_lomba`, `nama_aspek`, `presentase`) VALUES
(1, 2, 'Tajwid', 20),
(2, 2, 'Fashahah', 20),
(3, 2, 'Adab', 20),
(4, 2, 'Suara dan Lagu', 15),
(5, 2, 'Penguasaan Juz', 25),
(6, 1, 'Tajwid', 30),
(7, 1, 'Fashahah', 25),
(8, 1, 'Suara dan Lagu', 30),
(9, 1, 'Adab dan Penampilan', 15),
(13, 6, 'Sistematika dan Penulisan', 25),
(15, 6, 'Kualitas Isi dan Kebaruan Ide', 45),
(17, 6, 'Relevansi dan Argumentasi', 30);

-- --------------------------------------------------------

--
-- Table structure for table `lomba`
--

CREATE TABLE `lomba` (
  `id_lomba` int(11) NOT NULL,
  `nama_lomba` varchar(50) NOT NULL,
  `kuota` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lomba`
--

INSERT INTO `lomba` (`id_lomba`, `nama_lomba`, `kuota`) VALUES
(1, 'MTQ Tartil', 20),
(2, 'MHQ 2 Juz', 25),
(6, 'KTI', 10),
(7, 'MTQ Tilawah', 25),
(8, 'MHQ 5 Juz', 25),
(9, 'MFQ', 15);

-- --------------------------------------------------------

--
-- Table structure for table `nilai`
--

CREATE TABLE `nilai` (
  `id_nilai` int(11) NOT NULL,
  `id_peserta` int(11) NOT NULL,
  `id_juri` int(11) NOT NULL,
  `id_lomba` int(11) NOT NULL,
  `id_aspek` int(11) NOT NULL,
  `skor` int(11) NOT NULL CHECK (`skor` >= 0 and `skor` <= 100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `nilai`
--

INSERT INTO `nilai` (`id_nilai`, `id_peserta`, `id_juri`, `id_lomba`, `id_aspek`, `skor`) VALUES
(1, 1, 2, 1, 6, 90),
(2, 1, 2, 1, 7, 85),
(3, 1, 2, 1, 8, 88),
(4, 1, 2, 1, 9, 80),
(5, 2, 3, 2, 1, 92),
(6, 2, 3, 2, 2, 90),
(7, 2, 3, 2, 3, 88),
(8, 2, 3, 2, 4, 85),
(9, 2, 3, 2, 5, 95),
(10, 1, 4, 1, 6, 89),
(11, 1, 4, 1, 7, 86),
(12, 1, 4, 1, 8, 87),
(13, 1, 4, 1, 9, 80),
(14, 2, 5, 2, 1, 90),
(15, 2, 5, 2, 2, 88),
(16, 2, 5, 2, 3, 89),
(17, 2, 5, 2, 4, 86),
(18, 2, 5, 2, 5, 93),
(87, 99, 74, 1, 6, 67),
(88, 99, 74, 1, 7, 78),
(89, 99, 74, 1, 8, 89),
(90, 99, 74, 1, 9, 90),
(91, 3, 74, 1, 6, 90),
(92, 3, 74, 1, 7, 90),
(93, 3, 74, 1, 8, 90),
(94, 3, 74, 1, 9, 90),
(100, 187, 18, 6, 13, 88);

-- --------------------------------------------------------

--
-- Table structure for table `peserta`
--

CREATE TABLE `peserta` (
  `id_peserta` int(11) NOT NULL,
  `nama_peserta` varchar(100) NOT NULL,
  `asal` varchar(50) NOT NULL,
  `id_lomba` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `peserta`
--

INSERT INTO `peserta` (`id_peserta`, `nama_peserta`, `asal`, `id_lomba`) VALUES
(1, 'Ahmad', 'UPN Veteran Jawa Timur', 1),
(2, 'Muhammad', 'Universitas Airlangga', 2),
(3, 'dewa19', 'Teknik Sipil', 1),
(99, 'Ihiy', 'ManaHayo', 1),
(106, 'Baihaqi', 'Informatika', 1),
(131, 'Syifaul Haqq', 'Teknik Mesin', 2),
(141, 'Eka', 'Teknik Mesin', 2),
(187, 'Fauzan', 'Informatika', 6),
(188, 'Ilyas', 'Teknik Elektro', 6);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `role` enum('panitia','juri') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `username`, `password`, `nama`, `role`) VALUES
(1, 'panitia1', 'belanegara', 'Panitia', 'panitia'),
(2, 'juri1', 'mtqupnvjt', 'Juri1', 'juri'),
(3, 'juri2', 'mhqupnvjt', 'Juri2', 'juri'),
(4, 'juri3', 'mtqupnvjt', 'Juri3', 'juri'),
(5, 'juri4', 'mhqupnvjt', 'Juri4', 'juri'),
(18, 'baihaqi', 'belanegara', 'Baihaqi', 'juri'),
(68, 'baihq', 'upnvjt', 'hq', 'juri'),
(72, 'adijatmo', 'belanegara', 'Adijatmo', 'juri'),
(73, 'lastDebug2', '0000', 'lastDebug2', 'juri'),
(74, 'cimo', 'cimo', 'cimo', 'juri'),
(82, 'adiyatma', 'belanegara', 'Adiyatma Eka', 'juri');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `aspek_penilaian`
--
ALTER TABLE `aspek_penilaian`
  ADD PRIMARY KEY (`id_aspek`),
  ADD KEY `id_lomba` (`id_lomba`);

--
-- Indexes for table `lomba`
--
ALTER TABLE `lomba`
  ADD PRIMARY KEY (`id_lomba`);

--
-- Indexes for table `nilai`
--
ALTER TABLE `nilai`
  ADD PRIMARY KEY (`id_nilai`),
  ADD KEY `id_peserta` (`id_peserta`),
  ADD KEY `id_juri` (`id_juri`),
  ADD KEY `id_lomba` (`id_lomba`),
  ADD KEY `id_aspek` (`id_aspek`);

--
-- Indexes for table `peserta`
--
ALTER TABLE `peserta`
  ADD PRIMARY KEY (`id_peserta`),
  ADD KEY `id_lomba` (`id_lomba`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `aspek_penilaian`
--
ALTER TABLE `aspek_penilaian`
  MODIFY `id_aspek` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `lomba`
--
ALTER TABLE `lomba`
  MODIFY `id_lomba` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `nilai`
--
ALTER TABLE `nilai`
  MODIFY `id_nilai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT for table `peserta`
--
ALTER TABLE `peserta`
  MODIFY `id_peserta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=199;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=87;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `aspek_penilaian`
--
ALTER TABLE `aspek_penilaian`
  ADD CONSTRAINT `aspek_penilaian_ibfk_1` FOREIGN KEY (`id_lomba`) REFERENCES `lomba` (`id_lomba`) ON DELETE CASCADE;

--
-- Constraints for table `nilai`
--
ALTER TABLE `nilai`
  ADD CONSTRAINT `nilai_ibfk_1` FOREIGN KEY (`id_peserta`) REFERENCES `peserta` (`id_peserta`) ON DELETE CASCADE,
  ADD CONSTRAINT `nilai_ibfk_2` FOREIGN KEY (`id_juri`) REFERENCES `users` (`id_user`) ON DELETE CASCADE,
  ADD CONSTRAINT `nilai_ibfk_3` FOREIGN KEY (`id_lomba`) REFERENCES `lomba` (`id_lomba`) ON DELETE CASCADE,
  ADD CONSTRAINT `nilai_ibfk_4` FOREIGN KEY (`id_aspek`) REFERENCES `aspek_penilaian` (`id_aspek`) ON DELETE CASCADE;

--
-- Constraints for table `peserta`
--
ALTER TABLE `peserta`
  ADD CONSTRAINT `peserta_ibfk_1` FOREIGN KEY (`id_lomba`) REFERENCES `lomba` (`id_lomba`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
