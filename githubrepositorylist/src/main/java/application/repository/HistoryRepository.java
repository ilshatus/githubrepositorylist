package application.repository;

import application.modele.HistoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<HistoryEntity, Long> {
    HistoryEntity findByUsername(String username);
}
