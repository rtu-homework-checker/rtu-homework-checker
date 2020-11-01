package lv.rtu.homework.checker.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import lv.rtu.homework.checker.domain.File;
import lv.rtu.homework.checker.domain.*; // for static metamodels
import lv.rtu.homework.checker.repository.FileRepository;
import lv.rtu.homework.checker.service.dto.FileCriteria;
import lv.rtu.homework.checker.service.dto.FileDTO;
import lv.rtu.homework.checker.service.mapper.FileMapper;

/**
 * Service for executing complex queries for {@link File} entities in the database.
 * The main input is a {@link FileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FileDTO} or a {@link Page} of {@link FileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FileQueryService extends QueryService<File> {

    private final Logger log = LoggerFactory.getLogger(FileQueryService.class);

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    public FileQueryService(FileRepository fileRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    /**
     * Return a {@link List} of {@link FileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FileDTO> findByCriteria(FileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<File> specification = createSpecification(criteria);
        return fileMapper.toDto(fileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FileDTO> findByCriteria(FileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<File> specification = createSpecification(criteria);
        return fileRepository.findAll(specification, page)
            .map(fileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<File> specification = createSpecification(criteria);
        return fileRepository.count(specification);
    }

    /**
     * Function to convert {@link FileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<File> createSpecification(FileCriteria criteria) {
        Specification<File> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), File_.id));
            }
            if (criteria.getModifiedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedat(), File_.modifiedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), File_.deletedat));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), File_.createdat));
            }
            if (criteria.getFilename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFilename(), File_.filename));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskId(),
                    root -> root.join(File_.task, JoinType.LEFT).get(Task_.id)));
            }
            if (criteria.getVariantId() != null) {
                specification = specification.and(buildSpecification(criteria.getVariantId(),
                    root -> root.join(File_.variant, JoinType.LEFT).get(Variant_.id)));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildSpecification(criteria.getStudentId(),
                    root -> root.join(File_.student, JoinType.LEFT).get(Student_.id)));
            }
        }
        return specification;
    }
}
