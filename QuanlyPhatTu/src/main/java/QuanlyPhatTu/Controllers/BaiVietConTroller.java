package QuanlyPhatTu.Controllers;

import QuanlyPhatTu.Request.ReUpdateBaiViet;
import QuanlyPhatTu.Request.RequestBaiViet;
import QuanlyPhatTu.Services.Implements.BaiVietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/posts")
public class BaiVietConTroller {
    @Autowired
    BaiVietService baiVietService;

    @PostMapping(value = "/add")
    public String taoBaiViet(@RequestBody RequestBaiViet requestBaiViet){
        return baiVietService.taoBaiViet(requestBaiViet);
    }
    @PutMapping(value = "/update")
    public String suaBaiViet(@RequestBody ReUpdateBaiViet reUpdateBaiViet){
        return baiVietService.suaBaiViet(reUpdateBaiViet);
    }
    @DeleteMapping(value = "/delete")
    public String xoaBaiViet(@RequestParam int baiVietID){
        return baiVietService.xoaBaiViet(baiVietID);
    }
}
