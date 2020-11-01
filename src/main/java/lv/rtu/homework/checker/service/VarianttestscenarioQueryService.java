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

import lv.rtu.homework.checker.domain.Varianttestscenario;
import lv.rtu.homework.checker.domain.*; // for static metamodels
import lv.rtu.homework.checker.repository.VarianttestscenarioRepository;
import lv.rtu.homework.checker.service.dto.VarianttestscenarioCriteria;
import lv.rtu.homework.checker.service.dto.VarianttestscenarioDTO;
import lv.rtu.homework.checker.service.mapper.VarianttestscenarioMapper;

/**
 * Service for executing complex queries for {@link Varianttestscenario} entities in the database.
 * The main input is a {@link VarianttestscenarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VarianttestscenarioDTO} or a {@link Page} of {@link VarianttestscenarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VarianttestscenarioQueryService extends QueryService<Varianttestscenario> {

    private final Logger log = LoggerFactory.getLogger(VarianttestscenarioQueryService.class);

    private final VarianttestscenarioRepository varianttestscenarioRepository;

    private final VarianttestscenarioMapper varianttestscenarioMapper;

    public VarianttestscenarioQueryService(VarianttestscenarioRepository varianttestscenarioRepository, VarianttestscenarioMapper varianttestscenarioMapper) {
        this.varianttestscenarioRepository = varianttestscenarioRepository;
        this.varianttestscenarioMapper = varianttestscenarioMapper;
    }

    /**
     * Return a {@link List} of {@link VarianttestscenarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VarianttestscenarioDTO> findByCriteria(VarianttestscenarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Varianttestscenario> specification = createSpecification(criteria);
        return varianttestscenarioMapper.toDto(varianttestscenarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VarianttestscenarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VarianttestscenarioDTO> findByCriteria(VarianttestscenarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Varianttestscenario> specification = createSpecification(criteria);
        return varianttestscenarioRepository.findAll(specification, page)
            .map(varianttestscenarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VarianttestscenarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Varianttestscenario> specification = createSpecification(criteria);
        return varianttestscenarioRepository.count(specification);
    }

    /**
     * Function to convert {@link VarianttestscenarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Varianttestscenario> createSpecification(VarianttestscenarioCriteria criteria) {
        Specification<Varianttestscenario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Varianttestscenario_.id));
            }
            if (criteria.getInput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInput(), Varianttestscenario_.input));
            }
            if (criteria.getVariantId() != null) {
                specification = specification.and(buildSpecification(criteria.getVariantId(),
                    root -> root.join(Varianttestscenario_.variant, JoinType.LEFT).get(Variant_.id)));
            }
        }
        return specification;
    }
}
