package QuanlyPhatTu.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class XacNhanEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "phattuId")
    private PhatTu phatTu;

    @Column(name = "thoigianhethan")
    private LocalTime thoigianHethan;

    @Column(name = "maxacnhan")
    private String maXacNhan;

    @Column(name = "daxacnhan")
    private boolean daXacNhan;

}
