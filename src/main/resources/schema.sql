-- product
CREATE TABLE IF NOT EXISTS product (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  is_active TINYINT(1) NOT NULL DEFAULT 1,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_product_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- sales_channel
CREATE TABLE IF NOT EXISTS sales_channel (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  UNIQUE KEY uk_channel_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- product_cost
CREATE TABLE IF NOT EXISTS product_cost (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT NOT NULL,
  cost DECIMAL(12,2) NOT NULL,
  effective_from DATETIME NOT NULL,
  effective_to DATETIME NULL,
  CONSTRAINT fk_product_cost_product
    FOREIGN KEY (product_id) REFERENCES product(id)
    ON DELETE CASCADE,
  INDEX idx_cost_product_from_to (product_id, effective_from, effective_to)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- product_price
CREATE TABLE IF NOT EXISTS product_price (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT NOT NULL,
  sales_channel_id BIGINT NOT NULL,
  price DECIMAL(12,2) NOT NULL,
  effective_from DATETIME NOT NULL,
  effective_to DATETIME NULL,
  CONSTRAINT fk_price_product
    FOREIGN KEY (product_id) REFERENCES product(id)
    ON DELETE CASCADE,
  CONSTRAINT fk_price_channel
    FOREIGN KEY (sales_channel_id) REFERENCES sales_channel(id)
    ON DELETE CASCADE,
  INDEX idx_price_product_channel_from_to (product_id, sales_channel_id, effective_from, effective_to)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- sale_entry
CREATE TABLE IF NOT EXISTS sale_entry (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sale_datetime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  product_id BIGINT NOT NULL,
  sales_channel_id BIGINT NOT NULL,
  quantity INT NOT NULL DEFAULT 1,
  unit_cost_snapshot DECIMAL(12,2) NOT NULL,
  unit_price_snapshot DECIMAL(12,2) NOT NULL,
  total_cost  DECIMAL(12,2) AS (quantity * unit_cost_snapshot) STORED,
  total_price DECIMAL(12,2) AS (quantity * unit_price_snapshot) STORED,
  profit      DECIMAL(12,2) AS (total_price - total_cost) STORED,
  note VARCHAR(255) NULL,
  CONSTRAINT fk_sale_product  FOREIGN KEY (product_id) REFERENCES product(id),
  CONSTRAINT fk_sale_channel  FOREIGN KEY (sales_channel_id) REFERENCES sales_channel(id),
  INDEX idx_sale_by_date (sale_datetime),
  INDEX idx_sale_by_product (product_id),
  INDEX idx_sale_by_channel (sales_channel_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
