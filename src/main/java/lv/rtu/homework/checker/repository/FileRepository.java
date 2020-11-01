package lv.rtu.homework.checker.repository;

import lv.rtu.homework.checker.domain.File;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the File entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileRepository extends JpaRepository<File, Long>, JpaSpecificationExecutor<File> {
}
