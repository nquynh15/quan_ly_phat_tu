package QuanlyPhatTu.Controllers;

import QuanlyPhatTu.Request.ReUpdateComment;
import QuanlyPhatTu.Request.RequestComment;
import QuanlyPhatTu.Services.Implements.BinhLuanService;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/comment")
public class BinhLuanController {
    @Autowired
    BinhLuanService binhLuanService;

    @PostMapping(value = "/create")
    public String themBinhLuan(@RequestBody RequestComment requestComment) {
        return binhLuanService.themBinhLuan(requestComment);
    }

    @PutMapping(value = "/edit")
    public String suaBinhLuan(@RequestBody ReUpdateComment reUpdateComment) {
        return binhLuanService.suaBinhLuan(reUpdateComment);
    }

    @DeleteMapping(value = "/delete")
    public String xoaBinhLuan(@RequestParam int binhluanID){
        return binhLuanService.xoaBinhLuan(binhluanID);
    }
}