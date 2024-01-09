package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.BaiViet;
import QuanlyPhatTu.Entities.NguoiDungThichBaiViet;
import QuanlyPhatTu.Repositories.BaiVietRepo;
import QuanlyPhatTu.Repositories.NguoiDungThichBaiVietRepo;
import QuanlyPhatTu.Repositories.PhatTuRepo;
import QuanlyPhatTu.Request.RequestThichBaiViet;
import QuanlyPhatTu.Security.Service.UserDetailsImpl;
import QuanlyPhatTu.Services.Interfaces.INguoiDungThichBaiViet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class NguoiDungThichBaiVietService implements INguoiDungThichBaiViet {
    @Autowired
    BaiVietRepo baiVietRepo;
    @Autowired
    NguoiDungThichBaiVietRepo nguoiDungThichBaiVietRepo;
    @Autowired
    PhatTuRepo phatTuRepo;

    @Override
    public String thichBaiViet(RequestThichBaiViet thichBaiViet) {
        Optional<BaiViet> baiVietTest = baiVietRepo.findById(thichBaiViet.baiVietID);
        if(baiVietTest.isEmpty())
            return "Bài viết không tồn tại!";
        BaiViet baiViet = baiVietTest.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        int userId = currentUser.getId();
        NguoiDungThichBaiViet nguoiDungThichBaiViet = nguoiDungThichBaiVietRepo.findNguoiDungThichBaiVietByBaiVietAndPhatTu(baiViet.getId(), userId);
        if(nguoiDungThichBaiViet != null){
            updateLike(nguoiDungThichBaiViet);
            nguoiDungThichBaiVietRepo.save(nguoiDungThichBaiViet);
        }
        else{
            nguoiDungThichBaiViet.setPhatTu(phatTuRepo.findById(userId).get());
            nguoiDungThichBaiViet.setBaiViet(baiViet);
            nguoiDungThichBaiViet.setDaXoa(0);
            nguoiDungThichBaiViet.setThoigianThich(LocalDate.now());
            BaiViet baiVietmoi = baiVietRepo.findById(nguoiDungThichBaiViet.getBaiViet().getId()).get();
            baiVietmoi.setSoluotThich(baiVietmoi.getSoluotThich()+1);
            baiVietRepo.save(baiVietmoi);
            nguoiDungThichBaiVietRepo.save(nguoiDungThichBaiViet);
        }
        return "Thành công!";

    }
    private void updateLike(NguoiDungThichBaiViet nguoiDungThichBaiViet){
        if(nguoiDungThichBaiViet.getDaXoa() == 0)
            nguoiDungThichBaiViet.setDaXoa(1);
        else
            nguoiDungThichBaiViet.setDaXoa(0);

        BaiViet baiViet = baiVietRepo.findById(nguoiDungThichBaiViet.getBaiViet().getId()).get();
        if(nguoiDungThichBaiViet.getDaXoa() == 1)
            baiViet.setSoluotThich(baiViet.getSoluotThich()-1);
        else
            baiViet.setSoluotThich(baiViet.getSoluotThich()+1);
        baiVietRepo.save(baiViet);
    }
}
