package QuanlyPhatTu.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class DonDangKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ngayguidon")
    private LocalDate ngayGuiDon;

    @Column(name = "ngayXuLy")
    private LocalDate ngayXuLy;

    @Column(name = "nguoixuly")
    private int nguoiXuLy;


    @ManyToOne
    @JoinColumn(name = "trangthaidonId")
    private TrangThaiDon trangThaiDon;

    @ManyToOne
    @JoinColumn(name = "daotrangId")
    private DaoTrang daoTrang;

    @ManyToOne
    @JoinColumn(name = "phattuId")
    private PhatTu phatTu;
}
