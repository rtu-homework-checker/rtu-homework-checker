package lv.rtu.homework.checker.repository;

import lv.rtu.homework.checker.domain.Scenarioexpectedoutput;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Scenarioexpectedoutput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScenarioexpectedoutputRepository extends JpaRepository<Scenarioexpectedoutput, Long>, JpaSpecificationExecutor<Scenarioexpectedoutput> {
}
