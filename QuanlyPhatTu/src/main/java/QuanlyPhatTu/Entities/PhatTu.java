package QuanlyPhatTu.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class PhatTu extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "dahoantuc")
    private boolean daHoanTuc;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "updatetime")
    private LocalDate updateTime;

    @Column(name = "ngayhoanTuc")
    private LocalDate ngayHoanTuc;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "password")
    private String password;

    @Column(name = "phapDanh")
    private String phapDanh;

    @Column(name = "phonenumber")
    private String phonenumber;

    @ManyToOne
    @JoinColumn(name = "chuaId")
    private Chua chua;

    @ManyToMany(fetch = FetchType.EAGER) // dùng FetchType.EAGER thay vì .LAZY vì còn gọi lại roles khi đăng nhập
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phatTu")
    @JsonIgnore
    private List<RefreshToken> refreshTokenList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phatTu")
    @JsonIgnore
    private List<XacNhanEmail> xacNhanEmailList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phatTu")
    @JsonIgnore
    private List<DonDangKy> donDangKyList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phatTu")
    @JsonIgnore
    private List<PhatTuDaoTrang> phatTuDaoTrangList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phatTu")
    @JsonIgnore
    private List<NguoiDungThichBaiViet> nguoiDungThichBaiVietList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "phatTu")
    @JsonIgnore
    private List<BinhLuanBaiViet> binhLuanBaiVietList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "phatTu")
    @JsonIgnore
    private List<NguoiDungThichBinhLuanBaiViet> nguoiDungThichBinhLuanBaiVietList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phatTu")
    @JsonIgnore
    private List<BaiViet> baiVietList;

}
