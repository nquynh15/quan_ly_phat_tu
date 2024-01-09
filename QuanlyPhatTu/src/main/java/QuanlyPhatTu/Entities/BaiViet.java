package QuanlyPhatTu.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import QuanlyPhatTu.Entities.LoaiBaiViet;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class BaiViet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "loaibaivietId")
    private LoaiBaiViet loaiBaiViet;

    @Column(name = "tieude")
    private String tieuDe;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "noidung")
    private String noiDung;

    @ManyToOne
    @JoinColumn(name = "phattuId")
    private PhatTu phatTu;

    @Column(name = "nguoiduyetbaivietId")
    private Integer nguoiduyetbaiId;

    @Column(name = "soluotthich")
    private Integer soluotThich;

    @Column(name = "soluotBinhLuan")
    private Integer soluotBinhluan;

    @Column(name = "thoigiandang")
    private LocalDate thoigianDang;

    @Column(name = "thoigiancapnhat")
    private LocalDate thoigianCapNhat;

    @Column(name = "thoigianxoa")
    private LocalDate thoigianXoa;

    @Column(name = "daxoa")
    private Integer daXoa;

    @ManyToOne
    @JoinColumn(name = "trangthaibaivietId")
    private TrangthaiBaiViet trangthaiBaiViet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "baiViet")
    @JsonIgnore
    private List<BinhLuanBaiViet> binhLuanBaiVietList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "baiViet")
    @JsonIgnore
    private List<NguoiDungThichBaiViet> nguoiDungThichBaiVietList;
}
