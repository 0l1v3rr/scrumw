CREATE TABLE users(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(32) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    reg_date DATE
);

CREATE TABLE projects(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(32) NOT NULL,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(255),
    public INT(1) NOT NULL,
    created DATE NOT NULL
);

CREATE TABLE issues(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    project_owner VARCHAR(32) NOT NULL,
    project_name VARCHAR(64) NOT NULL,
    issue_title VARCHAR(32) NOT NULL,
    issue_description VARCHAR(128) NOT NULL,
    is_open INT(1) DEFAULT 1,
    opened_by VARCHAR(32) NOT NULL,
    closed_by VARCHAR(32),
    opened DATE NOT NULL,
    closed DATE
);
