package QuanlyPhatTu.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class NguoiDungThichBinhLuanBaiViet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "phattuId")
    private PhatTu phatTu;

    @ManyToOne
    @JoinColumn(name = "binhluanbaivietId")
    private BinhLuanBaiViet binhLuanBaiViet;

    @Column(name = "thoigianlike")
    private LocalDate thoigianLike;

    @Column(name = "daxoa")
    private int daXoa;

}
