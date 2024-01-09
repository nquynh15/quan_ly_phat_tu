package QuanlyPhatTu.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TrangThaiDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tentrangthai")
    private String tenTrangThai;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trangThaiDon")
    @JsonIgnore
    private List<DonDangKy> donDangKyList;
}
