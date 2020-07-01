package vn.tcx.demo.springbatchexport.domain;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NhanVien {

    @Column(name = "id")
    private Long id;

    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ten_nhan_vien")
    private String tenNhanVien;

    @Column(name = "email")
    private String email;

    @Column(name = "so_dien_thoai")
    private Long soDienThoai;
}
