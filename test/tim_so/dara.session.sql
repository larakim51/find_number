-- Tạo cơ sở dữ liệu (nếu chưa tồn tại)
USE game_db;
DROP TABLE users;

CREATE DATABASE IF NOT EXISTS game_db;

-- Sử dụng cơ sở dữ liệu
USE game_db;

-- Tạo bảng users
CREATE TABLE users (
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
    session_id INT,
    user_id INT,
    score INT DEFAULT 0,
    PRIMARY KEY (session_id, user_id),
    FOREIGN KEY (session_id) REFERENCES game_sessions(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
