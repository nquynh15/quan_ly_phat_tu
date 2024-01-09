package QuanlyPhatTu.Repositories;

import QuanlyPhatTu.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {
     Optional<Token> findAllByPtId(int ptId);
}
