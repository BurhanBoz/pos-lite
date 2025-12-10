-- Kanallar (idempotent)
INSERT INTO sales_channel (id, code, name) VALUES
  (1, 'RESTAURANT', 'Restoran')
ON DUPLICATE KEY UPDATE code=VALUES(code), name=VALUES(name);

INSERT INTO sales_channel (id, code, name) VALUES
  (2, 'PORTAL', 'Portal')
ON DUPLICATE KEY UPDATE code=VALUES(code), name=VALUES(name);

-- (İSTEĞE BAĞLI) Ürünleri de idempotent eklemek istersen:
-- INSERT INTO product (name, is_active) VALUES ('CHEDDAR BURGER', 1)
--   ON DUPLICATE KEY UPDATE is_active=VALUES(is_active);

-- Not: Maliyet/fiyat seed’ini artık otomatik koymak zorunda değilsin.
-- Gerekirse ON DUPLICATE KEY ile eklenebilir ama tarihçeli yapıda
-- business mantığını servisten yürütmen daha doğru.
