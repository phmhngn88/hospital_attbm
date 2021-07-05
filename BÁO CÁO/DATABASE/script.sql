create table DONVI
(
    MADV  NUMBER not null
        primary key,
    TENDV NVARCHAR2(50)
)
/

create table NHANVIEN
(
    MANV     VARCHAR2(5) not null
        primary key,
    HOTEN    NVARCHAR2(50),
    GIOITINH NVARCHAR2(5),
    NGAYSINH DATE,
    DIACHI   NVARCHAR2(100),
    LUONG    NUMBER,
    VAITRO   NVARCHAR2(50),
    DONVI    NUMBER
        constraint FK_NHANVIEN_DONVI
            references DONVI,
    ROWLABEL NUMBER(10) default NULL
)
/

create table BENHNHAN
(
    MABN     NUMBER not null
        primary key,
    HOTEN    RAW(400),
    NGAYSINH RAW(80),
    DIACHI   RAW(80),
    SODT     RAW(30)
)
/

create table HOSOBENHNHAN
(
    MAKB            NUMBER not null
        primary key,
    NGAYKB          DATE,
    MATT            VARCHAR2(5)
        constraint FK_HSBN_NV1
            references NHANVIEN,
    MABS            VARCHAR2(5)
        constraint FK_HSBN_NV2
            references NHANVIEN,
    MABN            NUMBER
        constraint FK_HSBN_BN
            references BENHNHAN,
    TINHTRANGBANDAU NVARCHAR2(200),
    KETLUANCUABS    NVARCHAR2(200)
)
/

create table DICHVU
(
    MADV   NUMBER not null
        primary key,
    TENDV  NVARCHAR2(50),
    DONGIA NUMBER
)
/

create table HOSODICHVU
(
    MAKB          NUMBER not null
        constraint FK_HSDV_HSBN
            references HOSOBENHNHAN,
    MADV          NUMBER not null
        constraint FK_HSDV_DICHVU
            references DICHVU,
    NGAYGIO       DATE,
    NGUOITHUCHIEN VARCHAR2(5)
        constraint FK_HSDV_NHANVIEN
            references NHANVIEN,
    KETLUAN       NVARCHAR2(200),
    GIATIEN       NUMBER,
    primary key (MAKB, MADV)
)
/

create table DONTHUOC
(
    MAKB NUMBER not null
        primary key,
    NVPT VARCHAR2(5)
        constraint FK_DONTHUOC_NHANVIEN
            references NHANVIEN
)
/

create table THUOC
(
    MATHUOC  NUMBER not null
        primary key,
    TENTHUOC NVARCHAR2(50),
    DVT      VARCHAR2(50),
    DONGIA   NUMBER,
    LUUY     NVARCHAR2(50)
)
/

create table CHITIETDONTHUOC
(
    MAKB     NUMBER not null
        constraint FK_CTDT_HSBN
            references HOSOBENHNHAN,
    MATHUOC  NUMBER not null
        constraint FK_HSDV_THUOC
            references THUOC,
    SL       NUMBER,
    LIEUDUNG NUMBER,
    MOTA     NVARCHAR2(50),
    GIATIEN  NUMBER,
    primary key (MAKB, MATHUOC)
)
/

create table HOADON
(
    SOHD     NUMBER not null
        primary key,
    MAKB     NUMBER
        constraint FK_HOADON_HSBN
            references HOSOBENHNHAN,
    NGAYHD   DATE,
    NGUOIPT  VARCHAR2(5)
        constraint FK_HOADON_NHANVIEN
            references NHANVIEN,
    TONGTIEN NUMBER
)
/

create table CTHDON
(
    SOHD    NUMBER not null
        constraint FK_CTHDON_HOADON
            references HOADON,
    MADV    NUMBER not null
        constraint FK_CTHDON_DICHVU
            references DICHVU,
    GIATIEN NUMBER,
    primary key (SOHD, MADV)
)
/

create table THANGNAM
(
    ID_THANGNAM NUMBER not null
        primary key,
    THANG       NUMBER,
    NAM         NUMBER
)
/

create table CHAMCONG
(
    MANV        VARCHAR2(5) not null
        constraint FK_CHAMCONG_NHANVIEN
            references NHANVIEN,
    SONGAYCONG  NUMBER,
    ID_THANGNAM NUMBER      not null
        constraint FK_CHAMCONG_THANGNAM
            references THANGNAM,
    primary key (MANV, ID_THANGNAM)
)
/

create or replace PROCEDURE INSERT_BENHNHAN(
    MABN IN NUMBER,
    HOTEN IN NVARCHAR2,
    NGAYSINH IN VARCHAR2,
    DIACHI IN VARCHAR2,
    SODT IN VARCHAR2
) AS
BEGIN
    DECLARE
        raw_TABLE     RAW(400)  :=
            UTL_RAW.CAST_TO_RAW(CONVERT('BENHNHAN', 'AL32UTF8', 'US7ASCII'));
        raw_MABN      RAW(2000) :=
            UTL_RAW.CAST_FROM_NUMBER(MABN);
        raw_HOTEN     RAW(400)  :=
            UTL_RAW.CAST_TO_RAW(CONVERT(HOTEN, 'AL32UTF8', 'US7ASCII'));
        raw_NGAYSINH  RAW(50)   :=
            UTL_RAW.CAST_TO_RAW(CONVERT(NGAYSINH, 'AL32UTF8', 'US7ASCII'));
        raw_DIACHI    RAW(50)   :=
            UTL_RAW.CAST_TO_RAW(CONVERT(DIACHI, 'AL32UTF8', 'US7ASCII'));
        raw_SODT      RAW(30)   :=
            UTL_RAW.CAST_TO_RAW(CONVERT(SODT, 'AL32UTF8', 'US7ASCII'));
        num_key_bytes NUMBER    := 128 / 8; -- key length 128 bits (16 bytes)
        salt_4insert  RAW(16);
        key_bytes_raw RAW(16); -- stores 128-bit encryption key
        --encryption_type    PLS_INTEGER :=          -- total encryption type
        --DBMS_CRYPTO.ENCRYPT_AES128 
        --+ DBMS_CRYPTO.CHAIN_CBC
        --+ DBMS_CRYPTO.PAD_PKCS5;
    BEGIN
        salt_4insert := DBMS_CRYPTO.RANDOMBYTES(num_key_bytes);
        key_bytes_raw :=
                UTL_RAW.BIT_XOR(
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
        dbms_output.put_line('raw ht: ' || raw_HOTEN);
        raw_NGAYSINH := UTL_RAW.CONCAT(UTL_RAW.SUBSTR(key_bytes_raw, 5, 4), raw_NGAYSINH);
        dbms_output.put_line('raw ns: ' || raw_NGAYSINH);
        raw_DIACHI := UTL_RAW.CONCAT(UTL_RAW.SUBSTR(key_bytes_raw, 9, 4), raw_DIACHI);
        dbms_output.put_line('raw dc: ' || raw_DIACHI);
        raw_SODT := UTL_RAW.CONCAT(UTL_RAW.SUBSTR(key_bytes_raw, 13, 4), raw_SODT);
        dbms_output.put_line('raw sdt: ' || raw_SODT);
        INSERT INTO HOSPITAL_DBA.BENHNHAN
            (MABN, HOTEN, NGAYSINH, DIACHI, SODT)
        VALUES (MABN, raw_HOTEN, raw_NGAYSINH, raw_DIACHI, raw_SODT);
        COMMIT;
        Dbms_Output.Put_Line('inserted');
    END;
END INSERT_BENHNHAN;
/

create or replace PROCEDURE PROCEDURE_45(
    PARAM1 IN VARCHAR2 --'NO PARAM'
, PARAM2 IN NUMBER --makb
, PARAM3 IN VARCHAR2 --'NO PARAM'
) AS
BEGIN
    DECLARE
        v_Bn          BENHNHAN%Rowtype;
        key_bytes_raw RAW(16);
        bn_ID         NUMBER;
        raw_HOTEN     RAW(400);
        raw_NGAYSINH  RAW(50);
        raw_DIACHI    RAW(50);
        raw_SODT      RAW(30);
    BEGIN
        IF PARAM1 = 'NO PARAM' AND PARAM3 = 'NO PARAM' THEN
            SELECT MABN INTO bn_ID FROM HOSOBENHNHAN WHERE MAKB = PARAM2;
            --dbms_output.put_line('MAKB: ' || bn_ID);
            SELECT * INTO v_Bn FROM BENHNHAN BN WHERE BN.MABN = bn_ID;
            --dbms_output.put_line(UTL_RAW.SUBSTR(v_Bn.HOTEN, 1, 4));
            key_bytes_raw := UTL_RAW.CONCAT(UTL_RAW.SUBSTR(v_Bn.HOTEN, 1, 4),
                                            UTL_RAW.SUBSTR(v_Bn.NGAYSINH, 1, 4), UTL_RAW.SUBSTR(v_Bn.DIACHI, 1, 4),
                                            UTL_RAW.SUBSTR(v_Bn.SODT, 1, 4));
            --dbms_output.put_line('key: ' || key_bytes_raw);
            raw_HOTEN := UTL_RAW.SUBSTR(v_Bn.HOTEN, 5);
            raw_HOTEN := dbms_crypto.Decrypt(
                    src => RAWTOHEX(raw_HOTEN),
                    typ => DBMS_CRYPTO.DES_CBC_PKCS5,
                    key => key_bytes_raw);

            raw_NGAYSINH := UTL_RAW.SUBSTR(v_Bn.NGAYSINH, 5);
            raw_NGAYSINH := dbms_crypto.Decrypt(
                    src => RAWTOHEX(raw_NGAYSINH),
                    typ => DBMS_CRYPTO.DES_CBC_PKCS5,
                    key => key_bytes_raw);

            raw_DIACHI := UTL_RAW.SUBSTR(v_Bn.DIACHI, 5);
            raw_DIACHI := dbms_crypto.Decrypt(
                    src => RAWTOHEX(raw_DIACHI),
                    typ => DBMS_CRYPTO.DES_CBC_PKCS5,
                    key => key_bytes_raw);

            raw_SODT := UTL_RAW.SUBSTR(v_Bn.SODT, 5);
            raw_SODT := dbms_crypto.Decrypt(
                    src => RAWTOHEX(raw_SODT),
                    typ => DBMS_CRYPTO.DES_CBC_PKCS5,
                    key => key_bytes_raw);

            dbms_output.put_line(bn_ID || ' ' || CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_HOTEN), 'US7ASCII', 'AL32UTF8') ||
                                 '   ' || CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_NGAYSINH), 'US7ASCII', 'AL32UTF8') ||
                                 '   ' || CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_DIACHI), 'US7ASCII', 'AL32UTF8') ||
                                 '   ' || CONVERT(UTL_RAW.CAST_TO_VARCHAR2(raw_SODT), 'US7ASCII', 'AL32UTF8'));
        ELSE
            dbms_output.put_line('No data found');
        End If;
    END;
END PROCEDURE_45;
/

create or replace function HSBN_policy(
    p_schema IN VARCHAR2 DEFAULT NULL,
    p_object IN VARCHAR2 DEFAULT NULL)
    return varchar2
as
    username varchar2(100);
    roledb   NHANVIEN.VAITRO%TYPE DEFAULT NULL;
begin
    username := SYS_CONTEXT('USERENV', 'SESSION_USER'); --lay user (user duoc dat giong ma nhan vien)
    select VAITRO
    into roledb
    from NHANVIEN
    where username = MANV;
    if (roledb = 'BacSi') then
        return 'MABS =  SYS_CONTEXT(''USERENV'', ''SESSION_USER'')' ;--'''||user||'''' ;
    else
        return '';
    end if;
end;
/

create or replace function HSDV_policy(
    p_schema IN VARCHAR2 DEFAULT NULL,
    p_object IN VARCHAR2 DEFAULT NULL)
    return varchar2
as
    username varchar2(100);
    roledb   NHANVIEN.VAITRO%TYPE DEFAULT NULL;
begin
    username := SYS_CONTEXT('USERENV', 'SESSION_USER'); --lay user (user duoc dat giong ma nhan vien)
    select VAITRO
    into roledb
    from NHANVIEN
    where username = MANV;
    if (roledb = 'BacSi') then
        return 'NGUOITHUCHIEN =  SYS_CONTEXT(''USERENV'', ''SESSION_USER'')' ;--'''||user||'''' ;
    else
        return '';
    end if;
end;
/

create or replace function NV_Luong_policy(
    p_schema IN VARCHAR2 DEFAULT NULL,
    p_object IN VARCHAR2 DEFAULT NULL)
    return varchar2
as
    --username varchar2(100);
    --roledb NHANVIEN.VAITRO%TYPE DEFAULT NULL;
begin
    --username := SYS_CONTEXT ('USERENV', 'SESSION_USER'); --lay user (user duoc dat giong ma nhan vien)
    --select VAITRO into roledb 
    --from NHANVIEN
    --where username = MANV;
    --if(roledb = 'NhanVien_QL_TN_NS') then 
    --   return '';
    --else 
    return 'MANV = SYS_CONTEXT(''USERENV'', ''SESSION_USER'')';
    --end if;
end;
/

create or replace function no_records(
    p_schema IN VARCHAR2 DEFAULT NULL,
    p_object IN VARCHAR2 DEFAULT NULL)
    RETURN VARCHAR2
AS
BEGIN
    RETURN '1=0';
END;
/

create or replace PROCEDURE show_system_user(v_TABLE OUT SYS_REFCURSOR)
AS
BEGIN
    open V_TABLE FOR
        SELECT *
        FROM all_users
        order by created DESC;
End;
/

create or replace PROCEDURE show_system_role(v_TABLE OUT SYS_REFCURSOR)
AS
BEGIN
    open V_TABLE FOR
        SELECT *
        FROM DBA_ROLES
        ORDER BY ROLE_ID DESC;
End;
/

create or replace PROCEDURE view_user_priviledge(v_TABLE OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN v_TABLE FOR
        SELECT USER_TAB_PRIVS.*
        FROM USER_TAB_PRIVS
        WHERE OWNER = 'HOSPITAL_DBA';
End;
/

create or replace PROCEDURE grant_priviledge(
    v_USER IN NVARCHAR2,
    v_TABLE IN NVARCHAR2,
    v_privil IN NVARCHAR2,
    v_GRANT_OPTION IN NVARCHAR2,
    v_COLUMN IN NVARCHAR2
)
    AUTHID CURRENT_USER
    IS
    user_name    NVARCHAR2(20) := v_USER;
    grant_table  NVARCHAR2(20) := v_TABLE;
    grant_privil NVARCHAR2(20) := v_privil;
    grant_column NVARCHAR2(20) := v_COLUMN;
    grant_option NVARCHAR2(20) := v_GRANT_OPTION;
    lv_stmt      NVARCHAR2(1000);
BEGIN
    IF grant_column = 'ALL COLUMNS' THEN
        IF grant_option = 'TRUE' THEN
            lv_stmt := 'GRANT ' || grant_privil || ' ON ' || grant_table || ' TO ' || user_name || ' WITH GRANT OPTION';
        ELSE
            lv_stmt := 'GRANT ' || grant_privil || ' ON ' || grant_table || ' TO ' || user_name;
        END IF;
    ELSE
        IF grant_option = 'TRUE' THEN
            lv_stmt := 'GRANT ' || grant_privil || '(' || grant_column || ') ' || ' ON ' || grant_table || ' TO ' ||
                       user_name || ' WITH GRANT OPTION';
        ELSE
            lv_stmt := 'GRANT ' || grant_privil || '(' || grant_column || ') ' || ' ON ' || grant_table || ' TO ' ||
                       user_name;
        END IF;
    END IF;
    --DBMS_OUTPUT.put_line(lv_stmt);
    EXECUTE IMMEDIATE (lv_stmt);
END;
/

create or replace PROCEDURE revoke_priviledge(
    v_USER IN NVARCHAR2,
    v_TABLE IN NVARCHAR2,
    v_privil IN NVARCHAR2
)
    AUTHID CURRENT_USER
    IS
    user_name NVARCHAR2(20) := v_USER;
    rv_table  NVARCHAR2(20) := v_TABLE;
    rv_privil NVARCHAR2(20) := v_privil;
    lv_stmt   NVARCHAR2(1000);
BEGIN
    lv_stmt := 'REVOKE ' || rv_privil || ' ON ' || rv_table || ' FROM ' || user_name;
    EXECUTE IMMEDIATE (lv_stmt);
END;
/

create or replace PROCEDURE revoke_role(
    v_USER IN NVARCHAR2,
    V_ROLE IN NVARCHAR2
)
    AUTHID CURRENT_USER
    IS
    user_name NVARCHAR2(20) := v_USER;
    RV_ROLE   NVARCHAR2(20) := V_ROLE;
    lv_stmt   NVARCHAR2(1000);
BEGIN
    lv_stmt := 'REVOKE ' || RV_ROLE || ' FROM ' || user_name;
    EXECUTE IMMEDIATE (lv_stmt);
END;
/

create or replace PROCEDURE view_user_roles(v_TABLE OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN v_TABLE FOR
        SELECT DBA_ROLE_PRIVS.*
        FROM DBA_ROLE_PRIVS,
             all_users
        WHERE all_users.username = dba_role_privs.grantee;
end;
/


