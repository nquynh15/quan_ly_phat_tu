package QuanlyPhatTu.Controllers;

import QuanlyPhatTu.Entities.PhatTu;
import QuanlyPhatTu.Repositories.PhatTuRepo;
import QuanlyPhatTu.Request.RequestChangePass;
import QuanlyPhatTu.Security.Service.UserDetailsImpl;
import QuanlyPhatTu.Services.CloudinaryService;
import QuanlyPhatTu.Services.Implements.PhatTuService;
import QuanlyPhatTu.plugin.Email;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/quan-ly-phat-tu")
public class PhattuController {
    @Autowired
    private PhatTuRepo phatTuRepo;
    @Autowired
    private PhatTuService phatTuService;

    private final CloudinaryService cloudinaryService;
    public PhattuController(CloudinaryService cloudinaryService){
        this.cloudinaryService = cloudinaryService;
    }

    private Email email;

    @GetMapping(value = "/tim")
    public Optional<PhatTu> tim(@RequestParam String tenTaiKhoan){
        return phatTuService.LayDS(tenTaiKhoan);
    }

    @PutMapping(value = "/change-password")
    public String changePassword(@RequestBody RequestChangePass requestChangePass){
        return phatTuService.ChangePassword(requestChangePass);
    }

    @GetMapping(value = "/forget-password")
    public String forgetPasword(@RequestParam String username){
        return phatTuService.ForgetPassword(username);
    }

    @GetMapping(value = "/filter-by-username")
    public List<PhatTu> filterByUsername(@RequestParam String username){
        Pageable page = PageRequest.of(0, 20);
        return phatTuService.filterByUsername(username, page);
    }
    @GetMapping(value = "/filter-by-email")
    public List<PhatTu> filterByEmail(@RequestParam String email){
        Pageable page = PageRequest.of(0, 20);
        return phatTuService.filterByEmail(email, page);
    }
    @GetMapping(value = "/filter-by-gender")
    public List<PhatTu> filterByGender(@RequestParam String gender){
        Pageable page = PageRequest.of(0, 20);
        return phatTuService.filterByGender(gender, page);
    }
    @PostMapping(value = "/upload-image")
    public ResponseEntity<?> upLoadImage(@ModelAttribute MultipartFile file){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        int userId = currentUser.getId();
        Optional<PhatTu> phatTuTest = phatTuRepo.findById(userId);
        if(phatTuTest.isEmpty())
            return ResponseEntity.badRequest().body("user is not found!");

//        Luu image vao folder uploads
//        String fileName = file.getOriginalFilename();
//        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
//
//        //lấy đg dẫn đến thư mục lưu trữ
//        Path uploadDir = Paths.get("uploads");
//
//        //kiểm tra thư mục và tạo thư mục nếu ko tồn tại
//        if(!Files.exists(uploadDir)) {
//            try {
//                Files.createDirectories(uploadDir);
//
//            } catch (IOException e) {
//                return ResponseEntity.badRequest().body("Can't create folder!");
//            }
//        }
//        //đg dẫn đến file
//        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
//        try {
//            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().body("Can't save file!");
//        }
        PhatTu phatTu = phatTuTest.get();
        //phatTu.setAvatar(uniqueFileName);
        Map data  = cloudinaryService.upload(file);
        phatTu.setAvatar(data.get("secure_url").toString());
        phatTuRepo.save(phatTu);


        return ResponseEntity.ok("File upload successfully!");
    }

    @GetMapping(value = "/images/{imageName}")
    public ResponseEntity<String> viewImage(@RequestParam("image") MultipartFile file){
//        try{
//            java.nio.file.Path imagePath = Paths.get("uploads/" + imageName);
//            UrlResource resource = new UrlResource(imagePath.toUri());
//            if(resource.exists()){
//                return ResponseEntity.ok()
//                        .contentType(MediaType.IMAGE_JPEG)
//                        .body(resource);
//            }
//            else{
//                return ResponseEntity.notFound().build();
//            }
//        }catch(Exception e){
//            return ResponseEntity.notFound().build();
//        }
        Map data = this.cloudinaryService.upload(file);
        return new ResponseEntity<>(data.get("secure_url").toString(), HttpStatusCode.valueOf(HttpStatus.SC_OK));
    }
}
