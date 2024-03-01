create database tiendaweb;
use tiendaweb;

CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    actividad BIT(1)
);

insert into categoria(nombre, actividad) values ('Bebidas', 1);
insert into categoria(nombre, actividad) values ('Golosinas', 1);
insert into categoria(nombre, actividad) values ('Galletas', 1);
insert into categoria(nombre, actividad) values ('Fideos', 1);
insert into categoria(nombre, actividad) values ('Lacteos', 1);

CREATE TABLE marca (
    id_marca INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    actividad BIT(1)
);

INSERT INTO marca(nombre, actividad) values ('Coca Cola', 1);
INSERT INTO marca(nombre, actividad) values ('San Jorge', 1);
INSERT INTO marca(nombre, actividad) values ('Aji No Moto', 1);
INSERT INTO marca(nombre, actividad) values ('Doña Gusta', 1);
INSERT INTO marca(nombre, actividad) values ('Sibarita', 1);

CREATE TABLE proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) ,
    nombre VARCHAR(100) NOT NULL,
    ruc VARCHAR(11) UNIQUE NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(13) NOT NULL,
    correo VARCHAR(200) NOT NULL,
    actividad BIT(1)
);

DELIMITER //
CREATE TRIGGER generate_prov_codigo BEFORE INSERT ON proveedor
FOR EACH ROW
BEGIN
    DECLARE new_id INT;
    DECLARE codigo_prefix VARCHAR(4);
    SET codigo_prefix = 'PV';
    -- Obtenemos el nuevo ID autoincremental
    SELECT AUTO_INCREMENT INTO new_id
    FROM information_schema.TABLES
    WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'proveedor';
    -- Formateamos el nuevo ID para que tenga tres dígitos
    SET new_id = LPAD(new_id, 3, '0');
    -- Generamos el nuevo código del proveedor
    SET NEW.codigo = CONCAT(codigo_prefix, new_id);
END//
DELIMITER ;

INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Alicorp', 'San Isidro-Lima', '+51987654321', 'alicorp@gmail.com', 1, 12345678910);
INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Coca Cola', 'San Miguel-Lima', '+51987654123', 'cocacola@gmail.com', 1, 12345678929);
INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Galletas GN', 'Ventanilla-Lima', '+51987654321', 'galletasGn@gmail.com', 0, 12345658691);
INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Bebidas Corp', 'Villa el salvador', '+51987654321', 'bebidasCorp@gmail.com', 1, 32659874128);
INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Vega', 'San Isidro-Lima', '+51987654321', 'vega@gmail.com', 1, 98735624152);

SELECT * FROM proveedor;

CREATE TABLE estado_producto (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    actividad BIT(1)
);

INSERT INTO estado_producto(nombre, actividad) values('EN STOCK ', 1);
INSERT INTO estado_producto(nombre, actividad) values('AGOTADO', 1);
INSERT INTO estado_producto(nombre, actividad) values('DESCUENTO', 1);
INSERT INTO estado_producto(nombre, actividad) values('PROMOCION', 1);
INSERT INTO estado_producto(nombre, actividad) values('POCAS UNIDADES', 1);

CREATE TABLE productos (
    id_productos INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10),
    nombre VARCHAR(50),
    descripcion VARCHAR(200),
    marca_id INT,
    categoria_id INT,
    proveedor_id INT,
    estado_id INT,
    foto VARCHAR(200),
    stock INT NOT NULL,
    stock_minimo INT NOT NULL,
    precio_neto DECIMAL(10 , 2 ) NOT NULL,
    precio_bruto DECIMAL(10 , 2 ) NOT NULL,
    codigobarra VARCHAR(100) NOT NULL,
    actividad BIT(1),
    FOREIGN KEY (marca_id)
        REFERENCES marca (id_marca),
    FOREIGN KEY (categoria_id)
        REFERENCES categoria (id_categoria),
    FOREIGN KEY (proveedor_id)
        REFERENCES proveedor (id_proveedor),
    FOREIGN KEY (estado_id)
        REFERENCES estado_producto (id_estado)
);

DELIMITER //
CREATE TRIGGER generar_prod_cod BEFORE INSERT ON productos
FOR EACH ROW
BEGIN
    DECLARE ultimo_id INT;
    SELECT MAX(id_productos) INTO ultimo_id FROM productos;
    IF ultimo_id IS NULL THEN
        SET NEW.codigo = 'PD001';
    ELSE
        SET NEW.codigo = CONCAT('PD', LPAD(ultimo_id + 1, 3, '0'));
    END IF;
END;
//

INSERT INTO productos (nombre, descripcion, marca_id, categoria_id, proveedor_id, estado_id, foto, stock, stock_minimo, precio_neto, precio_bruto, codigobarra, actividad)
VALUES ('Mayonesa Alacena 190g', 'Mayonesa Rica', 1, 1, 1, 1, 'imagen.jpg', 10, 5, 4.00, 5.60, '1234567890', 1);
INSERT INTO productos (nombre, descripcion, marca_id, categoria_id, proveedor_id, estado_id, foto, stock, stock_minimo, precio_neto, precio_bruto, codigobarra, actividad)
VALUES ('Tallarin Don victorio', 'Tallarin de 1KG', 1, 4, 5, 1, 'imagen.jpg', 10, 5, 4.00, 5.60, '1234567890', 1);
INSERT INTO productos (nombre, descripcion, marca_id, categoria_id, proveedor_id, estado_id, foto, stock, stock_minimo, precio_neto, precio_bruto, codigobarra, actividad)
VALUES ('Leche Ideal', 'Leche Cremosita', 1, 1, 1, 1, 'imagen.jpg', 10, 5, 4.00, 5.60, '1234567890', 1);
INSERT INTO productos (nombre, descripcion, marca_id, categoria_id, proveedor_id, estado_id, foto, stock, stock_minimo, precio_neto, precio_bruto, codigobarra, actividad)
VALUES ('Detergente Ariel', 'Detergente de 460G', 1, 1, 1, 1, 'imagen.jpg', 10, 5, 4.00, 5.60, '1234567890', 1);
INSERT INTO productos (nombre, descripcion, marca_id, categoria_id, proveedor_id, estado_id, foto, stock, stock_minimo, precio_neto, precio_bruto, codigobarra, actividad)
VALUES ('Gaseosa 1L', 'Coca Cola ', 1, 1, 1, 1, 'imagen.jpg', 10, 5, 4.00, 5.60, '1234567890', 1);
select * from productos;
