package QuanlyPhatTu.Services.Interfaces;

import QuanlyPhatTu.Request.ReUpdateBaiViet;
import QuanlyPhatTu.Request.RequestBaiViet;

public interface IBaiViet {
    public String taoBaiViet(RequestBaiViet requestBaiViet);
    public String suaBaiViet(ReUpdateBaiViet reUpdateBaiViet);
    public String xoaBaiViet(int baiVietID);
    public String duyetBaiViet(int baiVietID);
}
