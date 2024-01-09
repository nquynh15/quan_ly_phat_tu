package QuanlyPhatTu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import QuanlyPhatTu.Entities.DonDangKy;

@Repository
public interface DonDangKyRepo extends JpaRepository<DonDangKy, Integer> {
}
