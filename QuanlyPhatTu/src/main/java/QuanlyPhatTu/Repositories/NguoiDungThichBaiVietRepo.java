package QuanlyPhatTu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import QuanlyPhatTu.Entities.NguoiDungThichBaiViet;

import java.util.Optional;

@Repository
public interface NguoiDungThichBaiVietRepo extends JpaRepository<NguoiDungThichBaiViet, Integer> {

    @Query("SELECT nguoi FROM NguoiDungThichBaiViet nguoi WHERE nguoi.baiViet.id = :baivietID AND nguoi.phatTu.id = :phattuID")
    NguoiDungThichBaiViet findNguoiDungThichBaiVietByBaiVietAndPhatTu(@Param("baivietID") int baivietID, @Param("phattuID") int phattuID);

}
