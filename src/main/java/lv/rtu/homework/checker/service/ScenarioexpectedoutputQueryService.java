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

import lv.rtu.homework.checker.domain.Scenarioexpectedoutput;
import lv.rtu.homework.checker.domain.*; // for static metamodels
import lv.rtu.homework.checker.repository.ScenarioexpectedoutputRepository;
import lv.rtu.homework.checker.service.dto.ScenarioexpectedoutputCriteria;
import lv.rtu.homework.checker.service.dto.ScenarioexpectedoutputDTO;
import lv.rtu.homework.checker.service.mapper.ScenarioexpectedoutputMapper;

/**
 * Service for executing complex queries for {@link Scenarioexpectedoutput} entities in the database.
 * The main input is a {@link ScenarioexpectedoutputCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScenarioexpectedoutputDTO} or a {@link Page} of {@link ScenarioexpectedoutputDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScenarioexpectedoutputQueryService extends QueryService<Scenarioexpectedoutput> {

    private final Logger log = LoggerFactory.getLogger(ScenarioexpectedoutputQueryService.class);

    private final ScenarioexpectedoutputRepository scenarioexpectedoutputRepository;

    private final ScenarioexpectedoutputMapper scenarioexpectedoutputMapper;

    public ScenarioexpectedoutputQueryService(ScenarioexpectedoutputRepository scenarioexpectedoutputRepository, ScenarioexpectedoutputMapper scenarioexpectedoutputMapper) {
        this.scenarioexpectedoutputRepository = scenarioexpectedoutputRepository;
        this.scenarioexpectedoutputMapper = scenarioexpectedoutputMapper;
    }

    /**
     * Return a {@link List} of {@link ScenarioexpectedoutputDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScenarioexpectedoutputDTO> findByCriteria(ScenarioexpectedoutputCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Scenarioexpectedoutput> specification = createSpecification(criteria);
        return scenarioexpectedoutputMapper.toDto(scenarioexpectedoutputRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ScenarioexpectedoutputDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScenarioexpectedoutputDTO> findByCriteria(ScenarioexpectedoutputCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Scenarioexpectedoutput> specification = createSpecification(criteria);
        return scenarioexpectedoutputRepository.findAll(specification, page)
            .map(scenarioexpectedoutputMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScenarioexpectedoutputCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Scenarioexpectedoutput> specification = createSpecification(criteria);
        return scenarioexpectedoutputRepository.count(specification);
    }

    /**
     * Function to convert {@link ScenarioexpectedoutputCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Scenarioexpectedoutput> createSpecification(ScenarioexpectedoutputCriteria criteria) {
        Specification<Scenarioexpectedoutput> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Scenarioexpectedoutput_.id));
            }
            if (criteria.getOutputline() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutputline(), Scenarioexpectedoutput_.outputline));
            }
            if (criteria.getVarianttestscenarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getVarianttestscenarioId(),
                    root -> root.join(Scenarioexpectedoutput_.varianttestscenario, JoinType.LEFT).get(Varianttestscenario_.id)));
            }
        }
        return specification;
    }
}
