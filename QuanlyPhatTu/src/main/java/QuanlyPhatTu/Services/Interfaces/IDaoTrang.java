package QuanlyPhatTu.Services.Interfaces;

import QuanlyPhatTu.Entities.DaoTrang;
import QuanlyPhatTu.Entities.PhatTu;
import QuanlyPhatTu.Request.RequestDaoTrangAdd;
import QuanlyPhatTu.Request.RequestDaoTrangUpdate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IDaoTrang {
    public DaoTrang themDaoTrang(RequestDaoTrangAdd daoTrang);
    public String suaDaoTrang(int daotrangID, RequestDaoTrangUpdate daoTrang);
    public String xoaDaoTrang(int id);

    public List<DaoTrang> locTheoThoiGian(LocalDate thoigianBatdau1, LocalDate thoigianBatdau2, Pageable page);
    public List<DaoTrang> locTheoChuTri(int chuTriId, Pageable page);
}
