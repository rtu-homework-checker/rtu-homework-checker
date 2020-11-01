package lv.rtu.homework.checker.repository;

import lv.rtu.homework.checker.domain.Varianttestscenario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Varianttestscenario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VarianttestscenarioRepository extends JpaRepository<Varianttestscenario, Long>, JpaSpecificationExecutor<Varianttestscenario> {
}
