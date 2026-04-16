-- Restaurant Management System Database Schema
-- Run this in Supabase SQL Editor

-- Create Customers Table
CREATE TABLE IF NOT EXISTS customers (
    customer_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    email VARCHAR(255),
    membership_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT NOW()
);

-- Create Reservations Table
CREATE TABLE IF NOT EXISTS reservations (
    reservation_id VARCHAR(50) PRIMARY KEY,
    customer_id VARCHAR(50) NOT NULL,
    table_number VARCHAR(50) NOT NULL,
    reservation_date TIMESTAMP NOT NULL,
    number_of_guests INTEGER NOT NULL,
    status VARCHAR(50) DEFAULT 'Confirmed',
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);
