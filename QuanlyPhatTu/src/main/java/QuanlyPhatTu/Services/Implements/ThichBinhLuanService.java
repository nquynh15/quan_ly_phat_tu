package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.BinhLuanBaiViet;
import QuanlyPhatTu.Entities.NguoiDungThichBinhLuanBaiViet;
import QuanlyPhatTu.Repositories.BinhLuanBaiVietRepo;
import QuanlyPhatTu.Repositories.NguoiDungThichBinhLuanBVRepo;
import QuanlyPhatTu.Request.RequestLikeComment;
import QuanlyPhatTu.Security.Service.UserDetailsImpl;
import QuanlyPhatTu.Services.Interfaces.IThichBinhLuanBV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThichBinhLuanService implements IThichBinhLuanBV {

    @Autowired
    BinhLuanBaiVietRepo binhLuanBaiVietRepo;
    @Autowired
    NguoiDungThichBinhLuanBVRepo nguoiDungThichBinhLuanBVRepo;

    @Override
    public String thichBinhLuanBaiViet(RequestLikeComment requestLikeComment) {
        Optional<BinhLuanBaiViet> binhLuanBaiVietTest = binhLuanBaiVietRepo.findById(requestLikeComment.binhluanID);
        if(binhLuanBaiVietTest.isEmpty())
            return "Chưa bình luận bài viết này";

        BinhLuanBaiViet binhLuanBaiViet = binhLuanBaiVietTest.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        int userId = currentUser.getId();

        NguoiDungThichBinhLuanBaiViet nguoiDungThichBinhLuanBaiViet = nguoiDungThichBinhLuanBVRepo.findNguoiDungThichBinhLuanBaiVietByBinhLuanBaiVietAndPhatTu(binhLuanBaiViet.getId(), userId);
        if(nguoiDungThichBinhLuanBaiViet != null) {
            updateLike(nguoiDungThichBinhLuanBaiViet);
            nguoiDungThichBinhLuanBVRepo.save(nguoiDungThichBinhLuanBaiViet);
        }

        return "Thành công";
    }
    private void updateLike(NguoiDungThichBinhLuanBaiViet nguoiDungThicBinhLuanBaiViet){
        if(nguoiDungThicBinhLuanBaiViet.getDaXoa() == 0)
            nguoiDungThicBinhLuanBaiViet.setDaXoa(1);
        else
            nguoiDungThicBinhLuanBaiViet.setDaXoa(0);

        BinhLuanBaiViet binhLuanBaiViet = binhLuanBaiVietRepo.findById(nguoiDungThicBinhLuanBaiViet.getBinhLuanBaiViet().getId()).get();
        if(nguoiDungThicBinhLuanBaiViet.getDaXoa() == 1)
            binhLuanBaiViet.setSoLuotThich(binhLuanBaiViet.getSoLuotThich()-1);
        else
            binhLuanBaiViet.setSoLuotThich(binhLuanBaiViet.getSoLuotThich()+1);

    }
}
