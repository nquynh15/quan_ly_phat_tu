package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.BaiViet;
import QuanlyPhatTu.Entities.BinhLuanBaiViet;
import QuanlyPhatTu.Repositories.BaiVietRepo;
import QuanlyPhatTu.Repositories.BinhLuanBaiVietRepo;
import QuanlyPhatTu.Repositories.PhatTuRepo;
import QuanlyPhatTu.Request.ReUpdateComment;
import QuanlyPhatTu.Request.RequestComment;
import QuanlyPhatTu.Security.Service.UserDetailsImpl;
import QuanlyPhatTu.Services.Interfaces.IBinhLuanBaiViet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BinhLuanService implements IBinhLuanBaiViet {
    @Autowired
    BinhLuanBaiVietRepo binhLuanBaiVietRepo;
    @Autowired
    BaiVietRepo baiVietRepo;
    @Autowired
    PhatTuRepo phatTuRepo;

    @Override
    public String themBinhLuan(RequestComment requestComment) {
        Optional<BaiViet> baiVietTest = baiVietRepo.findById(requestComment.getBaivietID());
        if(baiVietTest.isEmpty())
            return "Bài viết không tồn tại!";
        //Lấy Id của người đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        int userId = currentUser.getId();

        BinhLuanBaiViet binhLuanBaiViet = new BinhLuanBaiViet();
        binhLuanBaiViet.setBaiViet(baiVietTest.get());
        binhLuanBaiViet.setPhatTu(phatTuRepo.findById(userId).get());
        binhLuanBaiViet.setBinhLuan(requestComment.getText());
        binhLuanBaiViet.setSoLuotThich(0);
        binhLuanBaiViet.setThoigianTao(LocalDate.now());
        binhLuanBaiViet.setThoigianCapNhat(LocalDate.now());
        binhLuanBaiViet.setDaXoa(0);
        demBinhLuan(binhLuanBaiViet);
        binhLuanBaiVietRepo.save(binhLuanBaiViet);

        return "Đã bình luận thành công";
    }
    private void demBinhLuan(BinhLuanBaiViet binhLuanBaiViet){
        BaiViet baiViet = baiVietRepo.findById(binhLuanBaiViet.getBaiViet().getId()).get();
        if(binhLuanBaiViet.getDaXoa() == 1)
            baiViet.setSoluotBinhluan(baiViet.getSoluotBinhluan()-1);
        else
            baiViet.setSoluotBinhluan(baiViet.getSoluotBinhluan()+1);
        baiVietRepo.save(baiViet);

    }
    @Override
    public String suaBinhLuan(ReUpdateComment reUpdateComment) {
        Optional<BinhLuanBaiViet> binhLuanBaiVietTest = binhLuanBaiVietRepo.findById(reUpdateComment.binhluanID);
        if(binhLuanBaiVietTest.isEmpty())
            return "Bình luận không tồn tại!";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
//        int userId = currentUser.getId();

        BinhLuanBaiViet binhLuanBaiViet = binhLuanBaiVietTest.get();
        binhLuanBaiViet.setBinhLuan(reUpdateComment.text);
        binhLuanBaiViet.setThoigianCapNhat(LocalDate.now());
        binhLuanBaiVietRepo.save(binhLuanBaiViet);

        return "Sửa bình luận thành công!";
    }

    @Override
    public String xoaBinhLuan(int binhluanID) {
        Optional<BinhLuanBaiViet> binhLuanBaiVietTest = binhLuanBaiVietRepo.findById(binhluanID);
        if(binhLuanBaiVietTest.isEmpty())
            return "Bình luận không tồn tại!";
        binhLuanBaiVietRepo.delete(binhLuanBaiVietTest.get());
        demBinhLuan(binhLuanBaiVietTest.get());

        return "Đã xóa bình luận";
    }
}
