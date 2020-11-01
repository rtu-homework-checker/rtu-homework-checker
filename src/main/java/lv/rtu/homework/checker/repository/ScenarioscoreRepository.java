package lv.rtu.homework.checker.repository;

import lv.rtu.homework.checker.domain.Scenarioscore;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Scenarioscore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScenarioscoreRepository extends JpaRepository<Scenarioscore, Long>, JpaSpecificationExecutor<Scenarioscore> {
}
