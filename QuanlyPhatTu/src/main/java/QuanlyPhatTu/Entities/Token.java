package QuanlyPhatTu.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public int ptId;
    public LocalTime timeExit;

    public Token(int ptId, LocalTime timeExit) {
        this.ptId = ptId;
        this.timeExit = LocalTime.now().plusHours(4);
    }

}
