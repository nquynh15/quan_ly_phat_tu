package QuanlyPhatTu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import QuanlyPhatTu.Entities.PhatTu;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhatTuRepo extends JpaRepository<PhatTu, Integer> {
     Optional<PhatTu> findAllByUsername (String username);
}
