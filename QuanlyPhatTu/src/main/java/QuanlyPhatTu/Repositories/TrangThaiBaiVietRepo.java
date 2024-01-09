package QuanlyPhatTu.Repositories;

import QuanlyPhatTu.Entities.TrangthaiBaiViet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrangThaiBaiVietRepo extends JpaRepository<TrangthaiBaiViet, Integer> {
}
