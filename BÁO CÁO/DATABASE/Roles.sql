--Role Nhan Vien Quan Ly
--Nhan Vien Quan Ly tai nguyen nhan su
create Role NhanVien_QL_TN_NS;


grant insert, select, update, delete on NHANVIEN to NhanVien_QL_TN_NS;
grant insert, select, update, delete on DONVI to NhanVien_QL_TN_NS;
grant select on BENHNHAN to NhanVien_QL_TN_NS;


--Nhan Vien quan ly tai vu
create Role NhanVien_QL_TV;
grant select on HOSODICHVU to NhanVien_QL_TV;
grant select on HOSOBENHNHAN to NhanVien_QL_TV;
grant select on THUOC to NhanVien_QL_TV;
grant insert, update (DONGIA) on THUOC to NhanVien_QL_TV;
grant update (GIATIEN) on CHITIETDONTHUOC to NhanVien_QL_TV;
grant select on CHITIETDONTHUOC to NhanVien_QL_TV;

grant select on DICHVU to NhanVien_QL_TV;
grant update (DONGIA) on DICHVU to NhanVien_QL_TV;

grant select on CTHDON to NhanVien_QL_TV;
grant update (GIATIEN) on CTHDON to NhanVien_QL_TV;
grant update (GIATIEN) on HOSODICHVU to NhanVien_QL_TV;


--Role Nhan vien ban thuoc
create Role NV_BanThuoc;
grant select on THUOC to NV_BanThuoc;
grant select on CHITIETDONTHUOC to NV_BanThuoc;
grant update (GIATIEN) on CHITIETDONTHUOC to NV_BanThuoc;

--Role Tiep Tan va Dieu Phoi
create Role NV_TiepTan_DieuPhoi;
grant insert, select, update, delete on BENHNHAN to NV_TiepTan_DieuPhoi;
grant select on HOSOBENHNHAN to NV_TiepTan_DieuPhoi;
grant update (NGAYKB, MABN, MATT, TINHTRANGBANDAU) on HOSOBENHNHAN to NV_TiepTan_DieuPhoi;
grant select on HOSODICHVU to NV_TiepTan_DieuPhoi;
grant insert on HOSODICHVU to NV_TiepTan_DieuPhoi;

--Role Bac Si
drop role BacSi;
create Role BacSi;
grant select on HOSOBENHNHAN to BacSi;
grant update (KETLUANCUABS) on HOSOBENHNHAN to BacSi;
grant select on HOSODICHVU to BacSi;
grant update (MADV, MAKB, NGAYGIO, NGUOITHUCHIEN, KETLUAN) on HOSODICHVU to BacSi;
grant insert, update, delete, select on CHITIETDONTHUOC to BacSi;

--Role Nhan Vien Ke Toan
create Role NV_KeToan;
grant select on CHAMCONG to NV_KeToan;
grant select on NHANVIEN to NV_KeToan;
grant update(LUONG) on NHANVIEN to NV_KeToan;


create user NV001 identified by 12345;
create user NV002 identified by 12345;
create user NV003 identified by 12345;
create user NV004 identified by 12345;
create user NV005 identified by 12345;
create user NV006 identified by 12345;

--RBAC
grant BacSi to NV005;
grant BacSi to NV006;
grant NhanVien_QL_TN_NS to NV001;
grant NhanVien_QL_TV to NV003;
grant NV_BanThuoc to NV004;
grant NV_TiepTan_DieuPhoi to NV002;
create user NV011 identified by 12345;
create user NV012 identified by 12345;
create user NV013 identified by 12345;
create user NV014 identified by 12345;
create user NV015 identified by 12345;
create user NV016 identified by 12345;
create user NV000 identified by 12345;--GiamDoc

grant create session to NV011;
grant create session to NV012;
grant create session to NV013;
grant create session to NV014;
grant create session to NV015;
grant create session to NV000;

grant select on NHANVIEN to NV011;
grant select on NHANVIEN to NV012;
grant select on NHANVIEN to NV013;
grant select on NHANVIEN to NV014;
grant select on NHANVIEN to NV015;
grant select on NHANVIEN to NV000;

grant NhanVien_QL_TV to NV013;
GRANT SELECT ON NHANVIEN TO BACSI;

SELECT * FROM all_users;

grant create session to BacSi;
grant create session to NhanVien_QL_TN_NS;
grant create session to NhanVien_QL_TV;
grant create session to NV_BanThuoc;
grant create session to NV_TiepTan_DieuPhoi;


select * from NHANVIEN;

SELECT
  *
FROM
  DBA_ROLE_PRIVS
SELECT
  *
FROM
  USER_SYS_PRIVS;
  
  
  
Drop role BacSi;
Drop role NhanVien_QL_TN_NS;
Drop role NhanVien_QL_TV;
Drop role NV_BanThuoc;
Drop role NV_TiepTan_DieuPhoi;
Drop role NV_KeToan;

Drop user NV001;
Drop user NV002;
Drop user NV003;
Drop user NV004;
Drop user NV005;
Drop user NV006;

--chon user dang dang nhap
SELECT SYS_CONTEXT ('USERENV', 'SESSION_USER') 
   FROM DUAL;
   
--lay ra role cua user dang dang nhap
SELECT Granted_role FROM USER_ROLE_PRIVS;

select
   grantee
from
   sys.dba_role_privs
where
   granted_role = 'DBA'
and
   grantee not in ('SYS','SYSTEM');
   
   
   