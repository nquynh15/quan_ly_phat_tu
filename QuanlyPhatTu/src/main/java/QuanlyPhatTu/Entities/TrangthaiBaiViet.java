package QuanlyPhatTu.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TrangthaiBaiViet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tentrangthai")
    private String tenTrangThai;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trangthaiBaiViet")
    @JsonIgnore
    private List<BaiViet> baiVietList;
}
