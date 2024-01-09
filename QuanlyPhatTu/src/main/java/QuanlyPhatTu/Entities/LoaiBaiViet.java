package QuanlyPhatTu.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class LoaiBaiViet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tenloai")
    private String tenLoai;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "loaiBaiViet")
    @JsonIgnore
    private List<BaiViet> baiVietList;

}
