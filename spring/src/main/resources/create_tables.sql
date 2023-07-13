DROP TABLE IF EXISTS brand, product, distributor, product_distributor, wire, resistor CASCADE;
DROP SEQUENCE IF EXISTS product_id_sequence;

CREATE SEQUENCE product_id_sequence AS INT;

CREATE TABLE brand
(
    id         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    brand_name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE product
(
    id           INT DEFAULT nextval('product_id_sequence') PRIMARY KEY,
    product_name VARCHAR        NOT NULL,
    part_number  VARCHAR UNIQUE NOT NULL,
    brand_id     INT            NOT NULL,
    CONSTRAINT product_brand_fk FOREIGN KEY (brand_id) REFERENCES brand (id)
);

CREATE TABLE wire
(
    id               INT     not null
        CONSTRAINT wire_pkey
            PRIMARY KEY
        CONSTRAINT wire_product_id_fk
            REFERENCES product (id),
    awg              VARCHAR NOT NULL,
    outside_diameter DECIMAL NOT NULL
);

CREATE TABLE resistor
(
    id         INT    not null
        CONSTRAINT resistor_pkey
            PRIMARY KEY
        CONSTRAINT resistor_product_id_fk
            REFERENCES product (id),
    resistance BIGINT NOT NULL,
    voltage    INT    NOT NULL
);

CREATE TABLE distributor
(
    id                  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    distributor_name    VARCHAR UNIQUE NOT NULL,
    distributor_contact VARCHAR DEFAULT NULL
);

CREATE TABLE product_distributor
(
    product_id     INT NOT NULL REFERENCES product (id),
    distributor_id INT NOT NULL REFERENCES distributor (id),
    PRIMARY KEY (product_id, distributor_id)
);