package QuanlyPhatTu.Request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestComment {
    private int baivietID;
    private String text;
}
