package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.DaoTrang;
import QuanlyPhatTu.Entities.DonDangKy;
import QuanlyPhatTu.Entities.PhatTu;
import QuanlyPhatTu.Repositories.DaoTrangRepo;
import QuanlyPhatTu.Repositories.DonDangKyRepo;
import QuanlyPhatTu.Repositories.PhatTuRepo;
import QuanlyPhatTu.Repositories.TrangThaiDonRepo;
import QuanlyPhatTu.Request.RequestDonDangKy;
import QuanlyPhatTu.Security.Service.UserDetailsImpl;
import QuanlyPhatTu.Services.Interfaces.IDonDangKy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DonDangKyService implements IDonDangKy {
    @Autowired
    DaoTrangRepo daoTrangRepo;
    @Autowired
    PhatTuRepo phatTuRepo;
    @Autowired
    DonDangKyRepo donDangKyRepo;
    @Autowired
    TrangThaiDonRepo trangThaiDonRepo;

    @Override
    public String guiDonDangKy(RequestDonDangKy donDangKy) {
        Optional<DaoTrang> daoTrangTest = daoTrangRepo.findById(donDangKy.daoTrangId);
        if(daoTrangTest.isEmpty())
            return "Đạo tràng chưa tồn tại!";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        int userId = currentUser.getId();
        Optional<PhatTu> phatTuTest = phatTuRepo.findById(userId);

        DonDangKy donDangKymoi = new DonDangKy();
        donDangKymoi.setNgayGuiDon(LocalDate.now());
        donDangKymoi.setNgayXuLy(null);
        donDangKymoi.setTrangThaiDon(trangThaiDonRepo.findById(1).get());
        donDangKymoi.setDaoTrang(daoTrangTest.get());
        donDangKymoi.setPhatTu(phatTuTest.get());
        donDangKyRepo.save(donDangKymoi);

        return "Đăng ký thành công!";
    }

    @Override
    public String duyetDon(int donID) {
        Optional<DonDangKy> donDangKyTest = donDangKyRepo.findById(donID);
        DonDangKy donDangKy = donDangKyTest.get();
        donDangKy.setTrangThaiDon(trangThaiDonRepo.findById(2).get());
        DaoTrang daoTrang = daoTrangRepo.findById(donDangKy.getDaoTrang().getId()).get();
        daoTrang.setSoTVthamgia(daoTrang.getSoTVthamgia() + 1);

        donDangKyRepo.save(donDangKy);
        daoTrangRepo.save(daoTrang);

        return "Đã duyệt!";
    }

    @Override
    public List<DonDangKy> locDuLieu(String trangThaiDon, Pageable page) {
        List<DonDangKy> list = new ArrayList<>();
        //Optional<TrangThaiDon>  trangThaiDonTest = trangThaiDonRepo.findAllByTenTrangThai(trangThaiDon);
        for (DonDangKy donDangky: donDangKyRepo.findAll()) {
            if(donDangky.getTrangThaiDon().getTenTrangThai().equals(trangThaiDon))
                list.add(donDangky);
        }

        int pageNumber = page.getPageNumber();
        int pagesize = page.getPageSize();
        int fromIndex = pageNumber * pagesize;
        int toIndex = Math.min(fromIndex + pagesize, list.size());
        if(fromIndex >= list.size())
            return Collections.emptyList();

        return list.subList(fromIndex, toIndex);
    }
}
