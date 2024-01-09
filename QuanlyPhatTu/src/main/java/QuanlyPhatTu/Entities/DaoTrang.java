package QuanlyPhatTu.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class DaoTrang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "daketthuc")
    private int daKetThuc;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "noitochuc")
    private String noiToChuc;

    @Column(name = "sothanhvienthamgia")
    private int soTVthamgia;

    @Column(name = "thoigianBatdau")
    private LocalDate thoigianBatdau;

    @Column(name = "nguoitrutri")
    private int nguoiTruTri;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "daoTrang")
    @JsonIgnore
    private List<DonDangKy> donDangKyList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "daoTrang")
    @JsonIgnore
    private List<PhatTuDaoTrang> phatTuDaoTrangList;
}
