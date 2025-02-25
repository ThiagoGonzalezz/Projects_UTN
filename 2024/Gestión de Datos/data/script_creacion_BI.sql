USE GD2C2024
GO

------------------------------------------------------------------------------------------
-- TABLAS DIMENSIONES --------------------------------------------------------------------
------------------------------------------------------------------------------------------

CREATE TABLE LOS_PEORES.bi_tipo_medio_pago(
    id DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    descripcion NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.bi_tipo_envio(
    id DECIMAL(18,0) NOT NULL IDENTITY(1,1),
    descripcion NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE LOS_PEORES.bi_rango_etario_clientes(
	id decimal(18,0) not null identity(1,1),
	descripcion varchar(255) not null
)
GO

CREATE TABLE LOS_PEORES.bi_tiempo(
	id decimal(18,0) not null identity(1,1),
	anio decimal(18,0) not null,
	cuatrimestre decimal(18,0) not null,
	mes decimal (18,0) not null
)
GO

CREATE TABLE LOS_PEORES.bi_ubicacion (
	id decimal(18,0) not null identity(1,1),
	provincia_id decimal(18,0) not null,
	provincia_nombre varchar(255) null,
	localidad_id decimal(18,0) not null,
	localidad_nombre varchar(255) not null
)
GO

CREATE TABLE LOS_PEORES.bi_rubro_subrubro(
	id decimal(18,0) not null identity(1,1),
	rubr_descripcion varchar(255) not null,
	rubr_id decimal(18,0) not null,
	subr_descripcion varchar(255) not null,
	subr_id decimal(18,0) not null
)
GO

CREATE TABLE LOS_PEORES.bi_marca(
	id decimal(18,0) not null identity(1,1),
	descripcion varchar(255) not null
)
GO

CREATE TABLE LOS_PEORES.bi_tipo_pago(
	id decimal(18,0) not null identity(1,1),
	descripcion varchar(255) not null
)
GO

CREATE TABLE LOS_PEORES.bi_concepto(
	id decimal(18,0) not null identity(1,1),
	detalle varchar(255) not null
)
GO

------------------------------------------------------------------------------------------
-- TABLAS HECHOS -------------------------------------------------------------------------
------------------------------------------------------------------------------------------

CREATE TABLE LOS_PEORES.bi_ventas(
	bi_tiempo_id decimal(18,0) not null,
	bi_rango_etario_clientes_id decimal(18,0) not null,
	bi_ubicacion_id decimal(18,0) not null,
	bi_rubro_subrubro_id decimal(18,0) not null,
	total_ventas decimal(12,2) not null,
	cant_ventas decimal(18,0) not null
)
GO

CREATE TABLE LOS_PEORES.bi_facturas(
	bi_tiempo_id decimal(18,0) not null,
	bi_ubicacion_id decimal(18,0) not null,
	bi_concepto_id decimal(18,0) not null,
	total decimal(18,0) not null
)
GO

CREATE TABLE LOS_PEORES.bi_pagos(
	bi_tiempo_id decimal(18,0) not null,
	bi_tipo_medio_pago_id decimal(18,0) not null,
	bi_ubicacion_id decimal(18,0) not null,
	bi_tipo_pago_id decimal(18,0) not null,
	total_importes decimal(18,0) not null,
)
GO

CREATE TABLE LOS_PEORES.bi_envios(
   bi_tiempo_id decimal(18,0) not null,
   bi_ubicacion_almacen_id decimal(18,0) not null,
   bi_ubicacion_final_id decimal(18,0)not null,
   cantidad decimal(18,0) not null,
   cantidad_cumplidos decimal(18,0) not null,
   total_costo decimal(18,0) not null
)
GO

CREATE TABLE LOS_PEORES.bi_publicaciones(
	bi_tiempo_id decimal(18,0) not null,
	bi_rubro_subrubro_id decimal(18,0) not null,
	bi_marca_id decimal(18,0) not null,
	tiempo_promedio decimal(18,0) not null,
	cantidad decimal(18,0) not null,
	stock_promedio_actual decimal(18,0) not null
)
GO

------------------------------------------------------------------------------------------
-- CONSTRAINTS ---------------------------------------------------------------------------
------------------------------------------------------------------------------------------


ALTER TABLE LOS_PEORES.bi_tipo_medio_pago add constraint PK_bi_tipo_medio_pago primary key (id)
GO

ALTER TABLE LOS_PEORES.bi_tipo_envio add constraint PK_bi_tipo_envio primary key (id)
GO

ALTER TABLE LOS_PEORES.bi_rango_etario_clientes add constraint PK_bi_rango_etario_clientes primary key (id)
GO

ALTER TABLE LOS_PEORES.bi_tiempo add constraint PK_bi_tiempo primary key (id)
ALTER TABLE LOS_PEORES.bi_tiempo add constraint tiempo_irrepetible unique (anio,cuatrimestre,mes) 
GO

ALTER TABLE LOS_PEORES.bi_ubicacion add constraint PK_bi_ubicacion primary key (id)
GO

ALTER TABLE LOS_PEORES.bi_rubro_subrubro add constraint PK_bi_rubro_subrubro primary key (id) --
GO

ALTER TABLE LOS_PEORES.bi_concepto add constraint PK_bi_concepto primary key (id) 
GO

ALTER TABLE LOS_PEORES.bi_facturas add constraint PK_bi_facturas primary key (bi_tiempo_id, bi_ubicacion_id, bi_concepto_id)
ALTER TABLE LOS_PEORES.bi_facturas ADD CONSTRAINT FK_bi_facturas_tiempo FOREIGN KEY (bi_tiempo_id) REFERENCES LOS_PEORES.bi_tiempo(id);
ALTER TABLE LOS_PEORES.bi_facturas ADD CONSTRAINT FK_bi_facturas_ubicacion FOREIGN KEY (bi_ubicacion_id) REFERENCES LOS_PEORES.bi_ubicacion(id);
ALTER TABLE LOS_PEORES.bi_facturas ADD CONSTRAINT FK_bi_concepto_id FOREIGN KEY (bi_concepto_id) REFERENCES LOS_PEORES.bi_concepto(id);
GO

ALTER TABLE LOS_PEORES.bi_marca add constraint PK_bi_marca primary key (id)
GO

ALTER TABLE LOS_PEORES.bi_tipo_pago add constraint PK_bi_tipo_pago primary key (id)
GO

ALTER TABLE LOS_PEORES.bi_ventas add constraint PK_bi_ventas primary key (bi_tiempo_id, bi_rango_etario_clientes_id, bi_ubicacion_id, bi_rubro_subrubro_id)
ALTER TABLE LOS_PEORES.bi_ventas ADD CONSTRAINT FK_bi_ventas_tiempo FOREIGN KEY (bi_tiempo_id) REFERENCES LOS_PEORES.bi_tiempo(id);
ALTER TABLE LOS_PEORES.bi_ventas ADD CONSTRAINT FK_bi_rango_etario_clientes_id FOREIGN KEY (bi_rango_etario_clientes_id) REFERENCES LOS_PEORES.bi_rango_etario_clientes(id);
ALTER TABLE LOS_PEORES.bi_ventas ADD CONSTRAINT FK_bi_ventas_ubicacion FOREIGN KEY (bi_ubicacion_id) REFERENCES LOS_PEORES.bi_ubicacion(id);
ALTER TABLE LOS_PEORES.bi_ventas ADD CONSTRAINT FK_bi_ventas_subrubro FOREIGN KEY (bi_rubro_subrubro_id) REFERENCES LOS_PEORES.bi_rubro_subrubro(id);
GO

ALTER TABLE LOS_PEORES.bi_publicaciones add constraint PK_bi_publicaciones primary key (bi_tiempo_id, bi_rubro_subrubro_id, bi_marca_id)
ALTER TABLE LOS_PEORES.bi_publicaciones ADD CONSTRAINT FK_bi_publicaciones_tiempo FOREIGN KEY (bi_tiempo_id) REFERENCES LOS_PEORES.bi_tiempo(id);
ALTER TABLE LOS_PEORES.bi_publicaciones ADD CONSTRAINT FK_bi_publicaciones_rubro_subrubro FOREIGN KEY (bi_rubro_subrubro_id) REFERENCES LOS_PEORES.bi_rubro_subrubro(id);
ALTER TABLE LOS_PEORES.bi_publicaciones ADD CONSTRAINT FK_bi_publicaciones_marca FOREIGN KEY (bi_marca_id) REFERENCES LOS_PEORES.bi_marca(id);
GO

ALTER TABLE LOS_PEORES.bi_envios ADD CONSTRAINT PK_bi_envios primary key (bi_tiempo_id, bi_ubicacion_almacen_id, bi_ubicacion_final_id);
ALTER TABLE LOS_PEORES.bi_envios ADD CONSTRAINT FK_bi_envios_tiempo foreign key (bi_tiempo_id) REFERENCES LOS_PEORES.bi_tiempo(id);
ALTER TABLE LOS_PEORES.bi_envios ADD CONSTRAINT FK_bi_envios_ubi_ini foreign key (bi_ubicacion_almacen_id) REFERENCES LOS_PEORES.bi_ubicacion(id);
ALTER TABLE LOS_PEORES.bi_envios ADD CONSTRAINT FK_bi_envios_ub_fin foreign key (bi_ubicacion_final_id) REFERENCES LOS_PEORES.bi_ubicacion(id);
GO

CREATE FUNCTION LOS_PEORES.obtener_cuatrimestre (@mes INT)
RETURNS INT
BEGIN
	RETURN CASE
        WHEN @mes IN (1, 2, 3) THEN 1
        WHEN @mes IN (4, 5, 6) THEN 2
        WHEN @mes IN (7, 8, 9) THEN 3
        WHEN @mes IN (10, 11, 12) THEN 4
        END
END
GO


CREATE FUNCTION LOS_PEORES.obtener_rango_etario (@fecha_nac DATETIME)
RETURNS DECIMAL(18,0)
BEGIN
	DECLARE @id decimal(18,0)
	DECLARE @edad INT

	SET @edad  = YEAR(GETDATE()) - YEAR(@fecha_nac)

	IF @edad < 25
		SET @id = 1
	ELSE IF @EDAD BETWEEN  25 AND 35
		SET @id = 2
	ELSE IF @EDAD BETWEEN 35 AND 50
		SET @id = 3
	ELSE IF @EDAD > 50
		SET @id = 4

	RETURN @id
END
GO

CREATE FUNCTION LOS_PEORES.obtener_cuotas (@cant_cuotas DECIMAL(18,0))
RETURNS DECIMAL(18,0)
BEGIN
	DECLARE @id decimal(18,0)
	IF isnull(@cant_cuotas,1) > 1
		SET @id=2
	ELSE SET @id=1

	RETURN @id
END
GO

------------------------------------------------------------------------------------------
-- POPULACION DIMENSIONES ----------------------------------------------------------------
------------------------------------------------------------------------------------------


CREATE PROCEDURE LOS_PEORES.bi_migrar_ubicaciones
AS
BEGIN
	insert LOS_PEORES.bi_ubicacion(provincia_id, provincia_nombre, localidad_id, localidad_nombre)
	select prov_codigo, prov_nombre, loca_codigo, loca_nombre from LOS_PEORES.provincia p 
	join LOS_PEORES.localidad ON loca_provincia = prov_codigo 
END
GO


CREATE PROCEDURE LOS_PEORES.bi_migrar_tiempos
AS
BEGIN
	INSERT INTO LOS_PEORES.bi_tiempo(anio, cuatrimestre, mes)
	SELECT DISTINCT
		YEAR(v.vent_fecha_hora) as tiempo_anio,
		LOS_PEORES.obtener_cuatrimestre(MONTH(v.vent_fecha_hora)) as tiempo_cuatrimestre,
		MONTH(v.vent_fecha_hora) as tiempo_mes
	FROM LOS_PEORES.venta v
	UNION
	SELECT DISTINCT
		YEAR(p.pago_fecha) as tiempo_anio,
		LOS_PEORES.obtener_cuatrimestre(MONTH(p.pago_fecha)) as tiempo_cuatrimestre,
		MONTH(p.pago_fecha) as tiempo_mes
	FROM LOS_PEORES.pago p
	UNION
	SELECT DISTINCT
		YEAR(pu.publ_fecha_inicio) as tiempo_anio,
		LOS_PEORES.obtener_cuatrimestre(MONTH(pu.publ_fecha_inicio)) as tiempo_cuatrimestre,
		MONTH(pu.publ_fecha_inicio) as tiempo_mes
	FROM LOS_PEORES.publicacion pu
	UNION
	SELECT DISTINCT
		YEAR(e.envi_fecha_hora_entrega) as tiempo_anio,
		LOS_PEORES.obtener_cuatrimestre(MONTH(e.envi_fecha_hora_entrega)) as tiempo_cuatrimestre,
		MONTH(e.envi_fecha_hora_entrega) as tiempo_mes
	FROM LOS_PEORES.envio e
	UNION
	SELECT DISTINCT
		YEAR(f.fact_fecha) as tiempo_anio,
		LOS_PEORES.obtener_cuatrimestre(MONTH(f.fact_fecha)) as tiempo_cuatrimestre,
		MONTH(f.fact_fecha) as tiempo_mes
	FROM LOS_PEORES.factura f
END
GO

CREATE PROCEDURE LOS_PEORES.bi_migrar_rango_etario
AS
BEGIN
	insert into LOS_PEORES.bi_rango_etario_clientes(descripcion)
    values ('<25'),('25-35'), ('35-50'),('>50')
END
GO



CREATE PROCEDURE LOS_PEORES.bi_migrar_marcas
AS
BEGIN
	insert LOS_PEORES.bi_marca(descripcion)
	select m.marc_detalle from LOS_PEORES.marca_productos m
END
GO


CREATE PROCEDURE LOS_PEORES.bi_migrar_tipo_medio_pago
AS
BEGIN
	insert LOS_PEORES.bi_tipo_medio_pago(descripcion)
	select tipomed_detalle from LOS_PEORES.tipo_medio_pago
END
GO

CREATE PROCEDURE LOS_PEORES.bi_migrar_tipo_pago
AS
BEGIN
	insert LOS_PEORES.bi_tipo_pago(descripcion)
	values('Pago Unico'),
			('Pago en Cuotas')
END
GO


CREATE PROCEDURE LOS_PEORES.bi_migrar_tipo_envio
AS
BEGIN
	insert LOS_PEORES.bi_tipo_envio(descripcion)
	select tipoenv_descripcion from LOS_PEORES.tipo_envio
END
GO

CREATE PROCEDURE LOS_PEORES.bi_migrar_conceptos
AS
BEGIN
	insert LOS_PEORES.bi_concepto
	select c.conc_detalle from LOS_PEORES.concepto c
END
GO


CREATE PROCEDURE LOS_PEORES.bi_migrar_rubro_subrubro
AS
BEGIN
    insert into LOS_PEORES.bi_rubro_subrubro(rubr_id, rubr_descripcion, subr_id, subr_descripcion)
    SELECT r.rubr_codigo, r.rubr_detalle, s.subr_codigo, s.subr_detalle
    FROM LOS_PEORES.rubro r JOIN LOS_PEORES.sub_rubro s on r.rubr_codigo=s.subr_padre
END
GO

------------------------------------------------------------------------------------------
-- POPULACION HECHOS ---------------------------------------------------------------------
------------------------------------------------------------------------------------------

CREATE PROCEDURE LOS_PEORES.bi_migrar_facturas
AS
BEGIN
	insert LOS_PEORES.bi_facturas (bi_tiempo_id, bi_ubicacion_id, bi_concepto_id, total)
	select t.id, ubi.id as ubicacion, conc.id as concepto, sum(fact_total) as total 
	from LOS_PEORES.factura f
	join LOS_PEORES.bi_tiempo t on t.anio = YEAR(f.fact_fecha) and t.cuatrimestre = LOS_PEORES.obtener_cuatrimestre(MONTH(f.fact_fecha)) and t.mes = MONTH(f.fact_fecha)
	join LOS_PEORES.vendedor v on v.vend_codigo = f.fact_vendedor
	join LOS_PEORES.usuario u on u.usua_codigo = v.vend_usuario 
	join LOS_PEORES.domicilio d on d.domi_usuario = u.usua_codigo
	join LOS_PEORES.localidad l on l.loca_codigo = domi_localidad
	join LOS_PEORES.bi_ubicacion ubi on ubi.localidad_id = domi_localidad and ubi.provincia_id = loca_provincia
	join LOS_PEORES.item_factura itf on f.fact_numero = itf.item_fact_numero 
	join LOS_PEORES.bi_concepto conc on conc.id = itf.item_fact_concepto  
	group by t.id, ubi.id, conc.id

END
GO

CREATE PROCEDURE LOS_PEORES.bi_migrar_ventas
AS
BEGIN
    insert LOS_PEORES.bi_ventas(bi_tiempo_id, bi_rango_etario_clientes_id, bi_ubicacion_id, bi_rubro_subrubro_id, total_ventas, cant_ventas)

    select tiempo.id as tiempo_id, rangoEtario.id as rango_etario_id
            , ubi.id as ubicacion_id, 
            rub.id as rub_sub_id,
            SUM(venta.vent_total), COUNT(distinct venta.vent_codigo)

    from LOS_PEORES.venta venta
        JOIN LOS_PEORES.bi_tiempo tiempo on tiempo.anio=YEAR(venta.vent_fecha_hora) and tiempo.cuatrimestre=LOS_PEORES.obtener_cuatrimestre(MONTH(venta.vent_fecha_hora)) and tiempo.mes=MONTH(venta.vent_fecha_hora)
        JOIN LOS_PEORES.cliente cli on venta.vent_cliente=cli.clie_codigo JOIN LOS_PEORES.bi_rango_etario_clientes rangoEtario on rangoEtario.id= LOS_PEORES.obtener_rango_etario(cli.clie_fecha_nac)
        JOIN LOS_PEORES.deta_venta detalle on detalle.deta_venta=venta.vent_codigo JOIN LOS_PEORES.publicacion publicacion on 
            publicacion.publ_codigo=detalle.deta_publicacion JOIN LOS_PEORES.almacen a on a.alma_codigo=publicacion.publ_almacen
             JOIN LOS_PEORES.localidad l on l.loca_codigo=a.alma_localidad JOIN LOS_PEORES.bi_ubicacion ubi on ubi.localidad_id=l.loca_codigo and ubi.provincia_id=l.loca_provincia
        join LOS_PEORES.producto prod on prod.prod_id = publicacion.publ_prod
        join LOS_PEORES.sub_rubro subrub on subrub.subr_codigo = prod.prod_subrubro 

        join LOS_PEORES.bi_rubro_subrubro rub on rub.subr_id = prod.prod_subrubro

    GROUP BY tiempo.id, rangoEtario.id, ubi.id, rub.id
END
GO

CREATE PROCEDURE LOS_PEORES.bi_migrar_pagos
AS
BEGIN
	INSERT INTO LOS_PEORES.bi_pagos(bi_tiempo_id, bi_tipo_medio_pago_id, bi_ubicacion_id, bi_tipo_pago_id, total_importes)

	SELECT tiempo.id as tiempo_id, medio_pago.tipomed_codigo as tipo_medio_pago_id, ubi.id as ubicacion_id, LOS_PEORES.obtener_cuotas(pagos.pago_cuotas) tipo_pago_id, SUM(pagos.pago_importe)

	FROM LOS_PEORES.pago pagos JOIN LOS_PEORES.bi_tiempo tiempo on tiempo.anio=YEAR(pagos.pago_fecha) and tiempo.cuatrimestre=LOS_PEORES.obtener_cuatrimestre(MONTH(pagos.pago_fecha))
			 and tiempo.mes=MONTH(pagos.pago_fecha)
			JOIN LOS_PEORES.tipo_medio_pago medio_pago on medio_pago.tipomed_codigo=pagos.pago_medio
			JOIN LOS_PEORES.venta venta on pagos.pago_venta=venta.vent_codigo join LOS_PEORES.envio envio on envio.envi_venta=venta.vent_codigo
			JOIN LOS_PEORES.domicilio domi on domi.domi_codigo=envio.envi_domicilio join LOS_PEORES.localidad localidad on localidad.loca_codigo=domi.domi_localidad
			JOIN LOS_PEORES.bi_ubicacion ubi on ubi.localidad_id=localidad.loca_codigo and ubi.provincia_id=localidad.loca_provincia

	GROUP BY tiempo.id, medio_pago.tipomed_codigo, ubi.id, pagos.pago_cuotas
END
GO 

create procedure LOS_PEORES.bi_migrar_envios
AS
BEGIN
     insert LOS_PEORES.bi_envios(bi_tiempo_id, bi_ubicacion_almacen_id, bi_ubicacion_final_id, cantidad, cantidad_cumplidos, total_costo)

	 select t.id, u.id , uf.id, count(*) AS cantiad, COUNT(CASE WHEN year(e.envi_fecha_hora_entrega) = year(e.envi_fecha_programada) and month(e.envi_fecha_hora_entrega) = month(e.envi_fecha_programada) THEN 1 ELSE 0  END) as cantidad_cumplidos ,sum(e.envi_costo)
      from LOS_PEORES.envio e

     join LOS_PEORES.venta v on e.envi_venta = v.vent_codigo

     join LOS_PEORES.bi_tiempo t on t.anio = year(vent_fecha_hora) and t.mes = month(vent_fecha_hora)

     join LOS_PEORES.deta_venta dv on dv.deta_venta = v.vent_codigo

     join LOS_PEORES.publicacion p on dv.deta_publicacion = p.publ_codigo

     join LOS_PEORES.almacen a on p.publ_almacen = a.alma_codigo

     join LOS_PEORES.localidad l on l.loca_codigo = a.alma_localidad

     join LOS_PEORES.bi_ubicacion u on u.localidad_id = l.loca_codigo

	 join LOS_PEORES.domicilio d2 on d2.domi_codigo = e.envi_domicilio

     join LOS_PEORES.localidad lf on lf.loca_codigo = d2.domi_localidad

     join LOS_PEORES.bi_ubicacion uf on uf.localidad_id =  lf.loca_codigo

     group by t.id, u.id, uf.id

END
GO


CREATE PROCEDURE LOS_PEORES.bi_migrar_publicaciones
AS
BEGIN
	insert LOS_PEORES.bi_publicaciones(bi_tiempo_id, bi_rubro_subrubro_id, bi_marca_id, tiempo_promedio, cantidad, stock_promedio_actual)
	select t.id, rub.id, marca.id ,avg(DATEDIFF(DAY, pub.publ_fecha_inicio, pub.publ_fecha_fin)) as tiempo_promedio, count(pub.publ_codigo) as cantidad, avg(pub.publ_stock)
	from LOS_PEORES.publicacion pub
	join LOS_PEORES.bi_tiempo t on t.anio = YEAR(pub.publ_fecha_inicio) and t.cuatrimestre = LOS_PEORES.obtener_cuatrimestre(MONTH(pub.publ_fecha_inicio)) and t.mes = MONTH(pub.publ_fecha_inicio)
	join LOS_PEORES.producto prod on prod.prod_id = pub.publ_prod
	join LOS_PEORES.sub_rubro subrub on subrub.subr_codigo = prod.prod_subrubro
	join LOS_PEORES.bi_rubro_subrubro rub on rub.subr_id = prod.prod_subrubro and subrub.subr_padre = rubr_id 
	join LOS_PEORES.modelo_productos mode on mode.mode_id = prod_modelo
	join LOS_PEORES.bi_marca marca on marca.id = mode.mode_marca_producto
	group by t.id, rub.id, marca.id
END
GO


------------------------------------------------------------------------------------------
-- VISTAS --------------------------------------------------------------------------------
------------------------------------------------------------------------------------------

CREATE VIEW LOS_PEORES.vista1
AS
SELECT 
    tiempo.anio as año,
    tiempo.cuatrimestre as cuatrimestre,
    rubsubrub.subr_descripcion,
    SUM(publicaciones.tiempo_promedio * publicaciones.cantidad) /SUM(publicaciones.cantidad) as tiempo_promedio
FROM LOS_PEORES.bi_publicaciones publicaciones
    JOIN LOS_PEORES.bi_tiempo tiempo on tiempo.id = publicaciones.bi_tiempo_id
    JOIN LOS_PEORES.bi_rubro_subrubro rubsubrub on rubsubrub.id = publicaciones.bi_rubro_subrubro_id
    GROUP BY tiempo.anio, tiempo.cuatrimestre, rubsubrub.subr_id, rubsubrub.subr_descripcion
GO

CREATE VIEW LOS_PEORES.vista2 AS 

SELECT tiempo.anio AS año,
		marca.id AS marca_id,
		marca.descripcion AS marca_descripcion,
		SUM(publi.stock_promedio_actual*cantidad)/SUM(cantidad) as promedio_stock_inicial
FROM LOS_PEORES.bi_publicaciones AS publi
JOIN LOS_PEORES.bi_tiempo AS tiempo ON tiempo.id = publi.bi_tiempo_id
JOIN LOS_PEORES.bi_marca AS marca ON marca.id = publi.bi_marca_id
GROUP BY marca.id, marca.descripcion, anio  
GO

CREATE VIEW LOS_PEORES.vista3 AS 

SELECT tiempo.anio AS anio, 
		tiempo.mes AS mes, 
		ubi.provincia_id AS prov_id, 
		ubi.provincia_nombre AS prov_nombre, 
		SUM(vent.total_ventas) / SUM(vent.cant_ventas) AS venta_promedio_mensual
FROM LOS_PEORES.bi_ventas AS vent
JOIN LOS_PEORES.bi_tiempo AS tiempo ON tiempo.id = vent.bi_tiempo_id
JOIN LOS_PEORES.bi_ubicacion AS ubi ON ubi.id = vent.bi_ubicacion_id
GROUP BY tiempo.anio, tiempo.mes, ubi.provincia_id, ubi.provincia_nombre
GO

CREATE VIEW LOS_PEORES.vista4 AS

SELECT rubr_descripcion,tiempo.anio, tiempo.cuatrimestre, ubi.localidad_nombre, re.descripcion as rango_etario

FROM LOS_PEORES.bi_ventas ventas 
	JOIN LOS_PEORES.bi_tiempo tiempo on tiempo.id=ventas.bi_tiempo_id
    JOIN LOS_PEORES.bi_ubicacion ubi ON ventas.bi_ubicacion_id=ubi.id 
    JOIN LOS_PEORES.bi_rango_etario_clientes re ON ventas.bi_rango_etario_clientes_id = re.id
    JOIN LOS_PEORES.bi_rubro_subrubro rubs ON ventas.bi_rubro_subrubro_id=rubs.id

 WHERE rubs.id IN (
        SELECT TOP 5 v2.bi_rubro_subrubro_id
        FROM LOS_PEORES.bi_ventas v2 join LOS_PEORES.bi_tiempo tiempo2 on tiempo.cuatrimestre=tiempo2.cuatrimestre and tiempo.anio=tiempo2.anio
        WHERE v2.bi_rango_etario_clientes_id=ventas.bi_rango_etario_clientes_id
            and v2.bi_ubicacion_id=ventas.bi_ubicacion_id
        GROUP BY v2.bi_rubro_subrubro_id
        ORDER BY SUM(v2.total_ventas) desc
    )

GROUP BY rubr_descripcion, tiempo.anio, tiempo.cuatrimestre, ubi.localidad_nombre, re.descripcion
GO


CREATE VIEW LOS_PEORES.vista6 AS

SELECT TOP 3 ubi.localidad_nombre, medio.descripcion, tiempo.anio, tiempo.mes
 
FROM LOS_PEORES.bi_pagos pago 
	JOIN LOS_PEORES.bi_tipo_medio_pago medio on pago.bi_tipo_medio_pago_id=medio.id
	JOIN LOS_PEORES.bi_tiempo tiempo on tiempo.id=pago.bi_tiempo_id
	JOIN LOS_PEORES.bi_ubicacion ubi on ubi.id=pago.bi_ubicacion_id

WHERE pago.bi_tipo_pago_id=2 
GROUP BY ubi.localidad_nombre, medio.descripcion, tiempo.anio, tiempo.mes
ORDER BY SUM(pago.total_importes) DESC

GO

CREATE VIEW LOS_PEORES.vista7 AS 

SELECT ubi.provincia_id, t.anio, t.mes, (sum(e.cantidad_cumplidos)/sum(e.cantidad))*100 as porcentaje
from LOS_PEORES.bi_envios e 
join LOS_PEORES.bi_ubicacion ubi on ubi.id = e.bi_ubicacion_almacen_id
join LOS_PEORES.bi_tiempo t on t.id = e.bi_tiempo_id
group by ubi.provincia_id, t.anio, t.mes

GO


CREATE VIEW LOS_PEORES.vista8 AS 

SELECT TOP 5 ubi.provincia_id AS prov_id, ubi.provincia_nombre AS prov_nombre
FROM LOS_PEORES.bi_envios AS env
JOIN LOS_PEORES.bi_ubicacion AS ubi ON ubi.id = env.bi_ubicacion_final_id
GROUP BY ubi.provincia_id, ubi.provincia_nombre
ORDER BY SUM(env.total_costo) DESC
GO



CREATE VIEW LOS_PEORES.vista9
AS
SELECT tiempo.anio AS año,
		tiempo.mes AS mes, 
		concepto.id AS id_concepto, 
		concepto.detalle AS detalle_concepto, 
		ISNULL((SUM(fact.total) / (SELECT SUM(total) 
							FROM LOS_PEORES.bi_facturas AS fact 
							WHERE fact.bi_tiempo_id = tiempo.id)),0) 
		* 100 AS porcentaje_total
FROM LOS_PEORES.bi_tiempo AS tiempo
LEFT JOIN LOS_PEORES.bi_facturas AS fact ON fact.bi_tiempo_id = tiempo.id
LEFT JOIN LOS_PEORES.bi_concepto AS concepto ON concepto.id = fact.bi_concepto_id
GROUP BY tiempo.id, tiempo.anio, tiempo.mes, concepto.id, concepto.detalle
GO

CREATE VIEW LOS_PEORES.vista10
AS
SELECT 
	tiempo.anio as año,
	tiempo.cuatrimestre as cuatrimestre,
	ubicacion.provincia_nombre as provincia,
	SUM(factura.total) as monto_facturado
FROM LOS_PEORES.bi_facturas factura
	JOIN LOS_PEORES.bi_tiempo tiempo on tiempo.id = factura.bi_tiempo_id
	JOIN LOS_PEORES.bi_ubicacion ubicacion on ubicacion.id = factura.bi_ubicacion_id
	JOIN LOS_PEORES.bi_concepto concepto on concepto.id = factura.bi_concepto_id
	GROUP BY tiempo.anio, tiempo.cuatrimestre, ubicacion.provincia_nombre
GO


------------------------------------------------------------------------------------------
-- EJECUCIONES ---------------------------------------------------------------------------
------------------------------------------------------------------------------------------


EXEC LOS_PEORES.bi_migrar_rango_etario;
EXEC LOS_PEORES.bi_migrar_marcas;
EXEC LOS_PEORES.bi_migrar_tipo_medio_pago;
EXEC LOS_PEORES.bi_migrar_tipo_pago;
EXEC LOS_PEORES.bi_migrar_tipo_envio;
EXEC LOS_PEORES.bi_migrar_conceptos;
EXEC LOS_PEORES.bi_migrar_rubro_subrubro;
EXEC LOS_PEORES.bi_migrar_ubicaciones;
EXEC LOS_PEORES.bi_migrar_tiempos;

EXEC LOS_PEORES.bi_migrar_facturas;
EXEC LOS_PEORES.bi_migrar_ventas;
EXEC LOS_PEORES.bi_migrar_pagos;
EXEC LOS_PEORES.bi_migrar_envios;
EXEC LOS_PEORES.bi_migrar_publicaciones;


/*
DROP TABLE LOS_PEORES.bi_pagos;
DROP TABLE LOS_PEORES.bi_facturas;
DROP TABLE LOS_PEORES.bi_ventas;
DROP TABLE LOS_PEORES.bi_envios;
DROP TABLE LOS_PEORES.bi_publicaciones;

DROP TABLE LOS_PEORES.bi_rubro_subrubro;
DROP TABLE LOS_PEORES.bi_ubicacion; 
DROP TABLE LOS_PEORES.bi_tiempo; 
DROP TABLE LOS_PEORES.bi_rango_etario_clientes; 
DROP TABLE LOS_PEORES.bi_concepto; 
DROP TABLE LOS_PEORES.bi_tipo_pago; 
DROP TABLE LOS_PEORES.bi_marca; 
DROP TABLE LOS_PEORES.bi_tipo_envio; 
DROP TABLE LOS_PEORES.bi_tipo_medio_pago;    


DROP PROCEDURE LOS_PEORES.bi_migrar_marcas;
DROP PROCEDURE LOS_PEORES.bi_migrar_tipo_medio_pago;
DROP PROCEDURE LOS_PEORES.bi_migrar_tipo_pago;
DROP PROCEDURE LOS_PEORES.bi_migrar_tipo_envio;
DROP PROCEDURE LOS_PEORES.bi_migrar_conceptos;
DROP PROCEDURE LOS_PEORES.bi_migrar_rubro_subrubro;
DROP PROCEDURE LOS_PEORES.bi_migrar_tiempos;
DROP PROCEDURE LOS_PEORES.bi_migrar_ubicaciones;
DROP PROCEDURE LOS_PEORES.bi_migrar_rango_etario;

DROP PROCEDURE LOS_PEORES.bi_migrar_facturas;
DROP PROCEDURE LOS_PEORES.bi_migrar_ventas;
DROP PROCEDURE LOS_PEORES.bi_migrar_pagos;
DROP PROCEDURE LOS_PEORES.bi_migrar_envios;
DROP PROCEDURE LOS_PEORES.bi_migrar_publicaciones;


DROP FUNCTION  LOS_PEORES.obtener_cuatrimestre;
DROP FUNCTION  LOS_PEORES.obtener_rango_etario;
DROP FUNCTION  LOS_PEORES.obtener_cuotas;

DROP VIEW LOS_PEORES.vista1
DROP VIEW LOS_PEORES.vista2 
DROP VIEW LOS_PEORES.vista3
DROP VIEW LOS_PEORES.vista4 
DROP VIEW LOS_PEORES.vista6 
DROP VIEW LOS_PEORES.vista7
DROP VIEW LOS_PEORES.vista8 
DROP VIEW LOS_PEORES.vista9
DROP VIEW LOS_PEORES.vista10 


GO*/