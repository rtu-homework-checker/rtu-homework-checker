package lv.rtu.homework.checker.repository;

import lv.rtu.homework.checker.domain.Variant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Variant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariantRepository extends JpaRepository<Variant, Long>, JpaSpecificationExecutor<Variant> {
}
