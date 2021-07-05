
CREATE OR REPLACE PROCEDURE hospital.INSERT_BENHNHAN(
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
      INSERT INTO hospital.BENHNHAN
        (MABN, HOTEN, NGAYSINH, DIACHI, SODT)
        VALUES
        (MABN, raw_HOTEN, raw_NGAYSINH, raw_DIACHI, raw_SODT);
        COMMIT;
        Dbms_Output.Put_Line('inserted');
END;
END INSERT_BENHNHAN;

CREATE OR REPLACE PROCEDURE hospital.PROCEDURE_45 
(
  PARAM1 IN VARCHAR2  --'NO PARAM'
, PARAM2 IN NUMBER --makb
, PARAM3 IN VARCHAR2 --'NO PARAM'
, PARAMOUT1 OUT NUMBER
, PARAMOUT2 OUT NVARCHAR2
, PARAMOUT3 OUT VARCHAR2
, PARAMOUT4 OUT VARCHAR2
, PARAMOUT5 OUT VARCHAR2
) AS 
BEGIN
  DECLARE
    v_Bn hospital.BENHNHAN%Rowtype;
    key_bytes_raw      RAW (16);
    bn_ID NUMBER;
    raw_HOTEN      RAW (400);
    raw_NGAYSINH      RAW (50);
    raw_DIACHI      RAW (50);
    raw_SODT      RAW (30);
    BEGIN
        IF PARAM1 = 'NO PARAM' AND PARAM3 = 'NO PARAM' THEN    
            SELECT MABN INTO bn_ID FROM hospital.HOSOBENHNHAN WHERE MAKB = PARAM2;
            SELECT * INTO v_Bn FROM hospital.BENHNHAN BN WHERE BN.MABN = bn_ID;
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
            
            PARAMOUT1 := bn_ID;
            PARAMOUT2 := CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_HOTEN),'US7ASCII','AL32UTF8');
            PARAMOUT3 := CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_NGAYSINH),'US7ASCII','AL32UTF8');
            PARAMOUT4 := CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_DIACHI),'US7ASCII','AL32UTF8');
            PARAMOUT5 := CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_SODT),'US7ASCII','AL32UTF8');
            dbms_output.put_line(PARAMOUT1 || ' ' || PARAMOUT2 ||
            '   ' || PARAMOUT3 || 
            '   ' || PARAMOUT3 ||
            '   ' || PARAMOUT4 ||
            '   ' || PARAMOUT5);
        ELSE
            dbms_output.put_line('No data found');
        End If;
    END;
END PROCEDURE_45;

CREATE OR REPLACE PROCEDURE hospital.UPDATE_BENHNHAN(
    inMABN IN NUMBER,
    HOTEN IN NVARCHAR2,
    NGAYSINH IN VARCHAR2,
    DIACHI IN VARCHAR2,
    SODT IN VARCHAR2
) AS 
BEGIN
DECLARE
    raw_TABLE      RAW (400) := 
   UTL_RAW.CAST_TO_RAW(CONVERT('BENHNHAN' ,'AL32UTF8','US7ASCII'));
   raw_HOTEN      RAW (400) :=
   UTL_RAW.CAST_TO_RAW(CONVERT(HOTEN ,'AL32UTF8','US7ASCII'));
   raw_NGAYSINH      RAW (50) := 
   UTL_RAW.CAST_TO_RAW(CONVERT(NGAYSINH,'AL32UTF8','US7ASCII'));
   raw_DIACHI      RAW (50) :=
   UTL_RAW.CAST_TO_RAW(CONVERT(DIACHI,'AL32UTF8','US7ASCII'));
   raw_SODT      RAW (30) :=
   UTL_RAW.CAST_TO_RAW(CONVERT(SODT ,'AL32UTF8','US7ASCII'));
   key_bytes_raw      RAW (16);               -- stores 128-bit encryption key
   v_Bn hospital.BENHNHAN%Rowtype;
   --encryption_type    PLS_INTEGER :=          -- total encryption type
                            --DBMS_CRYPTO.ENCRYPT_AES128 
                        --+ DBMS_CRYPTO.CHAIN_CBC
                          --+ DBMS_CRYPTO.PAD_PKCS5;
    BEGIN
        SELECT * INTO v_Bn FROM hospital.BENHNHAN BN WHERE BN.MABN = inMABN;
            --dbms_output.put_line(UTL_RAW.SUBSTR(v_Bn.HOTEN, 1, 4));
        key_bytes_raw :=  UTL_RAW.CONCAT(UTL_RAW.SUBSTR(v_Bn.HOTEN, 1, 4),
                UTL_RAW.SUBSTR(v_Bn.NGAYSINH, 1, 4), UTL_RAW.SUBSTR(v_Bn.DIACHI, 1, 4),
                UTL_RAW.SUBSTR(v_Bn.SODT, 1, 4));
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
       UPDATE hospital.BENHNHAN
       SET HOTEN = raw_HOTEN, NGAYSINH = raw_NGAYSINH, DIACHI = raw_DIACHI,
            SODT = raw_SODT
        WHERE MABN = inMABN;
        COMMIT;
        Dbms_Output.Put_Line('inserted');
END;
END UPDATE_BENHNHAN;

CREATE OR REPLACE PROCEDURE hospital.PROCEDURE_46
(
  PARAM1 IN VARCHAR2  --'NO PARAM'
, PARAM2 IN NUMBER --makb
, PARAM3 IN VARCHAR2 --'NO PARAM'
, PARAMOUT1 OUT NUMBER
, PARAMOUT2 OUT NVARCHAR2
, PARAMOUT3 OUT VARCHAR2
, PARAMOUT4 OUT VARCHAR2
, PARAMOUT5 OUT VARCHAR2
) AS 
BEGIN
  DECLARE
    v_Bn hospital.BENHNHAN%Rowtype;
    key_bytes_raw      RAW (16);
    raw_HOTEN      RAW (400);
    raw_NGAYSINH      RAW (50);
    raw_DIACHI      RAW (50);
    raw_SODT      RAW (30);
    BEGIN
        IF PARAM1 = 'NO PARAM' AND PARAM3 = 'NO PARAM' THEN    
            SELECT * INTO v_Bn FROM hospital.BENHNHAN BN WHERE BN.MABN = PARAM2;
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
            
            PARAMOUT1 := PARAM2;
            PARAMOUT2 := CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_HOTEN),'US7ASCII','AL32UTF8');
            PARAMOUT3 := CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_NGAYSINH),'US7ASCII','AL32UTF8');
            PARAMOUT4 := CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_DIACHI),'US7ASCII','AL32UTF8');
            PARAMOUT5 := CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_SODT),'US7ASCII','AL32UTF8');
            dbms_output.put_line(PARAMOUT1 || ' ' || PARAMOUT2 ||
            '   ' || PARAMOUT3 || 
            '   ' || PARAMOUT3 ||
            '   ' || PARAMOUT4 ||
            '   ' || PARAMOUT5);
        ELSE
            dbms_output.put_line('No data found');
        End If;
    END;
END PROCEDURE_46;

GRANT EXECUTE ON hospital.INSERT_BENHNHAN TO NV_TiepTan_DieuPhoi;
GRANT EXECUTE ON hospital.PROCEDURE_45 TO NV_TiepTan_DieuPhoi;
GRANT EXECUTE ON hospital.PROCEDURE_46 TO NV_TiepTan_DieuPhoi;
GRANT EXECUTE ON hospital.UPDATE_BENHNHAN TO NV_TiepTan_DieuPhoi;