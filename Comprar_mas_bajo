create or replace PROCEDURE Precio_menor()
 AS
 --cursor para recoger las cantidades, y los codigos de los productos de mi empresa
 CURSOR V_NUM_PROD IS
   SELECT V_COD_PROD, V_CANT
       FROM salvarez_prueba.TSIM_PROD_X_EMPRESA
       WHERE CDEMPRESA=V_COD_MY_EMP;

--Codigo de la empresa del profe
V_COD_EMPRESA SALVAREZ_PRUEBA.TSIM_PROD_X_EMPRESA.CDEMPRESA%TYPE :='00001';
--cantida que voy a comprar simpre
V_CANT SALVAREZ_PRUEBA.TSIM_PROD_X_EMPRESA.NMCANTIDAD%TYPE;
--Mensaje de error
V_ERROR VARCHAR2(5);
--Variable para el precio
V_PRECIO SALVAREZ_PRUEBA.TSIM_PROD_X_EMPRESA.CDEMPRESA%TYPE;
--Variable para el codigo de mi empresa
V_COD_MY_EMP SALVAREZ_PRUEBA.TSIM_PROD_X_EMPRESA.CDEMPRESA%TYPE := '21';
-- codigo de los productos
V_COD_PROD SALVAREZ_PRUEBA.TSIM_PROD_X_EMPRESA.CDPRODUCTO;


BEGIN
  OPEN V_NUM_PROD;
      LOOP
        FETCH V_NUM_PROD INTO V_COD_PROD,V_CANT;
        EXIT WHEN V_NUM_PROD%NOTFOUND;
        SELECT CDEMPRESA,PSVENTA into V_COD_EMPRESA,V_PRECIO
          FROM salvarez_prueba.TSIM_PROD_X_EMPRESA
          WHERE PSVENTA = (SELECT MIN(PSVENTA) FROM  salvarez_prueba.TSIM_PROD_X_EMPRESA WHERE CDPRODUCTO = V_COD_PROD AND CDEMPRESA <> '21' AND NMCANTIDAD>= 5 and ROWNUM =1)
          AND V_COD_PROD = V_COD_PROD ;
          SALVAREZ_PRUEBA.SP_COMPRAR_PROD(V_COD_EMPRESA, V_COD_PROD,2,V_ERROR);
        IF(V_CANT>=5) THEN
          V_PRECIO := V_PRECIO * 1.10;
        ELSE
          V_PRECIO := V_PRECIO * 1.05;
        END IF;
        SALVAREZ_PRUEBA.SP_CAMBIO_VALOR_PROD(V_CDPRODUCTO,V_PRECIO,V_ERROR);
    END LOOP;
  CLOSE V_NUM_PROD;

EXCEPTION

  WHEN NO_DATA_FOUND THEN
    dbms_output.put_line('El cursor no encuentra valores');

  when TOO_MANY_ROWS THEN
    dbms_output.put_line('EL cursor devuelve muchas mas filas. Maloo');

  WHEN OTHERS THEN
    err_num:= SQLCODE;
    err_msg:=SQLERRM;
    dbms_output.put_line('El error es el numero: ' || TO_CHAR(err_num) || ' El mensaje es: ' || err_msg);


END Precio_menor;
