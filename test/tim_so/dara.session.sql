-- Tạo cơ sở dữ liệu (nếu chưa tồn tại)
DROP TABLE usersAcc;
DROP TABLE game_session_players;
DROP TABLE game_sessions;

USE game_db;

CREATE DATABASE IF NOT EXISTS game_db;
-- Tạo bảng users
CREATE TABLE usersAcc (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    ranking INT DEFAULT 0,
    score INT DEFAULT 0,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    total_games INT DEFAULT 0
);

-- Tạo bảng game_sessions
CREATE TABLE game_sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    num_players INT NOT NULL,
    numbers_to_find INT NOT NULL,
    game_duration INT NOT NULL,
    lucky_number INT,
    lucky_number_bonus INT DEFAULT 0
);

-- Tạo bảng game_session_players
CREATE TABLE game_session_players (
    IdRoom INT NOT NULL,
    id INT NOT NULL,  -- ID của người chơi (từ bảng usersAcc)
    playerColor varchar(255) NOT NULL,
    point int(11) NOT NULL,
    KetQua varchar(255) NOT NULL,
    PRIMARY KEY (IdRoom, id),  -- Khóa chính ghép
    FOREIGN KEY (IdRoom) REFERENCES game_sessions(id), -- Khóa ngoại liên kết đến game_sessions
    FOREIGN KEY (id) REFERENCES usersAcc(id) -- Khóa ngoại liên kết đến usersAcc
);

