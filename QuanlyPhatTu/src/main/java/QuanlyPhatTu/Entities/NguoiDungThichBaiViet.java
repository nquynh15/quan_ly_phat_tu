package QuanlyPhatTu.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class NguoiDungThichBaiViet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "phattuId")
    private PhatTu phatTu;

    @ManyToOne
    @JoinColumn(name = "baivietId")
    private BaiViet baiViet;

    @Column(name = "thoigianthich")
    private LocalDate thoigianThich;

    @Column(name = "daxoa")
    private int daXoa;

}
