-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 26, 2026 at 12:48 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `recording_studio_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `albums`
--

CREATE TABLE `albums` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `year` int(11) NOT NULL,
  `artist_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `albums`
--

INSERT INTO `albums` (`id`, `name`, `year`, `artist_id`) VALUES
(1, 'Limasawa Street', 2019, 1),
(2, 'CLAPCLAPCLAP!', 2019, 2),
(3, 'Kapiling', 2021, 3),
(4, 'Kung Alam mo lang', 2023, 4),
(5, 'Episode', 2021, 5),
(10, 'Coco', 1990, 1),
(11, 'Coco', 1990, 1),
(12, 'Coco', 1990, 1),
(14, 'Coco (Remastered)', 1995, 1),
(15, 'Coco', 1990, 1),
(16, 'Coco', 1990, 1),
(17, 'Coco', 1990, 1),
(18, 'Coco', 1990, 1),
(19, 'Coco', 1990, 1),
(20, 'Pebble House', 2021, 1);

-- --------------------------------------------------------

--
-- Table structure for table `artists`
--

CREATE TABLE `artists` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT 'Name of the artist or band'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `artists`
--

INSERT INTO `artists` (`id`, `name`) VALUES
(1, 'Ben&Ben'),
(2, 'IV of SPADES'),
(3, 'I Belong to the Zoo'),
(4, 'Hev Abi'),
(5, 'Zack Tabudlo'),
(7, 'Paolo Sandejas'),
(9, 'Paolo Sandejas'),
(10, 'Paolo Sandejas'),
(12, 'Paolo Sandejas'),
(13, 'Paolo Sandejas'),
(14, 'Paolo Sandejas'),
(15, 'Paolo Sandejas'),
(16, 'Paolo Sandejas'),
(17, 'Cup of Joe');

-- --------------------------------------------------------

--
-- Table structure for table `playlists`
--

CREATE TABLE `playlists` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `playlists`
--

INSERT INTO `playlists` (`id`, `name`, `date_created`, `user_id`) VALUES
(1, 'Kilig', '2026-11-18 03:57:47', 1),
(2, 'Lumbay', '2026-11-18 03:57:47', 3),
(3, 'Paumanhin', '2026-11-18 03:57:47', 2);

-- --------------------------------------------------------

--
-- Table structure for table `playlist_songs`
--

CREATE TABLE `playlist_songs` (
  `playlist_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `playlist_songs`
--

INSERT INTO `playlist_songs` (`playlist_id`, `song_id`) VALUES
(1, 3),
(1, 4),
(1, 5),
(2, 1),
(2, 7),
(2, 8),
(3, 6),
(3, 9),
(3, 10),
(3, 11);

-- --------------------------------------------------------

--
-- Table structure for table `songs`
--

CREATE TABLE `songs` (
  `id` int(11) NOT NULL,
  `title` varchar(150) NOT NULL COMMENT 'Title of the song',
  `genre` varchar(50) NOT NULL COMMENT 'Musical genre (e.g., Folk Pop, Pinoy Rock)',
  `length` varchar(10) NOT NULL COMMENT 'Stored as text (e.g., 3:45)',
  `album_id` int(11) NOT NULL COMMENT 'Foreign Key referencing albums(id)',
  `is_archived` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0 = Active (FALSE), 1 = Archived (TRUE)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `songs`
--

INSERT INTO `songs` (`id`, `title`, `genre`, `length`, `album_id`, `is_archived`) VALUES
(1, 'Kathang Isip', 'Folk Pop', '4:36', 1, 0),
(2, 'Araw-Araw', 'Folk Pop', '4:12', 1, 0),
(3, 'Bata! Dahan-dahan', 'Pop', '4:07', 2, 0),
(4, 'Come Inside of My Heart', 'Indie Rock', '4:20', 2, 0),
(5, 'Balang Araw', 'Pinoy Pop', '4:48', 3, 0),
(6, 'Sana', 'Indie Rock', '4:32', 3, 0),
(7, 'Alam mo ba Girl', 'OPM', '3:43', 4, 0),
(8, 'Walang Alam', 'Pinoy R&B', '3:05', 4, 0),
(9, 'Habang Buhay', 'OPM', '4:04', 5, 0),
(10, 'Give me your Forever', 'P-Pop', '4:04', 5, 0),
(11, 'Sorry', 'OPM', '4:48', 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'USER',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `is_archived` tinyint(1) NOT NULL DEFAULT 0
) ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `role`, `created_at`, `is_archived`) VALUES
(1, 'admin', '$2a$10$fkCRrwDn.H/HhFu5FcxtWuusYTsSSu5/LxeymCXChIv9Ow3qThkUG', 'admin@gmail.com', 'ADMIN', '2026-11-26 11:45:50', 0),
(2, 'user', 'user123', 'user@gmail.com', 'USER', '2026-11-18 04:00:42', 0),
(3, 'xie', 'aslan0902', 'xie@gmail.com', 'USER', '2026-11-18 03:55:25', 0),
(4, 'yuno', '$2a$10$vNQW0Z5QrOdktf7Tf86sXOj1hHHMFsNqlSNhLAzzRftNqFSLvMcVK', 'yuno@gmail.com', 'USER', '2026-11-21 11:29:31', 0),
(13, 'ah', '$2a$10$4/Nr1UJeztpAZ.CcBNZKO.b9Fogy3B9NitGgtHj7wYuGwJOGuOsba', 'ahhh@gmail.com', 'USER', '2026-11-24 23:46:05', 0),
(14, 'huss', '$2a$10$3Kz6EpA4OMzA9HCjbIx7EOllOYmYgizKXCAywymPYmsbbtEXt3Uqy', 'shuyhkj@gmail.com', 'USER', '2026-11-26 11:20:33', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `albums`
--
ALTER TABLE `albums`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_albums_artist` (`artist_id`);

--
-- Indexes for table `artists`
--
ALTER TABLE `artists`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `playlists`
--
ALTER TABLE `playlists`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_playlists_user` (`user_id`);

--
-- Indexes for table `playlist_songs`
--
ALTER TABLE `playlist_songs`
  ADD PRIMARY KEY (`playlist_id`,`song_id`),
  ADD KEY `fk_playlist_songs_song` (`song_id`);

--
-- Indexes for table `songs`
--
ALTER TABLE `songs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_songs_album` (`album_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `albums`
--
ALTER TABLE `albums`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `artists`
--
ALTER TABLE `artists`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `playlists`
--
ALTER TABLE `playlists`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `songs`
--
ALTER TABLE `songs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `albums`
--
ALTER TABLE `albums`
  ADD CONSTRAINT `fk_albums_artist` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `playlists`
--
ALTER TABLE `playlists`
  ADD CONSTRAINT `fk_playlists_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `playlist_songs`
--
ALTER TABLE `playlist_songs`
  ADD CONSTRAINT `fk_playlist_songs_playlist` FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_playlist_songs_song` FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `songs`
--
ALTER TABLE `songs`
  ADD CONSTRAINT `fk_songs_album` FOREIGN KEY (`album_id`) REFERENCES `albums` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
