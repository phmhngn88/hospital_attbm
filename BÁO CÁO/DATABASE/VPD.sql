
--Dang nhap bang SYS, gan quuyen thuc thi o DBMS_RLS cho hos_dba
grant execute on DBMS_RLS to hos_dba;
--hos_dba
--Kiem tra policy
select * from USER_POLICIES;

--Tao Policy tren bang HSBN, bac si chi dc xem hsbn cua nhung benh nhan ma minh kham
create or replace function HSBN_policy(
    p_schema IN VARCHAR2 DEFAULT NULL,
    p_object IN VARCHAR2 DEFAULT NULL)
    return varchar2
    as
        username varchar2(100);
        roledb NHANVIEN.VAITRO%TYPE DEFAULT NULL;
    begin
        username := SYS_CONTEXT ('USERENV', 'SESSION_USER'); --lay user (user duoc dat giong ma nhan vien)
        select VAITRO into roledb 
        from NHANVIEN
        where username = MANV;
        if(roledb = 'BacSi') then 
           return 'MABS = SYS_CONTEXT(''USERENV'', ''SESSION_USER'')' ;--'''||user||'''' ;
        else 
            return '';
        end if;
    end;

select * from HOSOBENHNHAN;--khi tim bang SYS van khong the thay ket qua, vi VPD gioi han ca SYS
-- ap dung cho bang HSBN
begin
    DBMS_RLS.add_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'HOSOBENHNHAN',
    policy_name => 'BacSi_HSBN_policy',
    policy_function => 'HSBN_policy',
    statement_types => 'SELECT',
    update_check => true);
end;

begin
    DBMS_RLS.add_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'HOSOBENHNHAN',
    policy_name => 'BacSi_Update_HSBN_policy',
    policy_function => 'HSBN_policy',
    statement_types => 'Update',
    update_check => true);
end;

--xoa policy
begin
    DBMS_RLS.drop_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'HOSOBENHNHAN',
    policy_name => 'BacSi_HSBN_policy');
end;


--Bac si chi co the xem ho so dich vu tu bang HOSODICHVU ma chinh ho thuc hien cac dich vu do
create or replace function HSDV_policy(
    p_schema IN VARCHAR2 DEFAULT NULL,
    p_object IN VARCHAR2 DEFAULT NULL)
    return varchar2
    as
        username varchar2(100);
        roledb NHANVIEN.VAITRO%TYPE DEFAULT NULL;
    begin
        username := SYS_CONTEXT ('USERENV', 'SESSION_USER'); --lay user (user duoc dat giong ma nhan vien)
        select VAITRO into roledb 
        from NHANVIEN
        where username = MANV;
        if(roledb = 'BacSi') then
           return 'NGUOITHUCHIEN =  SYS_CONTEXT(''USERENV'', ''SESSION_USER'')' ;--'''||user||'''' ;
        else 
            return '';
        end if;
    end;

-- ap dung cho bang HSDV
begin
    DBMS_RLS.add_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'HOSODICHVU',
    policy_name => 'BacSi_HSDV_policy',
    policy_function => 'HSDV_policy',
    statement_types => 'SELECT',
    update_check => true);
end;

begin
    DBMS_RLS.add_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'HOSODICHVU',
    policy_name => 'BacSi_UPDATE_HSDV_policy',
    policy_function => 'HSDV_policy',
    statement_types => 'UPDATE',
    update_check => true);
end;

--xoa policy
begin
    DBMS_RLS.drop_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'HOSODICHVU',
    policy_name => 'BacSi_HSDV_policy');
end;

select * from HOSODICHVU;--check

--Tao policy tren bang NHANVIEN, Nhanvien chi co the nhin thay luong cua ban than
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
    
--ap dung chinh sach len bang nhan vien
begin
DBMS_RLS.add_policy
    (object_schema => 'HOSPITAL_DBA',
    object_name => 'NHANVIEN',
    policy_name => 'NVQL_NV_Luong_policy',
    policy_function => 'NV_Luong_policy',
    statement_types => 'SELECT',
    sec_relevant_cols => 'LUONG',
    sec_relevant_cols_opt => DBMS_RLS.all_rows);
 end;
 
 --xoa policy
begin
    DBMS_RLS.drop_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'NHANVIEN',
    policy_name => 'NVQL_NV_Luong_policy');
end;

--Tao Function no_record giup chan truy cap tat cac cac bang du lieu, truong hop nay la bang DICHVU
create or replace function no_records (
    p_schema IN VARCHAR2 DEFAULT NULL,
    p_object IN VARCHAR2 DEFAULT NULL)
    RETURN VARCHAR2
    AS
    BEGIN
    RETURN '1=0';
    END;
 
--ttao policy function ngan cac bang, o day la bang DICHVU, ngan ko cho insert, update, delete


-- chi cho xem bang DICHVU
begin
    DBMS_RLS.add_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'DICHVU',
    policy_name => 'no_record_policy',
    policy_function => 'no_records',
    statement_types => 'INSERT, UPDATE, DELETE',
    update_check => true);
end;

--khong cho xem bang DICHVU, cam bang DICHVU
begin
    DBMS_RLS.add_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'DICHVU',
    policy_name => 'no_read_DichVu',
    policy_function => 'no_records',
    statement_types => 'select',
    update_check => true);
end;

--xoa policy
begin
    DBMS_RLS.drop_policy(
    object_schema => 'HOSPITAL_DBA',
    object_name => 'DICHVU',
    policy_name => 'no_record_policy');
end;

--trace file
select host_name from v$instance
--DESKTOP-QC3TBIE
select name, value
from v$diag_info
where name = 'Diag Trace'
--C:\APP\DELL\PRODUCT\18.0.0\diag\rdbms\xe\xe\trace
select name, value 
from v$diag_info
where name = 'Default Trace File'
--C:\APP\DELL\PRODUCT\18.0.0\diag\rdbms\xe\xe\trace\xe_ora_8664.trc


--Tao ngu canh ung dung
--Create or replace package Set_NhanVien IS
--    procedure Set_job_position IS
--        jp varchar(100);
--    begin
--        select j

