DROP TABLE IF EXISTS brand, product, supplier, product_supplier, wire, resistor CASCADE;
DROP SEQUENCE IF EXISTS product_id_sequence;

CREATE SEQUENCE product_id_sequence AS INT;

CREATE TABLE brand
(
    id         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    brand_name VARCHAR NOT NULL
);

CREATE TABLE product
(
    id           INT DEFAULT nextval('product_id_sequence') PRIMARY KEY,
    product_name VARCHAR NOT NULL,
    brand_id     INT     NOT NULL,
    CONSTRAINT product_brand_fk FOREIGN KEY (brand_id) REFERENCES brand (id)
);

CREATE TABLE wire
(
    id               INT            not null
        CONSTRAINT wire_pkey
            PRIMARY KEY
        CONSTRAINT wire_product_id_fk
            REFERENCES product (id),
    part_number      VARCHAR UNIQUE NOT NULL,
    awg              VARCHAR        NOT NULL,
    outside_diameter DECIMAL        NOT NULL
);

CREATE TABLE resistor
(
    id          INT            not null
        CONSTRAINT resistor_pkey
            PRIMARY KEY
        CONSTRAINT resistor_product_id_fk
            REFERENCES product (id),
    part_number VARCHAR UNIQUE NOT NULL,
    resistance  BIGINT         NOT NULL,
    voltage     INT            NOT NULL
);

CREATE TABLE supplier
(
    id               INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    supplier_name    VARCHAR UNIQUE NOT NULL,
    supplier_contact VARCHAR DEFAULT NULL
);

CREATE TABLE product_supplier
(
    product_id  INT NOT NULL REFERENCES product (id),
    supplier_id INT NOT NULL REFERENCES supplier (id),
    CONSTRAINT bill_product_pkey PRIMARY KEY (product_id, supplier_id)
);