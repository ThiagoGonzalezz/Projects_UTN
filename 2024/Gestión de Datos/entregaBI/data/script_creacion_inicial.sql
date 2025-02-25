USE GD2C2024
GO

CREATE SCHEMA LOS_PEORES
GO

CREATE SYNONYM Maestra FOR gd_esquema.Maestra;
GO

-- TABLAS

CREATE TABLE LOS_PEORES.tipo_medio_pago(
    tipomed_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    tipomed_detalle NVARCHAR(50) NOT NULL
);
GO



CREATE TABLE LOS_PEORES.medio_pago(
    medi_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    medi_detalle NVARCHAR(50) NOT NULL,
    medi_tipo DECIMAL(18,0) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.tipo_envio(
    tipoenv_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    tipoenv_descripcion NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.concepto(
    conc_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    conc_detalle NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.marca_productos(
    marc_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    marc_detalle NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.modelo_productos(
		mode_id DECIMAL(18,0) NOT NULL IDENTITY(1,1),
        mode_codigo DECIMAL(18,0) NOT NULL,
        mode_descripcion NVARCHAR(50) NOT NULL,
        mode_marca_producto DECIMAL(18,0) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.rubro(
    rubr_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    rubr_detalle NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.sub_rubro(
    subr_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    subr_detalle NVARCHAR(50) NOT NULL,
    subr_padre DECIMAL(18,0)
);
GO

CREATE TABLE LOS_PEORES.usuario(
    usua_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    usua_nombre NVARCHAR(50) NOT NULL,
    usua_contrasenia NVARCHAR(50) NOT NULL,
    usua_fecha_creacion DATE NOT NULL
);
GO

CREATE TABLE LOS_PEORES.provincia(
    prov_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    prov_nombre NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.localidad(
    loca_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    loca_nombre NVARCHAR(50) NOT NULL,
    loca_provincia DECIMAL(18,0) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.domicilio(
    domi_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    domi_calle NVARCHAR(50) NOT NULL,
    domi_altura DECIMAL(18,0) NOT NULL,
    domi_localidad DECIMAL(18,0) NOT NULL,
    domi_piso DECIMAL(18,0) NULL,
    domi_depto NVARCHAR(50) NULL,
    domi_cp NVARCHAR(50) NOT NULL,
    domi_usuario DECIMAL(18,0) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.vendedor(
    vend_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    vend_cuit NVARCHAR(50) NOT NULL,
    vend_razonsocial NVARCHAR(50) NOT NULL,
    vend_mail NVARCHAR(50) NOT NULL,
    vend_usuario DECIMAL(18,0) NULL,
);
GO

CREATE TABLE LOS_PEORES.factura (
    fact_numero DECIMAL(18,0) NOT NULL,
    fact_fecha DATE NOT NULL,
    fact_vendedor DECIMAL(18,0) NOT NULL,   
    fact_total DECIMAL(18,2) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.cliente(
    clie_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    clie_nombre NVARCHAR(50) NOT NULL,
    clie_apellido NVARCHAR(50) NOT NULL,
    clie_fecha_nac DATE NOT NULL,
    clie_mail NVARCHAR(50) NULL,
	clie_dni DECIMAL(18,0) NULL,
	clie_usuario DECIMAL(18,0) NULL,
);
GO

CREATE TABLE LOS_PEORES.venta(
    vent_codigo DECIMAL(18,0) NOT NULL,
    vent_cliente DECIMAL(18,0) NOT NULL,
	vent_fecha_hora DATETIME NOT NULL,
	vent_total DECIMAL(18,2) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.almacen(
    alma_codigo DECIMAL(18,0) NOT NULL,
    alma_calle NVARCHAR(50) NOT NULL, 
    alma_altura DECIMAL(18,0) NOT NULL,
    alma_localidad DECIMAL(18,0) NOT NULL,
    alma_costo_dia_alquiler DECIMAL(18,2) NOT NULL,
);
GO

CREATE TABLE LOS_PEORES.producto(
    prod_id DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    prod_codigo NVARCHAR(50) NOT NULL,
    prod_descripcion NVARCHAR(50) NOT NULL,
    prod_subrubro DECIMAL(18,0) NOT NULL,
    prod_modelo DECIMAL(18,0) NOT NULL
);
GO


CREATE TABLE LOS_PEORES.pago(
	pago_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
	pago_fecha DATE NOT NULL,
    pago_venta DECIMAL(18,0) NOT NULL, 
    pago_medio DECIMAL(18,0) NOT NULL,
    pago_numero_tarjeta NVARCHAR(50) NOT NULL,
    pago_vencimiento_tarjeta DATE NOT NULL,
	pago_cuotas DECIMAL(18,0) NOT NULL,
	pago_importe DECIMAL(18,2) NOT NULL
);
GO 


CREATE TABLE LOS_PEORES.envio(
    envi_codigo DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    envi_fecha_programada DATE,
    envi_horario_inicio DECIMAL(18,0),
    envi_horario_fin DECIMAL(18,0),
    envi_costo DECIMAL(18,2),
    envi_fecha_hora_entrega DATETIME,
    envi_venta DECIMAL(18,0),
    envi_domicilio DECIMAL(18,0),
    envi_tipo DECIMAL(18,0)
);
GO

CREATE TABLE LOS_PEORES.publicacion (
    publ_codigo DECIMAL(18,0) NOT NULL,
    publ_descripcion NVARCHAR(50) NOT NULL,
    publ_fecha_inicio DATE NOT NULL,
	publ_prod DECIMAL(18,0) NOT NULL,
    publ_fecha_fin DATE NOT NULL,   
    publ_stock DECIMAL(18,0) NOT NULL,
	publ_precio DECIMAL(18,2) NOT NULL,
	publ_vendedor DECIMAL(18,0) NOT NULL,
	publ_almacen DECIMAL(18,0) NOT NULL,     
    publ_costo_publicacion DECIMAL(18,2) NOT NULL,
    publ_porcentaje_por_venta DECIMAL(18,2) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.deta_venta(
    deta_publicacion DECIMAL(18,0) NOT NULL,
    deta_venta DECIMAL(18,0) NOT NULL,
    deta_precio DECIMAL(18,2) NOT NULL,
    deta_cantidad DECIMAL(18,0) NOT NULL,
    deta_subtotal DECIMAL(18,2) NOT NULL
);
GO


CREATE TABLE LOS_PEORES.item_factura(
    item_fact_numero DECIMAL(18,0) NOT NULL,
    item_fact_publicacion DECIMAL(18,0) NOT NULL,
    item_fact_concepto DECIMAL(18,0) NOT NULL,
    item_fact_precio DECIMAL(18,2) NOT NULL,
    item_fact_cantidad DECIMAL(18,0) NOT NULL,
    item_fact_subtotal DECIMAL(18,2) NOT NULL
);
GO


-- CONSTRAINTS

ALTER TABLE LOS_PEORES.tipo_medio_pago ADD CONSTRAINT PK_tipo_medio_pago PRIMARY KEY(tipomed_codigo);
GO

ALTER TABLE LOS_PEORES.medio_pago ADD CONSTRAINT PK_medi_codigo PRIMARY KEY(medi_codigo);
ALTER TABLE LOS_PEORES.medio_pago ADD CONSTRAINT FK_medi_tipo FOREIGN KEY(medi_tipo) REFERENCES LOS_PEORES.tipo_medio_pago(tipomed_codigo);
GO

ALTER TABLE LOS_PEORES.tipo_envio ADD CONSTRAINT PK_tipoenv_codigo PRIMARY KEY(tipoenv_codigo);
GO

ALTER TABLE LOS_PEORES.concepto ADD CONSTRAINT PK_conc_codigo PRIMARY KEY(conc_codigo);
GO

ALTER TABLE LOS_PEORES.marca_productos ADD CONSTRAINT PK_marca_productos PRIMARY KEY(marc_codigo);
GO

ALTER TABLE LOS_PEORES.modelo_productos ADD CONSTRAINT PK_modelo_productos PRIMARY KEY(mode_id);
ALTER TABLE LOS_PEORES.modelo_productos ADD CONSTRAINT FK_modelo_marca FOREIGN KEY (mode_marca_producto) REFERENCES LOS_PEORES.marca_productos(marc_codigo);
GO

ALTER TABLE LOS_PEORES.rubro ADD CONSTRAINT PK_rubro PRIMARY KEY(rubr_codigo);
GO

ALTER TABLE LOS_PEORES.sub_rubro ADD CONSTRAINT PK_sub_rubro PRIMARY KEY(subr_codigo);
ALTER TABLE LOS_PEORES.sub_rubro ADD CONSTRAINT FK_subrubro_rubro FOREIGN KEY(subr_padre) REFERENCES LOS_PEORES.rubro(rubr_codigo);
GO

ALTER TABLE LOS_PEORES.usuario ADD CONSTRAINT PK_usuario PRIMARY KEY(usua_codigo);
GO

ALTER TABLE LOS_PEORES.provincia ADD CONSTRAINT PK_provincia PRIMARY KEY(prov_codigo);
GO

ALTER TABLE LOS_PEORES.localidad ADD CONSTRAINT PK_localidad PRIMARY KEY(loca_codigo);
ALTER TABLE LOS_PEORES.localidad ADD CONSTRAINT FK_localidad_provincia FOREIGN KEY (loca_provincia) REFERENCES LOS_PEORES.provincia(prov_codigo);
GO

ALTER TABLE LOS_PEORES.domicilio ADD CONSTRAINT PK_domicilio PRIMARY KEY(domi_codigo);
ALTER TABLE LOS_PEORES.domicilio ADD CONSTRAINT FK_domicilio_localidad FOREIGN KEY (domi_localidad) REFERENCES LOS_PEORES.localidad(loca_codigo);
ALTER TABLE LOS_PEORES.domicilio ADD CONSTRAINT FK_domicilio_usuario FOREIGN KEY (domi_usuario) REFERENCES LOS_PEORES.usuario(usua_codigo);
GO

ALTER TABLE LOS_PEORES.vendedor ADD CONSTRAINT PK_vendedor PRIMARY KEY(vend_codigo);
ALTER TABLE LOS_PEORES.vendedor ADD CONSTRAINT FK_vend_usuario FOREIGN KEY (vend_usuario) REFERENCES LOS_PEORES.usuario(usua_codigo);
GO

ALTER TABLE LOS_PEORES.cliente ADD CONSTRAINT PK_cliente PRIMARY KEY(clie_codigo);
ALTER TABLE LOS_PEORES.cliente ADD CONSTRAINT FK_clie_usuario FOREIGN KEY (clie_usuario) REFERENCES LOS_PEORES.usuario(usua_codigo);
GO

ALTER TABLE LOS_PEORES.venta ADD CONSTRAINT PK_venta PRIMARY KEY(vent_codigo);
ALTER TABLE LOS_PEORES.venta ADD CONSTRAINT FK_vent_cliente FOREIGN KEY (vent_cliente) REFERENCES LOS_PEORES.cliente(clie_codigo);
GO

ALTER TABLE LOS_PEORES.factura 
ADD CONSTRAINT PK_factura PRIMARY KEY(fact_numero);
GO

ALTER TABLE LOS_PEORES.factura 
ADD CONSTRAINT FK_factura_vendedor FOREIGN KEY(fact_vendedor) REFERENCES LOS_PEORES.vendedor(vend_codigo);
GO

ALTER TABLE LOS_PEORES.almacen ADD CONSTRAINT PK_almacen PRIMARY KEY(alma_codigo);
ALTER TABLE LOS_PEORES.almacen ADD CONSTRAINT FK_almacen_localidad FOREIGN KEY(alma_localidad) REFERENCES LOS_PEORES.localidad(loca_codigo);
GO

ALTER TABLE LOS_PEORES.producto ADD CONSTRAINT PK_producto PRIMARY KEY(prod_id);
ALTER TABLE LOS_PEORES.producto ADD CONSTRAINT FK_producto_modelo FOREIGN KEY(prod_modelo) REFERENCES LOS_PEORES.modelo_productos(mode_id);
ALTER TABLE LOS_PEORES.producto ADD CONSTRAINT FK_producto_subrubro FOREIGN KEY(prod_subrubro) REFERENCES LOS_PEORES.sub_rubro(subr_codigo);
GO


ALTER TABLE LOS_PEORES.publicacion ADD CONSTRAINT PK_publicacion PRIMARY KEY(publ_codigo);
ALTER TABLE LOS_PEORES.publicacion 
ADD CONSTRAINT FK_publicacion_producto FOREIGN KEY(publ_prod) REFERENCES LOS_PEORES.producto(prod_id);
GO

ALTER TABLE LOS_PEORES.publicacion 
ADD CONSTRAINT FK_publicacion_vendedor FOREIGN KEY(publ_vendedor) REFERENCES LOS_PEORES.vendedor(vend_codigo);
GO

ALTER TABLE LOS_PEORES.publicacion 
ADD CONSTRAINT FK_publicacion_almacen FOREIGN KEY(publ_almacen) REFERENCES LOS_PEORES.almacen(alma_codigo);
GO


ALTER TABLE LOS_PEORES.pago ADD CONSTRAINT PK_pago PRIMARY KEY(pago_codigo);
ALTER TABLE LOS_PEORES.pago ADD CONSTRAINT FK_pago_venta FOREIGN KEY(pago_venta) REFERENCES LOS_PEORES.venta(vent_codigo);
ALTER TABLE LOS_PEORES.pago ADD CONSTRAINT FK_pago_medio FOREIGN KEY(pago_medio) REFERENCES LOS_PEORES.medio_pago(medi_codigo);
GO


ALTER TABLE LOS_PEORES.envio ADD CONSTRAINT PK_envio PRIMARY KEY(envi_codigo);
ALTER TABLE LOS_PEORES.envio ADD CONSTRAINT FK_envio_venta FOREIGN KEY(envi_venta) REFERENCES LOS_PEORES.venta(vent_codigo);
ALTER TABLE LOS_PEORES.envio ADD CONSTRAINT FK_envio_domicilio FOREIGN KEY(envi_domicilio) REFERENCES LOS_PEORES.domicilio(domi_codigo);
ALTER TABLE LOS_PEORES.envio ADD CONSTRAINT FK_envio_tipo FOREIGN KEY(envi_tipo) REFERENCES LOS_PEORES.tipo_envio(tipoenv_codigo);
GO

ALTER TABLE LOS_PEORES.deta_venta ADD CONSTRAINT PK_deta_venta PRIMARY KEY(deta_publicacion, deta_venta);
ALTER TABLE LOS_PEORES.deta_venta ADD CONSTRAINT FK_deta_publicacion FOREIGN KEY (deta_publicacion) REFERENCES LOS_PEORES.publicacion(publ_codigo);
ALTER TABLE LOS_PEORES.deta_venta ADD CONSTRAINT FK_deta_venta FOREIGN KEY (deta_venta) REFERENCES LOS_PEORES.venta(vent_codigo);
GO

ALTER TABLE LOS_PEORES.item_factura ADD CONSTRAINT PK_item_factura PRIMARY KEY(item_fact_numero, item_fact_publicacion, item_fact_concepto);
ALTER TABLE LOS_PEORES.item_factura ADD CONSTRAINT FK_item_fact_numero FOREIGN KEY (item_fact_numero) REFERENCES LOS_PEORES.factura(fact_numero);
ALTER TABLE LOS_PEORES.item_factura ADD CONSTRAINT FK_item_fact_publicacion FOREIGN KEY (item_fact_publicacion) REFERENCES LOS_PEORES.publicacion(publ_codigo);
GO

-- PROCEDURES

CREATE PROC LOS_PEORES.migrar_tipo_medio_pago AS
BEGIN
    INSERT INTO LOS_PEORES.tipo_medio_pago (tipomed_detalle)
    SELECT DISTINCT Maestra.PAGO_TIPO_MEDIO_PAGO 
    FROM Maestra 
    WHERE Maestra.PAGO_TIPO_MEDIO_PAGO IS NOT NULL;
END
GO

CREATE PROC LOS_PEORES.migrar_medio_pago AS
BEGIN
    INSERT INTO LOS_PEORES.medio_pago (medi_detalle, medi_tipo)
    SELECT DISTINCT PAGO_MEDIO_PAGO, tipomed_codigo 
    FROM Maestra JOIN LOS_PEORES.tipo_medio_pago ON PAGO_TIPO_MEDIO_PAGO = tipomed_detalle
    WHERE PAGO_MEDIO_PAGO IS NOT NULL;
END
GO

CREATE PROC LOS_PEORES.migrar_tipo_envio AS
BEGIN
    INSERT INTO LOS_PEORES.tipo_envio (tipoenv_descripcion)
    SELECT DISTINCT ENVIO_TIPO
    FROM Maestra 
    WHERE ENVIO_TIPO IS NOT NULL;
END
GO

CREATE PROC LOS_PEORES.migrar_concepto AS
BEGIN
    INSERT INTO LOS_PEORES.concepto (conc_detalle)
    SELECT DISTINCT FACTURA_DET_TIPO
    FROM Maestra 
    WHERE FACTURA_DET_TIPO IS NOT NULL;
END
GO

CREATE PROC LOS_PEORES.migrar_marca_productos AS
BEGIN
    INSERT INTO LOS_PEORES.marca_productos (marc_detalle)
    SELECT DISTINCT PRODUCTO_MARCA 
    FROM Maestra 
    WHERE PRODUCTO_MARCA IS NOT NULL;
END
GO

CREATE PROC LOS_PEORES.migrar_modelo_productos AS 
BEGIN
    INSERT INTO LOS_PEORES.modelo_productos (mode_codigo,mode_descripcion, mode_marca_producto)
        SELECT DISTINCT PRODUCTO_MOD_CODIGO, PRODUCTO_MOD_DESCRIPCION, marc_codigo
        FROM Maestra JOIN LOS_PEORES.marca_productos ON PRODUCTO_MARCA = marc_detalle
        WHERE PRODUCTO_MOD_CODIGO IS NOT NULL AND PRODUCTO_MOD_DESCRIPCION IS NOT NULL
END
GO 

CREATE PROC LOS_PEORES.migracion_rubro AS
BEGIN
    INSERT INTO LOS_PEORES.rubro (rubr_detalle)
    SELECT DISTINCT Maestra.PRODUCTO_RUBRO_DESCRIPCION
    FROM Maestra 
    WHERE Maestra.PRODUCTO_RUBRO_DESCRIPCION IS NOT NULL;
END
GO 

CREATE PROC LOS_PEORES.migracion_sub_rubro AS
BEGIN
    INSERT INTO LOS_PEORES.sub_rubro (subr_detalle, subr_padre)
    SELECT DISTINCT PRODUCTO_SUB_RUBRO AS subr_detalle, rubr_codigo AS subr_padre
    FROM Maestra
    JOIN LOS_PEORES.rubro ON PRODUCTO_RUBRO_DESCRIPCION = rubr_detalle
    WHERE PRODUCTO_SUB_RUBRO IS NOT NULL AND PRODUCTO_RUBRO_DESCRIPCION IS NOT NULL;
END
GO

CREATE PROC LOS_PEORES.migrar_usuarios AS
BEGIN
    INSERT INTO LOS_PEORES.usuario (usua_nombre, usua_contrasenia, usua_fecha_creacion)
    (SELECT DISTINCT CLI_USUARIO_NOMBRE, CLI_USUARIO_PASS, CLI_USUARIO_FECHA_CREACION 
    FROM Maestra 
    WHERE CLI_USUARIO_NOMBRE IS NOT NULL AND CLI_USUARIO_PASS IS NOT NULL AND CLI_USUARIO_FECHA_CREACION IS NOT NULL)
	UNION
	(SELECT DISTINCT VEN_USUARIO_NOMBRE, VEN_USUARIO_PASS, VEN_USUARIO_FECHA_CREACION 
    FROM Maestra 
    WHERE VEN_USUARIO_NOMBRE IS NOT NULL AND VEN_USUARIO_PASS IS NOT NULL AND VEN_USUARIO_FECHA_CREACION IS NOT NULL)
END
GO

CREATE PROC LOS_PEORES.migrar_provincias AS
BEGIN
    INSERT INTO LOS_PEORES.provincia (prov_nombre)
     (SELECT DISTINCT CLI_USUARIO_DOMICILIO_PROVINCIA 
    FROM Maestra 
    WHERE CLI_USUARIO_DOMICILIO_PROVINCIA IS NOT NULL)
	UNION
    (SELECT DISTINCT ALMACEN_PROVINCIA 
    FROM Maestra 
    WHERE ALMACEN_PROVINCIA IS NOT NULL)
END
GO

CREATE PROC LOS_PEORES.migrar_localidades AS
BEGIN
    INSERT INTO LOS_PEORES.localidad (loca_nombre, loca_provincia)
    (SELECT DISTINCT CLI_USUARIO_DOMICILIO_LOCALIDAD, prov_codigo
    FROM Maestra 
    JOIN LOS_PEORES.provincia ON prov_nombre = CLI_USUARIO_DOMICILIO_PROVINCIA
    WHERE CLI_USUARIO_DOMICILIO_LOCALIDAD IS NOT NULL)
	UNION
	(SELECT DISTINCT ALMACEN_Localidad, prov_codigo
    FROM Maestra 
    JOIN LOS_PEORES.provincia ON prov_nombre = ALMACEN_PROVINCIA
    WHERE ALMACEN_Localidad IS NOT NULL)
END
GO

CREATE PROC LOS_PEORES.migrar_domicilios AS
BEGIN
    INSERT INTO domicilio (domi_calle, domi_altura, domi_localidad, domi_piso, domi_depto, domi_cp, domi_usuario)
		(SELECT DISTINCT CLI_USUARIO_DOMICILIO_CALLE, CLI_USUARIO_DOMICILIO_NRO_CALLE, loca_codigo, CLI_USUARIO_DOMICILIO_PISO, CLI_USUARIO_DOMICILIO_DEPTO, CLI_USUARIO_DOMICILIO_CP, usua_codigo
        FROM Maestra 
        JOIN LOS_PEORES.provincia ON prov_nombre = CLI_USUARIO_DOMICILIO_PROVINCIA
        JOIN LOS_PEORES.localidad ON loca_nombre+STR(loca_provincia) = CLI_USUARIO_DOMICILIO_LOCALIDAD+STR(prov_codigo) 
		JOIN LOS_PEORES.usuario ON  usua_nombre+usua_contrasenia = CLI_USUARIO_NOMBRE+CLI_USUARIO_PASS AND usua_fecha_creacion = CLI_USUARIO_FECHA_CREACION
        WHERE CLI_USUARIO_DOMICILIO_CALLE IS NOT NULL AND CLI_USUARIO_DOMICILIO_NRO_CALLE IS NOT NULL AND CLI_USUARIO_DOMICILIO_CP IS NOT NULL)
		UNION
		(SELECT DISTINCT VEN_USUARIO_DOMICILIO_CALLE, VEN_USUARIO_DOMICILIO_NRO_CALLE, loca_codigo, VEN_USUARIO_DOMICILIO_PISO, VEN_USUARIO_DOMICILIO_DEPTO, VEN_USUARIO_DOMICILIO_CP, usua_codigo
        FROM Maestra 
        JOIN LOS_PEORES.provincia ON prov_nombre = VEN_USUARIO_DOMICILIO_PROVINCIA
        JOIN LOS_PEORES.localidad ON loca_nombre+STR(loca_provincia) = VEN_USUARIO_DOMICILIO_LOCALIDAD+STR(prov_codigo) 
        JOIN LOS_PEORES.usuario ON  usua_nombre+usua_contrasenia = VEN_USUARIO_NOMBRE+VEN_USUARIO_PASS AND usua_fecha_creacion = VEN_USUARIO_FECHA_CREACION
		WHERE VEN_USUARIO_DOMICILIO_CALLE IS NOT NULL AND VEN_USUARIO_DOMICILIO_NRO_CALLE IS NOT NULL AND VEN_USUARIO_DOMICILIO_CP IS NOT NULL)
		END
GO

CREATE PROC LOS_PEORES.migrar_vendedores AS
BEGIN
    INSERT INTO LOS_PEORES.vendedor(vend_cuit, vend_razonsocial, vend_mail, vend_usuario)
    SELECT DISTINCT VENDEDOR_CUIT, VENDEDOR_RAZON_SOCIAL, VENDEDOR_MAIL, usua_codigo
    FROM Maestra 
    JOIN LOS_PEORES.usuario ON VEN_USUARIO_NOMBRE+VEN_USUARIO_PASS = usua_nombre+usua_contrasenia AND VEN_USUARIO_FECHA_CREACION=usua_fecha_creacion
    WHERE VENDEDOR_CUIT IS NOT NULL
END
GO

CREATE PROC LOS_PEORES.migrar_clientes AS
BEGIN
    INSERT INTO LOS_PEORES.cliente(clie_nombre, clie_apellido, clie_fecha_nac, clie_mail, clie_dni, clie_usuario)
    SELECT DISTINCT CLIENTE_NOMBRE, CLIENTE_APELLIDO, CLIENTE_FECHA_NAC, CLIENTE_MAIL, CLIENTE_DNI, usua_codigo
    FROM Maestra 
    JOIN LOS_PEORES.usuario ON CLI_USUARIO_NOMBRE+CLI_USUARIO_PASS = usua_nombre+usua_contrasenia AND CLI_USUARIO_FECHA_CREACION=usua_fecha_creacion
    WHERE CLIENTE_DNI IS NOT NULL 
END
GO

CREATE PROC LOS_PEORES.migrar_ventas AS
BEGIN
    INSERT INTO LOS_PEORES.venta(vent_codigo, vent_cliente, vent_fecha_hora, vent_total)
    SELECT DISTINCT VENTA_CODIGO, clie_codigo, VENTA_FECHA, VENTA_TOTAL
    FROM Maestra 
	JOIN LOS_PEORES.cliente ON STR(CLIENTE_DNI)+CLIENTE_NOMBRE+CLIENTE_APELLIDO = STR(clie_dni)+clie_nombre+clie_apellido 
	WHERE VENTA_CODIGO IS NOT NULL 
END
GO

CREATE PROC LOS_PEORES.migrar_almacenes AS
BEGIN
    INSERT INTO LOS_PEORES.almacen (alma_codigo, alma_calle, alma_altura, alma_localidad, alma_costo_dia_alquiler)
    SELECT DISTINCT ALMACEN_CODIGO, ALMACEN_CALLE, ALMACEN_NRO_CALLE, loca_codigo, ALMACEN_COSTO_DIA_AL
    FROM Maestra 
    JOIN LOS_PEORES.provincia ON prov_nombre = ALMACEN_PROVINCIA
    JOIN LOS_PEORES.localidad ON loca_nombre+STR(loca_provincia) = ALMACEN_Localidad+STR(prov_codigo)
    WHERE ALMACEN_CODIGO IS NOT NULL;
END
GO

CREATE PROC LOS_PEORES.migrar_producto AS
BEGIN
    INSERT INTO LOS_PEORES.producto(prod_codigo, prod_descripcion, prod_subrubro, prod_modelo)
    SELECT DISTINCT PRODUCTO_CODIGO, PRODUCTO_DESCRIPCION, subr_codigo, mode_id
    FROM Maestra
	JOIN LOS_PEORES.rubro ON rubr_detalle = PRODUCTO_RUBRO_DESCRIPCION
    JOIN LOS_PEORES.sub_rubro ON subr_detalle+STR(subr_padre)=PRODUCTO_SUB_RUBRO+STR(rubr_codigo)
	JOIN LOS_PEORES.marca_productos ON marc_detalle = PRODUCTO_MARCA 
	JOIN LOS_PEORES.modelo_productos ON mode_codigo+STR(mode_marca_producto)=PRODUCTO_MOD_CODIGO+STR(marc_codigo)
    WHERE PRODUCTO_SUB_RUBRO IS NOT NULL AND PRODUCTO_MOD_CODIGO IS NOT NULL
END 
GO


CREATE PROC LOS_PEORES.migrar_facturas AS
BEGIN
    INSERT INTO LOS_PEORES.factura (fact_numero, fact_fecha, fact_vendedor, fact_total)
    SELECT DISTINCT 
        FACTURA_NUMERO,             
        FACTURA_FECHA,                
        publ_vendedor,                  
        FACTURA_TOTAL                 
    FROM  Maestra
     JOIN LOS_PEORES.publicacion ON PUBLICACION_CODIGO = publ_codigo  
    WHERE FACTURA_NUMERO IS NOT NULL
      AND FACTURA_FECHA IS NOT NULL
      AND FACTURA_TOTAL IS NOT NULL;
END;
GO



CREATE PROC LOS_PEORES.migrar_pagos AS
BEGIN
    INSERT INTO LOS_PEORES.pago (pago_fecha, pago_venta, pago_medio, pago_numero_tarjeta, pago_vencimiento_tarjeta, pago_cuotas, pago_importe)
    SELECT DISTINCT PAGO_FECHA, vent_codigo, medi_codigo, PAGO_NRO_TARJETA, PAGO_FECHA_VENC_TARJETA, PAGO_CANT_CUOTAS, PAGO_IMPORTE
    FROM Maestra 
    JOIN LOS_PEORES.venta ON vent_codigo = VENTA_CODIGO
	JOIN LOS_PEORES.tipo_medio_pago ON tipomed_detalle = PAGO_TIPO_MEDIO_PAGO
    JOIN LOS_PEORES.medio_pago ON medi_detalle+STR(medi_tipo) = PAGO_MEDIO_PAGO+STR(tipomed_codigo) 
    WHERE PAGO_IMPORTE IS NOT NULL;
END
GO

CREATE PROC LOS_PEORES.migrar_envios AS
BEGIN
    INSERT INTO LOS_PEORES.envio(envi_fecha_programada, envi_horario_inicio, envi_horario_fin, envi_costo,envi_fecha_hora_entrega,
                                    envi_venta, envi_domicilio, envi_tipo)
    SELECT DISTINCT ENVIO_FECHA_PROGAMADA, ENVIO_HORA_INICIO, ENVIO_HORA_FIN_INICIO, ENVIO_COSTO, ENVIO_FECHA_ENTREGA, vent_codigo, domi_codigo, tipoenv_codigo
    FROM Maestra 
    JOIN LOS_PEORES.provincia ON prov_nombre = CLI_USUARIO_DOMICILIO_PROVINCIA
    JOIN LOS_PEORES.localidad ON loca_nombre+STR(loca_provincia) = CLI_USUARIO_DOMICILIO_LOCALIDAD+STR(prov_codigo)
    JOIN LOS_PEORES.venta ON VENTA_CODIGO=vent_codigo 
    JOIN LOS_PEORES.usuario ON CLI_USUARIO_NOMBRE+CLI_USUARIO_PASS = usua_nombre+usua_contrasenia AND CLI_USUARIO_FECHA_CREACION=usua_fecha_creacion -- En este caso la estrategia es joinear por nombre, contraseña de usuario y fecha de creación
    JOIN LOS_PEORES.domicilio ON str(domi_altura)+domi_calle+domi_cp+domi_depto+str(domi_localidad)+str(domi_piso)+str(domi_usuario)
                =str(CLI_USUARIO_DOMICILIO_NRO_CALLE)+CLI_USUARIO_DOMICILIO_CALLE+CLI_USUARIO_DOMICILIO_CP+CLI_USUARIO_DOMICILIO_DEPTO+str(loca_codigo)+str(CLI_USUARIO_DOMICILIO_PISO)+str(usua_codigo)
    JOIN LOS_PEORES.tipo_envio ON tipoenv_descripcion=ENVIO_TIPO
    WHERE ENVIO_FECHA_PROGAMADA IS NOT NULL AND ENVIO_HORA_INICIO IS NOT NULL AND ENVIO_HORA_FIN_INICIO IS NOT NULL
            AND ENVIO_COSTO IS NOT NULL AND ENVIO_FECHA_ENTREGA IS NOT NULL
END
GO


CREATE PROC LOS_PEORES.migrar_publicacion AS
BEGIN
    
    INSERT INTO LOS_PEORES.publicacion (
	    publ_codigo,
        publ_descripcion, 
        publ_fecha_inicio, 
        publ_fecha_fin,
		publ_prod,                
        publ_stock, 
        publ_precio,           
		publ_vendedor,
		publ_almacen,    
        publ_costo_publicacion, 
        publ_porcentaje_por_venta
    )
  SELECT DISTINCT
        PUBLICACION_CODIGO,
        PUBLICACION_DESCRIPCION,
        PUBLICACION_FECHA,
        PUBLICACION_FECHA_V,
        prod_id,                          
        PUBLICACION_STOCK,
        PUBLICACION_PRECIO,
        vend_codigo,
        alma_codigo,
        PUBLICACION_COSTO,
        PUBLICACION_PORC_VENTA
    FROM Maestra
		 JOIN LOS_PEORES.rubro ON rubr_detalle = PRODUCTO_RUBRO_DESCRIPCION 
		 JOIN LOS_PEORES.sub_rubro ON subr_detalle+str(subr_padre) = PRODUCTO_SUB_RUBRO+str(rubr_codigo)
		 JOIN LOS_PEORES.marca_productos ON marc_detalle = PRODUCTO_MARCA 
		 JOIN LOS_PEORES.modelo_productos ON str(mode_codigo)+str(mode_marca_producto) = str(PRODUCTO_MOD_CODIGO)+str(marc_codigo)
		 JOIN LOS_PEORES.producto ON prod_codigo+str(prod_modelo)+str(prod_subrubro) = PRODUCTO_CODIGO+str(mode_id)+str(subr_codigo)
		 JOIN LOS_PEORES.vendedor ON vend_cuit = VENDEDOR_CUIT
		 JOIN LOS_PEORES.almacen ON alma_codigo = ALMACEN_CODIGO
    WHERE PUBLICACION_DESCRIPCION IS NOT NULL AND PUBLICACION_FECHA IS NOT NULL AND PUBLICACION_FECHA_V IS NOT NULL AND PUBLICACION_STOCK IS NOT NULL AND PUBLICACION_PRECIO IS NOT NULL AND PUBLICACION_COSTO IS NOT NULL AND PUBLICACION_PORC_VENTA IS NOT NULL
END;
GO


CREATE PROC LOS_PEORES.migrar_deta_ventas AS
BEGIN
    INSERT INTO LOS_PEORES.deta_venta(deta_publicacion, deta_venta,deta_precio, deta_cantidad, deta_subtotal)
    SELECT DISTINCT PUBLICACION_CODIGO, VENTA_CODIGO,VENTA_DET_PRECIO, VENTA_DET_CANT, VENTA_DET_SUB_TOTAL
    FROM Maestra 
    WHERE VENTA_DET_CANT IS NOT NULL 
END
GO

CREATE PROC LOS_PEORES.migrar_items_facturas AS
BEGIN
    INSERT INTO LOS_PEORES.item_factura
    SELECT DISTINCT FACTURA_NUMERO, PUBLICACION_CODIGO, conc_codigo, FACTURA_DET_PRECIO, FACTURA_DET_CANTIDAD, FACTURA_DET_SUBTOTAL
FROM Maestra
JOIN LOS_PEORES.concepto ON conc_detalle = FACTURA_DET_TIPO
END
GO


-- INDICES

CREATE INDEX idx_localidad_loca_nombre ON LOS_PEORES.localidad (loca_nombre);
CREATE INDEX idx_localidad_loca_provincia ON LOS_PEORES.localidad (loca_provincia);

CREATE INDEX idx_provincia_prov_codigo ON LOS_PEORES.provincia (prov_codigo);

CREATE INDEX idx_usuario_usua_nombre ON LOS_PEORES.usuario (usua_nombre);
CREATE INDEX idx_usuario_usua_codigo ON LOS_PEORES.usuario (usua_codigo);


-- EJECUCIONES

EXEC LOS_PEORES.migrar_tipo_medio_pago;
EXEC LOS_PEORES.migrar_medio_pago;
EXEC LOS_PEORES.migrar_tipo_envio;
EXEC LOS_PEORES.migrar_concepto;
EXEC LOS_PEORES.migrar_marca_productos;
EXEC LOS_PEORES.migrar_modelo_productos;
EXEC LOS_PEORES.migracion_rubro;
EXEC LOS_PEORES.migracion_sub_rubro;
EXEC LOS_PEORES.migrar_usuarios;
EXEC LOS_PEORES.migrar_provincias;
EXEC LOS_PEORES.migrar_localidades;
EXEC LOS_PEORES.migrar_domicilios;
EXEC LOS_PEORES.migrar_vendedores;
EXEC LOS_PEORES.migrar_clientes;
EXEC LOS_PEORES.migrar_ventas;
EXEC LOS_PEORES.migrar_almacenes;
EXEC LOS_PEORES.migrar_producto;
EXEC LOS_PEORES.migrar_publicacion;
EXEC LOS_PEORES.migrar_facturas;
EXEC LOS_PEORES.migrar_pagos;
EXEC LOS_PEORES.migrar_envios;
EXEC LOS_PEORES.migrar_deta_ventas;
EXEC LOS_PEORES.migrar_items_facturas;