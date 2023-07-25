DROP TABLE IF EXISTS brand, product, supplier, product_supplier CASCADE;


CREATE TABLE brand
(
    id         int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    brand_name varchar NOT NULL
);

CREATE TABLE product
(
    id           int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_name varchar UNIQUE NOT NULL,
    brand_id     bigint         NOT NULL,
    CONSTRAINT fk_product_brand FOREIGN KEY (brand_id) REFERENCES brand (id)
);

CREATE TABLE supplier
(
    id            int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    supplier_name varchar NOT NULL,
    supplier_contact varchar DEFAULT NULL
);

CREATE TABLE product_supplier
(
    product_id  int REFERENCES product (id),
    supplier_id int REFERENCES supplier (id),
    CONSTRAINT bill_product_pkey PRIMARY KEY (product_id, supplier_id)
);

INSERT INTO brand (brand_name)
VALUES ('brand_1');
INSERT INTO brand (brand_name)
VALUES ('brand_2');

INSERT INTO product (product_name, brand_id)
VALUES ('product_1', 1);
INSERT INTO product (product_name, brand_id)
VALUES ('product_2', 1);
INSERT INTO product (product_name, brand_id)
VALUES ('product_3', 2);

INSERT INTO supplier (supplier_name, supplier_contact)
VALUES ('supplier_1', 'supplier_contact_1');
INSERT INTO supplier (supplier_name, supplier_contact)
VALUES ('supplier_2', 'supplier_contact_2');

INSERT INTO product_supplier (product_id, supplier_id)
VALUES (1, 1);
INSERT INTO product_supplier (product_id, supplier_id)
VALUES (1, 2);
INSERT INTO product_supplier (product_id, supplier_id)
VALUES (2, 1);
INSERT INTO product_supplier (product_id, supplier_id)
VALUES (2, 2);
INSERT INTO product_supplier (product_id, supplier_id)
VALUES (3, 2);
