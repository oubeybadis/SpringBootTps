-- MySQL Database Setup Script for User Management System
-- Run this script in your MySQL client to create the database and table

CREATE DATABASE IF NOT EXISTS user_management;

USE user_management;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

