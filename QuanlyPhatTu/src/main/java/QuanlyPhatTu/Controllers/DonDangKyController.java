package QuanlyPhatTu.Controllers;

import QuanlyPhatTu.Entities.DonDangKy;
import QuanlyPhatTu.Request.RequestDonDangKy;
import QuanlyPhatTu.Services.Implements.DonDangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/form")
public class DonDangKyController {
    @Autowired
    DonDangKyService donDangKyService;

    @PostMapping(value = "/submit")
    public String guiDonDangKy(@RequestBody RequestDonDangKy donDangKy){
        return donDangKyService.guiDonDangKy(donDangKy);
    }

    @PutMapping(value = "/approve")
    public String duyetDon(@RequestParam int donID){
        return donDangKyService.duyetDon(donID);
    }

    @GetMapping(value = "/get-data-form")
    public List<DonDangKy> layDuLieu(@RequestParam String trangThaiDon){
        Pageable page = PageRequest.of(0, 20);
        return donDangKyService.locDuLieu(trangThaiDon, page);
    }
}
