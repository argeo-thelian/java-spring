DELETE FROM tbl_invoices;
INSERT INTO tbl_invoices (id, number_invoice, description, customer_id, create_at, state) VALUES (1, '0001', 'invoice office items', 1, NOW(), 'CREATED');

DELETE FROM tbl_invoice_items;
INSERT INTO tbl_invoice_items ( invoice_id, product_id, quantity, price) VALUES(1, 1, 5, 178.89);
INSERT INTO tbl_invoice_items ( invoice_id, product_id, quantity, price) VALUES(2, 2, 4, 12.5);
INSERT INTO tbl_invoice_items ( invoice_id, product_id, quantity, price) VALUES(3, 3, 12, 40.06);