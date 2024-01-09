package QuanlyPhatTu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import QuanlyPhatTu.Entities.Chua;

@Repository
public interface ChuaRepo extends JpaRepository<Chua, Integer> {
}
