package QuanlyPhatTu.Repositories;

import QuanlyPhatTu.Entities.NguoiDungThichBinhLuanBaiViet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoiDungThichBinhLuanBVRepo extends JpaRepository<NguoiDungThichBinhLuanBaiViet, Integer> {
     @Query("SELECT nguoi FROM NguoiDungThichBinhLuanBaiViet nguoi WHERE nguoi.binhLuanBaiViet.id = :binhLuanID AND nguoi.phatTu.id = :phattuID")
     NguoiDungThichBinhLuanBaiViet findNguoiDungThichBinhLuanBaiVietByBinhLuanBaiVietAndPhatTu(@Param("binhLuanID") int binhLuanID, @Param("phattuID") int phattuID);
}
