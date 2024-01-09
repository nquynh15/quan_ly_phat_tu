package QuanlyPhatTu.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Chua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tenchua")
    private String tenChua;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "ngaythanhlap")
    private LocalDate ngayThanhLap;

    @Column(name = "nguoiTrutri")
    private String nguoiTruTri;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chua")
    @JsonIgnore
    private List<PhatTu> phatTuList;
}
