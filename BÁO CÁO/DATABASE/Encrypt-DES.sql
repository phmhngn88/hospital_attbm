
CREATE TABLE hospital.BENHNHAN
(
    MABN NUMBER,
    HOTEN RAW(400),
    NGAYSINH RAW(80),
    DIACHI RAW(80),
    SODT RAW(30),
    PRIMARY KEY(MABN)
);

ALTER TABLE BENHNHAN
MODIFY (HOTEN RAW(400),
    NGAYSINH RAW(80),
    DIACHI RAW(80),
    SODT RAW(30));

CREATE OR REPLACE PROCEDURE HOSPITAL_DBA.INSERT_BENHNHAN(
    MABN IN NUMBER,
    HOTEN IN NVARCHAR2,
    NGAYSINH IN VARCHAR2,
    DIACHI IN VARCHAR2,
    SODT IN VARCHAR2
) AS 
BEGIN
DECLARE
    raw_TABLE      RAW (400) := 
   UTL_RAW.CAST_TO_RAW(CONVERT('BENHNHAN' ,'AL32UTF8','US7ASCII'));
   raw_MABN      RAW (2000) :=
   UTL_RAW.CAST_FROM_NUMBER(MABN);
   raw_HOTEN      RAW (400) :=
   UTL_RAW.CAST_TO_RAW(CONVERT(HOTEN ,'AL32UTF8','US7ASCII'));
   raw_NGAYSINH      RAW (50) := 
   UTL_RAW.CAST_TO_RAW(CONVERT(NGAYSINH,'AL32UTF8','US7ASCII'));
   raw_DIACHI      RAW (50) :=
   UTL_RAW.CAST_TO_RAW(CONVERT(DIACHI,'AL32UTF8','US7ASCII'));
   raw_SODT      RAW (30) :=
   UTL_RAW.CAST_TO_RAW(CONVERT(SODT ,'AL32UTF8','US7ASCII'));
   num_key_bytes      NUMBER := 128/8;        -- key length 128 bits (16 bytes)
   salt_4insert RAW (16);
   key_bytes_raw      RAW (16);               -- stores 128-bit encryption key
   --encryption_type    PLS_INTEGER :=          -- total encryption type
                            --DBMS_CRYPTO.ENCRYPT_AES128 
                        --+ DBMS_CRYPTO.CHAIN_CBC
                          --+ DBMS_CRYPTO.PAD_PKCS5;
    BEGIN
        salt_4insert := DBMS_CRYPTO.RANDOMBYTES (num_key_bytes);
        key_bytes_raw := 
   UTL_RAW.BIT_XOR (
   UTL_RAW.BIT_XOR(UTL_RAW.BIT_XOR(raw_MABN, raw_TABLE), raw_HOTEN), salt_4insert);
    dbms_output.put_line('raw key :' || key_bytes_raw);
        raw_HOTEN := DBMS_CRYPTO.ENCRYPT
        (
            src => raw_HOTEN,
            typ => DBMS_CRYPTO.DES_CBC_PKCS5,
            key => key_bytes_raw
        );
        raw_NGAYSINH := DBMS_CRYPTO.ENCRYPT
      (
         src => raw_NGAYSINH,
         typ => DBMS_CRYPTO.DES_CBC_PKCS5,
         key => key_bytes_raw
      );
      raw_DIACHI := DBMS_CRYPTO.ENCRYPT
      (
         src => raw_DIACHI,
         typ => DBMS_CRYPTO.DES_CBC_PKCS5,
         key => key_bytes_raw
      );
      
       raw_SODT := DBMS_CRYPTO.ENCRYPT
       (
            src => raw_SODT,
            typ => DBMS_CRYPTO.DES_CBC_PKCS5,
            key => key_bytes_raw
       );
       --hiding key in data
       raw_HOTEN := UTL_RAW.CONCAT(UTL_RAW.SUBSTR(key_bytes_raw, 1, 4), raw_HOTEN);
       dbms_output.put_line('raw ht: ' || raw_HOTEN );
       raw_NGAYSINH := UTL_RAW.CONCAT(UTL_RAW.SUBSTR(key_bytes_raw, 5, 4), raw_NGAYSINH);
       dbms_output.put_line('raw ns: ' || raw_NGAYSINH );
       raw_DIACHI := UTL_RAW.CONCAT(UTL_RAW.SUBSTR(key_bytes_raw, 9, 4), raw_DIACHI);
       dbms_output.put_line('raw dc: ' || raw_DIACHI );
       raw_SODT := UTL_RAW.CONCAT(UTL_RAW.SUBSTR(key_bytes_raw, 13, 4), raw_SODT);
       dbms_output.put_line('raw sdt: ' || raw_SODT);
      INSERT INTO HOSPITAL_DBA.BENHNHAN
        (MABN, HOTEN, NGAYSINH, DIACHI, SODT)
        VALUES
        (MABN, raw_HOTEN, raw_NGAYSINH, raw_DIACHI, raw_SODT);
        COMMIT;
        Dbms_Output.Put_Line('inserted');
END;
END INSERT_BENHNHAN;

SELECT * FROM BENHNHAN;
DELETE FROM BENHNHAN;
DROP TABLE BENHNHAN;

INSERT INTO HOSPITAL_DBA.HOSOBENHNHAN (MAKB, NGAYKB, MABS, MABN, TINHTRANGBANDAU,
KETLUANCUABS) VALUES (1, TO_DATE('2021-06-06 15:43:01', 'YYYY-MM-DD HH24:MI:SS'), 'NV005', 2, N'khoe manh', N'vui ve yeu doi');

INSERT INTO HOSOBENHNHAN (MAKB, NGAYKB, MABS, MABN, TINHTRANGBANDAU,
KETLUANCUABS) VALUES (2, TO_DATE('2021-06-06 15:44:25', 'YYYY-MM-DD HH24:MI:SS'), 'NV005', 2, N'dau om', N'sot ');

INSERT INTO HOSOBENHNHAN (MAKB, NGAYKB, MABS, MABN, TINHTRANGBANDAU,
KETLUANCUABS) VALUES (3, TO_DATE('2021-06-06 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'NV005', 3, N'metmo i', N'HIV');

DELETE FROM HOSOBENHNHAN;
SELECT * FROM HOSOBENHNHAN;

CREATE OR REPLACE PROCEDURE PROCEDURE_45
(
  PARAM1 IN VARCHAR2  --'NO PARAM'
, PARAM2 IN NUMBER --makb
, PARAM3 IN VARCHAR2 --'NO PARAM'
) AS 
BEGIN
  DECLARE
    v_Bn BENHNHAN%Rowtype;
    key_bytes_raw      RAW (16);
    bn_ID NUMBER;
    raw_HOTEN      RAW (400);
    raw_NGAYSINH      RAW (50);
    raw_DIACHI      RAW (50);
    raw_SODT      RAW (30);
    BEGIN
        IF PARAM1 = 'NO PARAM' AND PARAM3 = 'NO PARAM' THEN
            SELECT MABN INTO bn_ID FROM HOSOBENHNHAN WHERE MAKB = PARAM2;
            --dbms_output.put_line('MAKB: ' || bn_ID);
            SELECT * INTO v_Bn FROM BENHNHAN BN WHERE BN.MABN = bn_ID;
            --dbms_output.put_line(UTL_RAW.SUBSTR(v_Bn.HOTEN, 1, 4));
            key_bytes_raw :=  UTL_RAW.CONCAT(UTL_RAW.SUBSTR(v_Bn.HOTEN, 1, 4),
                UTL_RAW.SUBSTR(v_Bn.NGAYSINH, 1, 4), UTL_RAW.SUBSTR(v_Bn.DIACHI, 1, 4),
                UTL_RAW.SUBSTR(v_Bn.SODT, 1, 4));
            --dbms_output.put_line('key: ' || key_bytes_raw);
             raw_HOTEN  := UTL_RAW.SUBSTR(v_Bn.HOTEN, 5);
             raw_HOTEN := dbms_crypto.Decrypt(
             src => RAWTOHEX(raw_HOTEN),
             typ => DBMS_CRYPTO.DES_CBC_PKCS5, 
             key => key_bytes_raw);
             
             raw_NGAYSINH  := UTL_RAW.SUBSTR(v_Bn.NGAYSINH, 5);
             raw_NGAYSINH := dbms_crypto.Decrypt(
             src => RAWTOHEX(raw_NGAYSINH),
             typ => DBMS_CRYPTO.DES_CBC_PKCS5, 
             key => key_bytes_raw);
             
             raw_DIACHI  := UTL_RAW.SUBSTR(v_Bn.DIACHI, 5);
             raw_DIACHI := dbms_crypto.Decrypt(
             src => RAWTOHEX(raw_DIACHI),
             typ => DBMS_CRYPTO.DES_CBC_PKCS5, 
             key => key_bytes_raw);
             
             raw_SODT  := UTL_RAW.SUBSTR(v_Bn.SODT, 5);
             raw_SODT := dbms_crypto.Decrypt(
             src => RAWTOHEX(raw_SODT),
             typ => DBMS_CRYPTO.DES_CBC_PKCS5, 
             key => key_bytes_raw);
             
            dbms_output.put_line(bn_ID || ' ' || CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_HOTEN),'US7ASCII','AL32UTF8') ||
            '   ' || CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_NGAYSINH),'US7ASCII','AL32UTF8') || 
            '   ' || CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_DIACHI),'US7ASCII','AL32UTF8') ||
            '   ' || CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_SODT),'US7ASCII','AL32UTF8'));
        ELSE
            dbms_output.put_line('No data found');
        End If;
    END;
END PROCEDURE_45;


