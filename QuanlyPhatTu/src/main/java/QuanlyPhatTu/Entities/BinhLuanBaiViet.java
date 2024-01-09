package QuanlyPhatTu.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class BinhLuanBaiViet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "baivietId")
    private BaiViet baiViet;

    @ManyToOne
    @JoinColumn(name = "phattuId")
    private PhatTu phatTu;

    @Column(name = "binhluan")
    private String binhLuan;

    @Column(name = "soluotThich")
    private int soLuotThich;

    @Column(name = "thoigiantao")
    private LocalDate thoigianTao;

    @Column(name = "thoigiancapnhat")
    private LocalDate thoigianCapNhat;

    @Column(name = "thoigianxoa")
    private LocalDate thoigianXoa;

    @Column(name = "daxoa")
    private int daXoa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "binhLuanBaiViet")
    @JsonIgnore
    private List<NguoiDungThichBinhLuanBaiViet> nguoiDungThichBinhLuanBaiVietList;

}
