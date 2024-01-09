package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.DaoTrang;
import QuanlyPhatTu.Entities.PhatTu;
import QuanlyPhatTu.Repositories.DaoTrangRepo;
import QuanlyPhatTu.Request.RequestDaoTrangAdd;
import QuanlyPhatTu.Request.RequestDaoTrangUpdate;
import QuanlyPhatTu.Services.Interfaces.IDaoTrang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DaoTrangService implements IDaoTrang {
    @Autowired
    DaoTrangRepo daoTrangRepo;

    @Override
    public DaoTrang themDaoTrang(RequestDaoTrangAdd daoTrang) {
        DaoTrang daoTrangmoi = new DaoTrang();
        daoTrangmoi.setNoiDung(daoTrang.noiDung);
        daoTrangmoi.setNoiToChuc(daoTrang.noiToChuc);
        daoTrangmoi.setThoigianBatdau(daoTrang.thoiGianBatDau);
        daoTrangmoi.setDaKetThuc(0);
        daoTrangmoi.setSoTVthamgia(0);

        daoTrangRepo.save(daoTrangmoi);
        return daoTrangmoi;
    }

    @Override
    public String suaDaoTrang(int daotrangID, RequestDaoTrangUpdate daoTrang) {
        Optional<DaoTrang> daoTrangTest = daoTrangRepo.findById(daotrangID);
        if(daoTrangTest.isEmpty())
            return "Đạo tràng không tồn tại!";
        DaoTrang daoTrangcu = daoTrangTest.get();
        daoTrangcu.setDaKetThuc(daoTrang.daKetThuc);
        daoTrangcu.setNoiDung(daoTrang.noiDung);
        daoTrangcu.setNoiToChuc(daoTrang.noiToChuc);
        daoTrangcu.setSoTVthamgia(daoTrang.soThanhVien);
        daoTrangcu.setThoigianBatdau(daoTrang.thogianBatDau);
        daoTrangcu.setNguoiTruTri(daoTrang.nguoiTruTri);

        daoTrangRepo.save(daoTrangcu);

        return "Update successfully!";
    }

    @Override
    public String xoaDaoTrang(int id) {
        Optional<DaoTrang> daoTrangTest = daoTrangRepo.findById(id);
        if(daoTrangTest.isEmpty())
            return "Đạo tràng không tồn tại!";
        daoTrangRepo.deleteById(id);
        return "Đã xóa đạo tràng!";
    }

    private int compareTime(LocalDate time1, LocalDate time2){
        return time1.compareTo(time2);
    }

    @Override
    public List<DaoTrang> locTheoThoiGian(LocalDate thoigianBatdau1, LocalDate thoigianBatdau2, Pageable page) {

        List<DaoTrang> list = new ArrayList<>();
        List<DaoTrang> listDaoTrang = daoTrangRepo.findAll();
        if(thoigianBatdau1 == null && thoigianBatdau2 == null)
            list = listDaoTrang;
        else if(thoigianBatdau1 == null){
            for (DaoTrang daotrang: listDaoTrang) {
                if(daotrang.getThoigianBatdau().compareTo(thoigianBatdau2) < 0)
                    list.add(daotrang);
            }
        }
        else if(thoigianBatdau2 == null){
            for (DaoTrang daotrang: listDaoTrang) {
                if(daotrang.getThoigianBatdau().compareTo(thoigianBatdau1) > 0)
                    list.add(daotrang);
            }
        }
        else{
            for (DaoTrang daotrang: listDaoTrang) {
                if(daotrang.getThoigianBatdau().compareTo(thoigianBatdau1) > 0 && daotrang.getThoigianBatdau().compareTo(thoigianBatdau2) < 0)
                    list.add(daotrang);
            }
        }


        int pageNumber = page.getPageNumber();
        int pagesize = page.getPageSize();
        int fromIndex = pageNumber * pagesize;
        int toIndex = Math.min(fromIndex + pagesize, list.size());
        if(fromIndex >= list.size())
            return Collections.emptyList();

        return list.subList(fromIndex, toIndex);
    }

    @Override
    public List<DaoTrang> locTheoChuTri(int chuTriId, Pageable page) {
        List<DaoTrang> list = new ArrayList<>();
        for (DaoTrang daotrang: daoTrangRepo.findAll()) {
            if(daotrang.getNguoiTruTri() == chuTriId)
                list.add(daotrang);
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
