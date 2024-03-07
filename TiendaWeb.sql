create database tiendaweb;
use tiendaweb;

CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    actividad BIT(1) DEFAULT 1
);

insert into categoria(nombre, actividad) values ('Bebidas', 1);
insert into categoria(nombre, actividad) values ('Golosinas', 1);
insert into categoria(nombre, actividad) values ('Galletas', 1);
insert into categoria(nombre, actividad) values ('Fideos', 1);
insert into categoria(nombre, actividad) values ('Lacteos', 1);

CREATE TABLE marca (
    id_marca INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    actividad BIT(1) DEFAULT 1
);

INSERT INTO marca(nombre, actividad) values ('Coca Cola', 1);
INSERT INTO marca(nombre, actividad) values ('San Jorge', 1);
INSERT INTO marca(nombre, actividad) values ('Aji No Moto', 1);
INSERT INTO marca(nombre, actividad) values ('Doña Gusta', 1);
INSERT INTO marca(nombre, actividad) values ('Sibarita', 1);

CREATE TABLE proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(13) ,
    nombre VARCHAR(100) NOT NULL,
    ruc VARCHAR(11) UNIQUE NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    correo VARCHAR(200) NOT NULL,
    actividad BIT(1) DEFAULT 1
);

-- N + Ruc

DELIMITER //

CREATE TRIGGER generar_codigo_proveedor BEFORE INSERT ON proveedor
FOR EACH ROW
BEGIN
    DECLARE primer_letra VARCHAR(1);
    DECLARE codigo_generado VARCHAR(13);
    
    SET primer_letra = SUBSTRING(NEW.nombre, 1, 1);
    SET codigo_generado = CONCAT(primer_letra, NEW.ruc);
    
    SET NEW.codigo = codigo_generado;
END;
//

DELIMITER ;


INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Alicorp', 'San Isidro-Lima', '987654321', 'alicorp@gmail.com', 1, 12345678910);
INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Coca Cola', 'San Miguel-Lima', '987654123', 'cocacola@gmail.com', 1, 12345678929);
INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Galletas GN', 'Ventanilla-Lima', '987654321', 'galletasGn@gmail.com', 0, 12345658691);
INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Bebidas Corp', 'Villa el salvador', '987654321', 'bebidasCorp@gmail.com', 1, 32659874128);
INSERT INTO proveedor(nombre, direccion, telefono, correo, actividad, ruc) VALUES 
('Vega', 'San Isidro-Lima', '987654321', 'vega@gmail.com', 1, 98735624152);

SELECT * FROM proveedor;

CREATE TABLE estado_producto (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    actividad BIT(1) DEFAULT 1
);

INSERT INTO estado_producto(nombre, actividad) values('EN STOCK ', 1);
INSERT INTO estado_producto(nombre, actividad) values('AGOTADO', 1);
INSERT INTO estado_producto(nombre, actividad) values('DESCUENTO', 1);
INSERT INTO estado_producto(nombre, actividad) values('PROMOCION', 1);
INSERT INTO estado_producto(nombre, actividad) values('POCAS UNIDADES', 1);

CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(18),
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    foto VARCHAR(255) NOT NULL, 
    marca_id INT NOT NULL,
    categoria_id INT NOT NULL,
    proveedor_id INT NOT NULL,
    estado_id INT NOT NULL,
    stock INT NOT NULL,
    stock_minimo INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    codigobarra VARCHAR(15) NOT NULL,
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

CREATE TRIGGER generar_codigo_producto BEFORE INSERT ON producto
FOR EACH ROW
BEGIN
    DECLARE codigo_generado VARCHAR(18);
    SET codigo_generado = CONCAT('PD-', NEW.codigobarra);
    SET NEW.codigo = codigo_generado;
END;
//

DELIMITER ;

INSERT INTO producto (nombre, descripcion, foto, marca_id, categoria_id, proveedor_id, estado_id, stock, stock_minimo, precio, codigobarra, actividad)
VALUES ('Mayonesa Alacena 190g', 'Mayonesa Rica', 'Image.jpg' , 1, 1, 1, 1, 10, 5, 5.40, '1234567890', 1);
INSERT INTO producto (nombre, descripcion, foto, marca_id, categoria_id, proveedor_id, estado_id, stock, stock_minimo, precio, codigobarra, actividad)
VALUES ('Tallarin Don victorio', 'Tallarin de 1KG', 'Image.png',1, 4, 5, 1, 10, 5, 5.60, '1234567890', 1);
select * from producto;

CREATE TABLE rol (
	role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    rol_name VARCHAR(50)
);

CREATE TABLE usuario(
	id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(11),
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    dni VARCHAR(8) UNIQUE NOT NULL,
    telefono VARCHAR(11) NOT NULL,
    correo VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    actividad BIT(1) DEFAULT 1
);

DELIMITER //

CREATE TRIGGER generar_codigo_usuario BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
    DECLARE inicial_nombre VARCHAR(1);
    DECLARE inicial_apellido VARCHAR(1);
    
    SET inicial_nombre = LEFT(NEW.nombre, 1);
    SET inicial_apellido = LEFT(NEW.apellido, 1);
    
    SET NEW.codigo = CONCAT(inicial_nombre, inicial_apellido, NEW.dni);
END;
//

DELIMITER ;

CREATE TABLE user_role (
	user_id BIGINT,
    role_id BIGINT,
    FOREIGN KEY (user_id)
        REFERENCES usuario (id_usuario),
	FOREIGN KEY (role_id)
        REFERENCES rol (role_id)
);

CREATE TABLE carrito (
	cart_id BIGINT PRIMARY KEY AUTO_INCREMENT, 
    total_precio DOUBLE,
    user_id BIGINT,
     FOREIGN KEY (user_id)
        REFERENCES usuario (id_usuario)
);

CREATE TABLE carrito_detalle (
	cart_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    producto_precio DOUBLE,
    cantidad INT,
    cart_id BIGINT,
    producto_id INT,
    FOREIGN KEY(cart_id)
		REFERENCES carrito(cart_id),
    FOREIGN KEY(producto_id)
		REFERENCES producto(id_producto)
);

CREATE TABLE orden (
	orden_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    correo VARCHAR(255) NOT NULL,
    orden_fecha DATE, 
    orden_estado VARCHAR(255),
    total_cantidad DOUBLE,
    pago_id BIGINT,
	FOREIGN KEY (pago_id)
		REFERENCES pago (payment_id)
);

CREATE TABLE pago (
	payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    metodo_pay VARCHAR(255)
);

CREATE TABLE orden_item (
	orden_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    producto_precio DOUBLE,
    cantidad INT,
    orden_id BIGINT,
    producto_id INT,
    FOREIGN KEY (orden_id)
		REFERENCES orden(orden_id),
	FOREIGN KEY (producto_id) 
		REFERENCES producto (id_producto)
);
