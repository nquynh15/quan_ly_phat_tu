package QuanlyPhatTu.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "thoigianHethan")
    private LocalDateTime thoiginaHethan;

    @ManyToOne
    @JoinColumn(name = "phattuId")
    private PhatTu phatTu;

}
