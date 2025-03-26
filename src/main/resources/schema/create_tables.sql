-- Users 테이블 생성
CREATE TABLE IF NOT EXISTS "User" (
                                      id SERIAL PRIMARY KEY,
                                      email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp
    );

-- Posts 테이블 생성
CREATE TABLE IF NOT EXISTS "Post" (
                                      id SERIAL PRIMARY KEY,
                                      user_id INT NOT NULL REFERENCES "User" (id),
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT current_timestamp
    );

-- WorkoutData 테이블 생성
CREATE TABLE IF NOT EXISTS "WorkoutData" (
                                             id SERIAL PRIMARY KEY,
                                             post_id INT NOT NULL REFERENCES "Post" (id),
    user_id INT NOT NULL REFERENCES "User" (id),
    distance_km FLOAT NOT NULL,
    duration_seconds INT NOT NULL,
    avg_speed FLOAT NOT NULL,
    calories_burned FLOAT NOT NULL,
    location_data JSON
    );

-- Comment 테이블 생성
CREATE TABLE IF NOT EXISTS "Comment" (
                                         id SERIAL PRIMARY KEY,
                                         post_id INT NOT NULL REFERENCES "Post" (id),
    user_id INT NOT NULL REFERENCES "User" (id),
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp
    );

-- Like 테이블 생성
CREATE TABLE IF NOT EXISTS "Like" (
                                      id SERIAL PRIMARY KEY,
                                      post_id INT NOT NULL REFERENCES "Post" (id),
    user_id INT NOT NULL REFERENCES "User" (id)
    );

-- Challenge 테이블 생성
CREATE TABLE IF NOT EXISTS "Challenge" (
                                           id SERIAL PRIMARY KEY,
                                           title VARCHAR(255) NOT NULL,
    description TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    goal_distance_km FLOAT NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp
    );

-- ChallengeEntry 테이블 생성
CREATE TABLE IF NOT EXISTS "ChallengeEntry" (
                                                id SERIAL PRIMARY KEY,
                                                challenge_id INT NOT NULL REFERENCES "Challenge" (id),
    user_id INT NOT NULL REFERENCES "User" (id),
    total_distance_km FLOAT DEFAULT 0,
    ranking INT
    );
