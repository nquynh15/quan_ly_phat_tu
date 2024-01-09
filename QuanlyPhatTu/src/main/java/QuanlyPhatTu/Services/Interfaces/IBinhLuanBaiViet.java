package QuanlyPhatTu.Services.Interfaces;

import QuanlyPhatTu.Request.ReUpdateComment;
import QuanlyPhatTu.Request.RequestBaiViet;
import QuanlyPhatTu.Request.RequestComment;

public interface IBinhLuanBaiViet {
    public String themBinhLuan(RequestComment requestComment);
    public String suaBinhLuan(ReUpdateComment reUpdateComment);
    public String xoaBinhLuan(int binhluanID);

}
