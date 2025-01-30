-- Create enum type for user roles
CREATE TYPE user_role AS ENUM ('ADMIN', 'STUDENT', 'TEACHER');

-- Create users table
CREATE TABLE users (
                       user_id BIGSERIAL PRIMARY KEY,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       role_name user_role,
                       is_first_password_changed BOOLEAN NOT NULL DEFAULT FALSE,
                       groups VARCHAR(255)
);

-- Create team table
CREATE TABLE team (
                      team_id BIGSERIAL PRIMARY KEY,
                      team_name VARCHAR(50) NOT NULL,
                      description VARCHAR(200),
                      creator_id BIGINT REFERENCES users(user_id)
);

-- Create post table
CREATE TABLE post (
                      post_id BIGSERIAL PRIMARY KEY,
                      post_title VARCHAR(50) NOT NULL,
                      post_content VARCHAR(500) NOT NULL,
                      created_at TIMESTAMP NOT NULL,
                      updated_at TIMESTAMP NOT NULL,
                      user_id BIGINT REFERENCES users(user_id)
);

-- Create event table
CREATE TABLE event (
                       event_id BIGSERIAL PRIMARY KEY,
                       event_name VARCHAR(100) NOT NULL,
                       event_description VARCHAR(200) NOT NULL,
                       start_date DATE NOT NULL,
                       end_date DATE NOT NULL,
                       team_id BIGINT NOT NULL REFERENCES team(team_id)
);

-- Create grade table
CREATE TABLE grade (
                       grade_id BIGSERIAL PRIMARY KEY,
                       subject_name VARCHAR(100) NOT NULL,
                       grade VARCHAR(1) NOT NULL,
                       comment VARCHAR(100),
                       date_of_receipt DATE NOT NULL,
                       creator_id BIGINT NOT NULL,
                       user_id BIGINT NOT NULL REFERENCES users(user_id)
);

-- Create schedule table
CREATE TABLE schedule (
                          schedule_id BIGSERIAL PRIMARY KEY,
                          subject_name VARCHAR(255),
                          start_date TIMESTAMP,
                          end_date TIMESTAMP,
                          groups VARCHAR(255)
);

-- Create address_book_entry table
CREATE TABLE address_book_entry (
                                    address_book_entry_id BIGSERIAL PRIMARY KEY,
                                    user_id BIGINT UNIQUE REFERENCES users(user_id)
);

-- Create join table for team_user many-to-many relationship
CREATE TABLE team_user (
                           team_id BIGINT REFERENCES team(team_id),
                           user_id BIGINT REFERENCES users(user_id),
                           PRIMARY KEY (team_id, user_id)
);

-- Create join table for post_team many-to-many relationship
CREATE TABLE post_team (
                           post_id BIGINT REFERENCES post(post_id),
                           team_id BIGINT REFERENCES team(team_id),
                           PRIMARY KEY (post_id, team_id)
);

-- Create indexes for frequently accessed columns
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_team_name ON team(team_name);
CREATE INDEX idx_post_created_at ON post(created_at);
CREATE INDEX idx_grade_user ON grade(user_id);
CREATE INDEX idx_schedule_start_date ON schedule(start_date);