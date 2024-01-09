package QuanlyPhatTu.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PhatTuDaoTrang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(name = "dathamggia")
    private boolean daThamGia;

    @Column(name = "lydoKhongthmgia")
    private String lydoKhongThamGia;

    @ManyToOne
    @JoinColumn(name = "daotrangId")
    private DaoTrang daoTrang;

    @ManyToOne
    @JoinColumn(name = "phattuId")
    private PhatTu phatTu;


}
