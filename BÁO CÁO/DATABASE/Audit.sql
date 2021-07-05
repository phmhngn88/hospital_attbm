--Dang Nhap voi quyen SYS
--
alter system set audit_trail = DB, EXTENDED scope = spfile; -- chuc nang giong voi audit_trail = db nhung co them 2 cot SQL binding va SQL text vao sys.aud$ 
--System SET altered.
show parameter audit_trail;

shutdown immediate;

startup;

------------------
--Audit cac bang
audit all on hospital_dba.NHANVIEN by access;
audit all on hospital_dba.BENHNHAN by access;


-- by access nghia la moi hoat dong tren object se dc luu thanh 1 dong trong bang dba_audit_trail;


noaudit all on hospitaldba.NHANVIEN;

--mo ta bang luu de lay ra cac cot
desc dba_audit_trail

--kiem tra
select os_username, username, owner, timestamp, action_name, sql_text
from dba_audit_trail
order by  TIMESTAMP desc;

select *
from dba_audit_trail
order by  TIMESTAMP desc;

------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------
--Dang nhap voi DBA
--Fined-Grain Audit
--Kiem tra audit policy
select OBJECT_SCHEMA,OBJECT_NAME,POLICY_OWNER,POLICY_NAME,POLICY_COLUMN,SEL,INS,UPD,DEL
 from dba_audit_policies;

--Audit nhung ai da insert, update, delete tren cot Luong Bang NhanVien
Begin
    DBMS_FGA.add_policy(
    object_schema   => 'hospital_dba',
    object_name     => 'NHANVIEN',
    policy_name     => 'Luong_audit',
    statement_types => 'insert, update, delete',
    audit_column    => 'LUONG'
    );
End;
--Test 
update NHANVIEN 
set LUONG = 10000
where NHANVIEN.MANV = 'NV001';

--Audit nhung ai da thao tac tren cot KetLuanCUaBacSi trong bang HOSOBENHNHAN
Begin
    DBMS_FGA.add_policy(
    object_schema   => 'hospital_dba',
    object_name     => 'HOSOBENHNHAN',
    policy_name     => 'HSBN_KetLuan_audit',
    statement_types => 'insert, update, delete, select',
    audit_column    => 'KETLUANCUABS'
    );
End;


--Test 
select * from HOSOBENHNHAN; --phai dang nhap voi NV005 moi lam dc vi dba da bi gioi han boi VPD

--Check FGA
select * from DBA_FGA_AUDIT_TRAIL;


--Kiem tra cac chinh sach audit
select os_username, username, owner, timestamp, action, sql_text from DBA_COMMON_AUDIT_TRAIL ORDER BY EXTENDED_TIMESTAMP DESC;

    
SELECT * FROM USER_AUDIT_SESSION;

SELECT os_username, username, owner, timestamp, action, sql_text FROM DBA_OBJ_AUDIT_OPTS WHERE OWNER != 'LBACSYS' AND OWNER != 'DVSYS' AND OWNER != 'DVF';

CREATE OR REPLACE PROCEDURE activeAuditAll(idk IN NVARCHAR2(500)) AS
BEGIN

    execute immediate 'AUDIT ALL ON ' ||idk|| ' BY ACCESS';
end;

BEGIN
    activeAuditAll('nhanvien');
end;

alter session set container = "XEPDB1";
