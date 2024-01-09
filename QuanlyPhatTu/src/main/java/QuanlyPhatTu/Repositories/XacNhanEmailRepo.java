package QuanlyPhatTu.Repositories;

import QuanlyPhatTu.Entities.XacNhanEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface XacNhanEmailRepo extends JpaRepository<XacNhanEmail, Integer> {
     Optional<XacNhanEmail> findAllByMaXacNhan(String maXacNhan);
}
