package lv.rtu.homework.checker.repository;

import lv.rtu.homework.checker.domain.Score;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Score entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScoreRepository extends JpaRepository<Score, Long>, JpaSpecificationExecutor<Score> {
}
