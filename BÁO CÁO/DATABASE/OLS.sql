    --Cai dat Ols
--Part 1: check OLS of DB
--DB phai dc su dung tren Plugable database (PBD) khong duoc tren V$root
show con_name;    -- check curent PDB --Dang Nhap voi dba -- khong duoc la V$ROOT
SELECT STATUS FROM DBA_OLS_STATUS WHERE NAME = 'OLS_CONFIGURE_STATUS';-- check if OLS has been registered
SELECT VALUE FROM V$OPTION WHERE PARAMETER = 'Oracle Label Security';-- check if OLS endabled

--Part 2: if OLS not enabled yet
--Connect vao dung curent PDB (khong dc la V$ROOT)
--Tot nhat dung SQLPLUS
--SYS@XEPDB1 AS SYSDBA (XEPDB1 la 1 PDB dang su dung de luu schema)
EXEC LBACSYS.CONFIGURE_OLS; -- config
EXEC LBACSYS.OLS_ENFORCEMENT.ENABLE_OLS; -- enable




--Part3 : if OLS already enabled
ALTER USER LBACSYS ACCOUNT UNLOCK IDENTIFIED BY 12345;-- unlock user LBACSYS co quyen dung OLS

--Tao policy -- Dang Nhap voi LBACSYS -- MK la 12345
--Tao Policy
Begin SA_SYSDBA.create_policy(
    policy_name => 'BV_OLS',
    column_name => 'rowlabel',
    default_options => 'read_control');
End;

--drop policy
Begin SA_SYSDBA.drop_policy(
    policy_name => 'BV_OLS');
End;

--Tao Level 
--3 level danh cho Giam Doc> Quan Ly > NhanVien
Begin 
    SA_COMPONENTS.CREATE_LEVEL(
    policy_name => 'BV_OLS',
    level_num => 100,
    short_name => 'EXEC',
    long_name => 'GIAM DOC');
    
    SA_COMPONENTS.CREATE_LEVEL(
    policy_name => 'BV_OLS',
    level_num => 50,
    short_name => 'MGR',
    long_name => 'QUAN LY');
    
    SA_COMPONENTS.CREATE_LEVEL(
    policy_name => 'BV_OLS',
    level_num => 20,
    short_name => 'EMP',
    long_name => 'NHAN VIEN');

End;


--Tao Compartment
Begin    
    SA_COMPONENTS.CREATE_COMPARTMENT(
    policy_name => 'BV_OLS',
    comp_num    =>  75, 
    short_name  => 'QLNS',
    long_name   => 'Quan Ly Nhan Su');
    
    SA_COMPONENTS.CREATE_COMPARTMENT(
    policy_name => 'BV_OLS',
    comp_num    =>  65, 
    short_name  => 'BS',
    long_name   => 'Bac Si');
    
    SA_COMPONENTS.CREATE_COMPARTMENT(
    policy_name => 'BV_OLS',
    comp_num    =>  55, 
    short_name  => 'TT',
    long_name   => 'Tiep Tan');
    
    SA_COMPONENTS.CREATE_COMPARTMENT(
    policy_name => 'BV_OLS',
    comp_num    =>  45, 
    short_name  => 'TV',
    long_name   => 'Tai Vu');
End;

--Tao Label
BEGIN
    SA_LABEL_ADMIN.CREATE_LABEL  (
    policy_name     => 'BV_OLS',
    label_tag       => '1000',
    label_value     => 'EXEC:QLNS,BS,TT,TV',
    data_label      => TRUE);
    
    SA_LABEL_ADMIN.CREATE_LABEL  (
    policy_name     => 'BV_OLS',
    label_tag       => '900',
    label_value     => 'MGR:QLNS',
    data_label      => TRUE);    
    
    SA_LABEL_ADMIN.CREATE_LABEL  (
    policy_name     => 'BV_OLS',
    label_tag       => '800',
    label_value     => 'MGR:QLNS,BS,TT,TV',
    data_label      => TRUE);
    
    SA_LABEL_ADMIN.CREATE_LABEL  (
    policy_name     => 'BV_OLS',
    label_tag       => '700',
    label_value     => 'EMP:QLNS',
    data_label      => TRUE);
    
    SA_LABEL_ADMIN.CREATE_LABEL  (
    policy_name     => 'BV_OLS',
    label_tag       => '600',
    label_value     => 'EMP:BS',
    data_label      => TRUE);
    
    SA_LABEL_ADMIN.CREATE_LABEL  (
    policy_name     => 'BV_OLS',
    label_tag       => '500',
    label_value     => 'EMP:TT',
    data_label      => TRUE);
    
    SA_LABEL_ADMIN.CREATE_LABEL  (
    policy_name     => 'BV_OLS',
    label_tag       => '400',
    label_value     => 'EMP:TV',
    data_label      => TRUE);    
END;


--SELECT * FROM dba_sa_labels; -- Kiem tra danh sach label

--Gan User Label
--Tao NhanVien moi de gan
-- Dang nhap bang hos_dba



--Nhung nhan vien nay chua dc cap role nen khong the thao tac, ta se dung chung de the hien OLS

--Dang nhap lai bang LBACSYS
--Gan Level cho User
Begin
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV001',
    max_read_label    => 'MGR:QLNS,BS,TT,TV',
    max_write_label   => 'MGR:QLNS,BS,TT,TV',
    def_label         => 'MGR:QLNS,BS,TT,TV',
    row_label         => 'MGR:QLNS,BS,TT,TV');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV002',
    max_read_label    => 'EMP:TT',
    max_write_label   => 'EMP:TT',
    def_label         => 'EMP:TT',
    row_label         => 'EMP:TT');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV003',
    max_read_label    => 'EMP:TV',
    max_write_label   => 'EMP:TV',
    def_label         => 'EMP:TV',
    row_label         => 'EMP:TV');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV005',
    max_read_label    => 'EMP:BS',
    max_write_label   => 'EMP:BS',
    def_label         => 'EMP:BS',
    row_label         => 'EMP:BS');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV006',
    max_read_label    => 'EMP:BS',
    max_write_label   => 'EMP:BS',
    def_label         => 'EMP:BS',
    row_label         => 'EMP:BS');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV011',
    max_read_label    => 'MGR:QLNS,BS,TT,TV',
    max_write_label   => 'MGR:QLNS,BS,TT,TV',
    def_label         => 'MGR:QLNS,BS,TT,TV',
    row_label         => 'MGR:QLNS,BS,TT,TV');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV012',
    max_read_label    => 'EMP:TT',
    max_write_label   => 'EMP:TT',
    def_label         => 'EMP:TT',
    row_label         => 'EMP:TT');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV013',
    max_read_label    => 'EMP:TV',
    max_write_label   => 'EMP:TV',
    def_label         => 'EMP:TV',
    row_label         => 'EMP:TV');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV015',
    max_read_label    => 'EMP:BS',
    max_write_label   => 'EMP:BS',
    def_label         => 'EMP:BS',
    row_label         => 'EMP:BS');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV016',
    max_read_label    => 'EMP:BS',
    max_write_label   => 'EMP:BS',
    def_label         => 'EMP:BS',
    row_label         => 'EMP:BS');
    
    SA_USER_ADMIN.SET_USER_LABELS (
    policy_name       => 'BV_OLS',
    user_name         => 'NV000',
    max_read_label    => 'EXEC:QLNS,BS,TT,TV',
    max_write_label   => 'EXEC:QLNS,BS,TT,TV',
    def_label         => 'EXEC:QLNS,BS,TT,TV',
    row_label         => 'EXEC:QLNS,BS,TT,TV');

End;

--Gan Nhan cho cac du lieu cua bang
--Dang Nhap quyen SYS
UPDATE NHANVIEN
SET rowlabel = char_to_label ('BV_OLS', 'MGR:QLNS,BS,TT,TV')
where VAITRO = 'NhanVien_QL_TN_NS';

UPDATE NHANVIEN
SET rowlabel = char_to_label ('BV_OLS', 'EMP:TT')
where VAITRO = 'NV_TiepTan_DieuPhoi';

UPDATE NHANVIEN
SET rowlabel = char_to_label ('BV_OLS', 'EMP:TV')
where VAITRO = 'NhanVien_QL_TV';

UPDATE NHANVIEN
SET rowlabel = char_to_label ('BV_OLS', 'EMP:BS')
where VAITRO = 'BacSi';

UPDATE NHANVIEN
SET rowlabel = char_to_label ('BV_OLS', 'EXEC:QLNS,BS,TT,TV')
where VAITRO = 'GiamDoc';

--Ap dung Policy

--Dang Nhap voi quyen SYS
grant BV_OLS_DBA to LBACSYS; --gan quyen thuc thi chinh sach OLS vua tao ra cho LBACSYS
--Dang nhap lai voi LBACSYS;

Begin SA_SYSDBA.drop_policy(
    policy_name => 'BV_OLS');
End;

--Ap Dung chinh sach o bang nhan vien   
begin
    sa_policy_admin.apply_table_policy
    (policy_name =>'BV_OLS',
    schema_name => 'HOSPITAL_DBA',
    table_name => 'NHANVIEN',
    table_options =>
    'READ_CONTROL,WRITE_CONTROL,CHECK_CONTROL');
end;


select * from DBA_SA_USERS ;

select * from DBA_SA_USER_LEVELS;

select * from MAX_READ_LABEL;

select * from HOSPITAL_DBA.HOSODICHVU;
select * from HOSPITAL_DBA.NHANVIEN
WHERE VAITRO = 'BacSi';

select * from DBA_SA_USERS ;



