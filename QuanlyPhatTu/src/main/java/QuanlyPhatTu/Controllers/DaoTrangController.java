package QuanlyPhatTu.Controllers;

import QuanlyPhatTu.Entities.DaoTrang;
import QuanlyPhatTu.Repositories.DaoTrangRepo;
import QuanlyPhatTu.Request.RequestDaoTrangAdd;
import QuanlyPhatTu.Request.RequestDaoTrangUpdate;
import QuanlyPhatTu.Services.Implements.DaoTrangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/dao-trang")
public class DaoTrangController {
    @Autowired
    DaoTrangRepo daoTrangRepo;
    @Autowired
    DaoTrangService daoTrangService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> themDaoTrang(@RequestBody RequestDaoTrangAdd daoTrang){
        daoTrangService.themDaoTrang(daoTrang);
        return ResponseEntity.ok().body("Thêm đạo tràng thành công");
    }

    @PutMapping(value = "/update")
    public String suaDaoTrang(@RequestParam int daotrangID,@RequestBody RequestDaoTrangUpdate daoTrang){
        return daoTrangService.suaDaoTrang(daotrangID, daoTrang);
    }

    @DeleteMapping(value = "/delete")
    public String xoaDaoTrang(@RequestParam int daotrangID){
        return daoTrangService.xoaDaoTrang(daotrangID);
    }

    @GetMapping(value = "/filter-by-date")
    public List<DaoTrang> locTheoThoiGianBatDau(@RequestParam LocalDate thoigian1, LocalDate thoigian2){
        Pageable page = PageRequest.of(0, 20);
        return daoTrangService.locTheoThoiGian(thoigian1, thoigian2, page);
    }
    @GetMapping(value = "/filter-by-chutri")
    public List<DaoTrang> locTheoChuTri(@RequestParam int chutriID){
        Pageable page = PageRequest.of(0, 20);
        return daoTrangService.locTheoChuTri(chutriID, page);
    }
}
