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

import lv.rtu.homework.checker.domain.Variant;
import lv.rtu.homework.checker.domain.*; // for static metamodels
import lv.rtu.homework.checker.repository.VariantRepository;
import lv.rtu.homework.checker.service.dto.VariantCriteria;
import lv.rtu.homework.checker.service.dto.VariantDTO;
import lv.rtu.homework.checker.service.mapper.VariantMapper;

/**
 * Service for executing complex queries for {@link Variant} entities in the database.
 * The main input is a {@link VariantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VariantDTO} or a {@link Page} of {@link VariantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VariantQueryService extends QueryService<Variant> {

    private final Logger log = LoggerFactory.getLogger(VariantQueryService.class);

    private final VariantRepository variantRepository;

    private final VariantMapper variantMapper;

    public VariantQueryService(VariantRepository variantRepository, VariantMapper variantMapper) {
        this.variantRepository = variantRepository;
        this.variantMapper = variantMapper;
    }

    /**
     * Return a {@link List} of {@link VariantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VariantDTO> findByCriteria(VariantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Variant> specification = createSpecification(criteria);
        return variantMapper.toDto(variantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VariantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VariantDTO> findByCriteria(VariantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Variant> specification = createSpecification(criteria);
        return variantRepository.findAll(specification, page)
            .map(variantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VariantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Variant> specification = createSpecification(criteria);
        return variantRepository.count(specification);
    }

    /**
     * Function to convert {@link VariantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Variant> createSpecification(VariantCriteria criteria) {
        Specification<Variant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Variant_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Variant_.title));
            }
            if (criteria.getModifiedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedat(), Variant_.modifiedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), Variant_.deletedat));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Variant_.createdat));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskId(),
                    root -> root.join(Variant_.task, JoinType.LEFT).get(Task_.id)));
            }
        }
        return specification;
    }
}
