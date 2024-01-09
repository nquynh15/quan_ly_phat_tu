package QuanlyPhatTu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import QuanlyPhatTu.Entities.DaoTrang;

@Repository
public interface DaoTrangRepo extends JpaRepository<DaoTrang, Integer> {
}
