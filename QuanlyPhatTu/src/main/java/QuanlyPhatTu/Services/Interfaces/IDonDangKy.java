package QuanlyPhatTu.Services.Interfaces;

import QuanlyPhatTu.Entities.DonDangKy;
import QuanlyPhatTu.Request.RequestDonDangKy;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IDonDangKy {
    public String guiDonDangKy(RequestDonDangKy donDangKy);
    public String duyetDon(int donID);
    public List<DonDangKy> locDuLieu(String trangThaiDon, Pageable page);
}
