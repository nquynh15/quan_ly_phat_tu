package QuanlyPhatTu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import QuanlyPhatTu.Entities.BaiViet;
@Repository
public interface BaiVietRepo extends JpaRepository<BaiViet, Integer> {
}
