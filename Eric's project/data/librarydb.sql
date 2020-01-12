-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2020 at 03:28 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `librarydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `booklist`
--

CREATE TABLE `booklist` (
  `ID` varchar(10) NOT NULL,
  `Title` varchar(50) NOT NULL,
  `Author` varchar(50) NOT NULL,
  `Publisher` varchar(50) NOT NULL,
  `Type` varchar(20) NOT NULL,
  `Category` varchar(50) DEFAULT NULL,
  `IsAvailable` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `booklist`
--

INSERT INTO `booklist` (`ID`, `Title`, `Author`, `Publisher`, `Type`, `Category`, `IsAvailable`) VALUES
('B001', 'Meditations', 'Marcus Aurelius', 'Modern Library', '', NULL, 0),
('B002', 'Histories of Nations', 'Peter Furtado', 'James & Hudson', '', NULL, 0),
('B003', 'Great Cat Tales', 'Lesley O\'Mara', 'Carroll & Graff', '', NULL, 1),
('B004', 'Mein Kampo', 'Adolf Hitler', 'JAICO', '', NULL, 1),
('B005', 'Contagious', 'Jonah Berger', 'Simon & Schuster', 'Non Fiction', 'Self-help Book', 1),
('B006', 'Introduction to Philosophy', 'Christine Ramos', 'Rex Publishing', 'Non Fiction', 'Textbook', 1),
('B007', 'Jose Rizal', 'Gregorio Zaide', 'All Nations Publishing', 'Non Fiction', 'Biography/Autobiography', 1);

-- --------------------------------------------------------

--
-- Table structure for table `issuedbooklist`
--

CREATE TABLE `issuedbooklist` (
  `BookID` varchar(10) NOT NULL,
  `MemberID` varchar(10) NOT NULL,
  `DateIssued` datetime NOT NULL,
  `ReturnDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `issuedbooklist`
--

INSERT INTO `issuedbooklist` (`BookID`, `MemberID`, `DateIssued`, `ReturnDate`) VALUES
('B001', 'M001', '2020-01-09 10:14:25', '2020-01-03 00:00:00'),
('B002', 'M001', '2020-01-09 10:27:55', '2020-01-17 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `memberlist`
--

CREATE TABLE `memberlist` (
  `ID` varchar(10) NOT NULL,
  `Name` char(100) NOT NULL,
  `Email` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `memberlist`
--

INSERT INTO `memberlist` (`ID`, `Name`, `Email`) VALUES
('M001', 'Eric Dela Cruz', 'eric@mail.com'),
('M002', 'Sly Ponio', 'sly@mail.com');

-- --------------------------------------------------------

--
-- Table structure for table `receivedbooklist`
--

CREATE TABLE `receivedbooklist` (
  `BookID` varchar(10) NOT NULL,
  `MemberID` varchar(10) NOT NULL,
  `DateReturned` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `receivedbooklist`
--

INSERT INTO `receivedbooklist` (`BookID`, `MemberID`, `DateReturned`) VALUES
('B001', 'M001', '2020-01-09 09:46:38');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booklist`
--
ALTER TABLE `booklist`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `memberlist`
--
ALTER TABLE `memberlist`
  ADD PRIMARY KEY (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
