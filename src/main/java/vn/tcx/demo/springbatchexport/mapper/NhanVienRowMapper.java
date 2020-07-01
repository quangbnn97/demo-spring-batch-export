package vn.tcx.demo.springbatchexport.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import vn.tcx.demo.springbatchexport.domain.NhanVien;

public class NhanVienRowMapper implements RowMapper<NhanVien> {

    @Override
    public NhanVien mapRow(ResultSet rs, int rowNum) throws SQLException {
        NhanVien nhanVien = new NhanVien();

        nhanVien.setMaNhanVien(rs.getString("ma_nhan_vien"));
        nhanVien.setTenNhanVien(rs.getString("ten_nhan_vien"));
        nhanVien.setEmail(rs.getString("email"));
        nhanVien.setSoDienThoai(rs.getLong("so_dien_thoai"));

        return nhanVien;
    }

}
