package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.BaiViet;
import QuanlyPhatTu.Entities.LoaiBaiViet;
import QuanlyPhatTu.Repositories.BaiVietRepo;
import QuanlyPhatTu.Repositories.LoaiBaiVietRepo;
import QuanlyPhatTu.Repositories.PhatTuRepo;
import QuanlyPhatTu.Repositories.TrangThaiBaiVietRepo;
import QuanlyPhatTu.Request.ReUpdateBaiViet;
import QuanlyPhatTu.Request.RequestBaiViet;
import QuanlyPhatTu.Security.Service.UserDetailsImpl;
import QuanlyPhatTu.Services.Interfaces.IBaiViet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BaiVietService implements IBaiViet {
    @Autowired
    BaiVietRepo baiVietRepo;
    @Autowired
    LoaiBaiVietRepo loaiBaiVietRepo;
    @Autowired
    TrangThaiBaiVietRepo trangThaiBaiVietRepo;
    @Autowired
    PhatTuRepo phatTuRepo;

    @Override
    public String taoBaiViet(RequestBaiViet requestBaiViet) {
        Optional<LoaiBaiViet> loaiBaiVietTest = loaiBaiVietRepo.findById(requestBaiViet.loaiBaiViet);
        if(loaiBaiVietTest.isEmpty())
            return "Loại bài viết không tồn tại!";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        int userId = currentUser.getId();

        BaiViet baiViet = new BaiViet();
        baiViet.setLoaiBaiViet(loaiBaiVietTest.get());
        baiViet.setPhatTu(phatTuRepo.findById(userId).get());
        baiViet.setTieuDe(requestBaiViet.tieuDe);
        baiViet.setMoTa(requestBaiViet.mota);
        baiViet.setMoTa(requestBaiViet.noiDung);
        baiViet.setSoluotThich(0);
        baiViet.setSoluotBinhluan(0);
        baiViet.setDaXoa(0);
        baiViet.setTrangthaiBaiViet(trangThaiBaiVietRepo.findById(1).get());
        baiVietRepo.save(baiViet);

        return "Tạo bài viết thành công, chờ phên duyệt";
    }

    @Override
    public String suaBaiViet(ReUpdateBaiViet reUpdateBaiViet) {
        Optional<BaiViet> baiVietTest = baiVietRepo.findById(reUpdateBaiViet.id);
        if(baiVietTest.isEmpty())
            return "Bài viết không tồn tại để sửa!";

        BaiViet baiViet = baiVietTest.get();
        baiViet.setLoaiBaiViet(loaiBaiVietRepo.findById(reUpdateBaiViet.loaiBaiViet).get());
        baiViet.setTieuDe(reUpdateBaiViet.tieuDe);
        baiViet.setMoTa(reUpdateBaiViet.mota);
        baiViet.setNoiDung(reUpdateBaiViet.noiDung);
        baiVietRepo.save(baiViet);

        return "Sửa thành công!";
    }

    @Override
    public String xoaBaiViet(int baiVietID) {
        Optional<BaiViet> baiVietTest = baiVietRepo.findById(baiVietID);
        if(baiVietTest.isEmpty())
            return "Bài viết không tồn tại!";
        baiVietRepo.delete(baiVietTest.get());

        return "Xóa thành công!";
    }

    @Override
    public String duyetBaiViet(int baiVietID) {
        BaiViet baiViet = baiVietRepo.findById(baiVietID).get();
        baiViet.setTrangthaiBaiViet(trangThaiBaiVietRepo.findById(2).get());
        baiViet.setThoigianDang(LocalDate.now());
        baiVietRepo.save(baiViet);
        return null;
    }

}
