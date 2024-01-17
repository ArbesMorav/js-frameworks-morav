CREATE TABLE IF NOT EXISTS javascript_framework (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    rating DECIMAL(3, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS framework_version (
    id INT AUTO_INCREMENT PRIMARY KEY,
    framework_id INT NOT NULL,
    version INT NOT NULL,
    end_of_support DATE,
    FOREIGN KEY (framework_id) REFERENCES javascript_framework(id)
);
