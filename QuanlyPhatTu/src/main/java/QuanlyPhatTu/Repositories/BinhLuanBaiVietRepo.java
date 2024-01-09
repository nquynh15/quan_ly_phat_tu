package QuanlyPhatTu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import QuanlyPhatTu.Entities.BinhLuanBaiViet;

@Repository
public interface BinhLuanBaiVietRepo extends JpaRepository<BinhLuanBaiViet, Integer> {
}
