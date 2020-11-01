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

import lv.rtu.homework.checker.domain.Scenarioscore;
import lv.rtu.homework.checker.domain.*; // for static metamodels
import lv.rtu.homework.checker.repository.ScenarioscoreRepository;
import lv.rtu.homework.checker.service.dto.ScenarioscoreCriteria;
import lv.rtu.homework.checker.service.dto.ScenarioscoreDTO;
import lv.rtu.homework.checker.service.mapper.ScenarioscoreMapper;

/**
 * Service for executing complex queries for {@link Scenarioscore} entities in the database.
 * The main input is a {@link ScenarioscoreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScenarioscoreDTO} or a {@link Page} of {@link ScenarioscoreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScenarioscoreQueryService extends QueryService<Scenarioscore> {

    private final Logger log = LoggerFactory.getLogger(ScenarioscoreQueryService.class);

    private final ScenarioscoreRepository scenarioscoreRepository;

    private final ScenarioscoreMapper scenarioscoreMapper;

    public ScenarioscoreQueryService(ScenarioscoreRepository scenarioscoreRepository, ScenarioscoreMapper scenarioscoreMapper) {
        this.scenarioscoreRepository = scenarioscoreRepository;
        this.scenarioscoreMapper = scenarioscoreMapper;
    }

    /**
     * Return a {@link List} of {@link ScenarioscoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScenarioscoreDTO> findByCriteria(ScenarioscoreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Scenarioscore> specification = createSpecification(criteria);
        return scenarioscoreMapper.toDto(scenarioscoreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ScenarioscoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScenarioscoreDTO> findByCriteria(ScenarioscoreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Scenarioscore> specification = createSpecification(criteria);
        return scenarioscoreRepository.findAll(specification, page)
            .map(scenarioscoreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScenarioscoreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Scenarioscore> specification = createSpecification(criteria);
        return scenarioscoreRepository.count(specification);
    }

    /**
     * Function to convert {@link ScenarioscoreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Scenarioscore> createSpecification(ScenarioscoreCriteria criteria) {
        Specification<Scenarioscore> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Scenarioscore_.id));
            }
            if (criteria.getPassed() != null) {
                specification = specification.and(buildSpecification(criteria.getPassed(), Scenarioscore_.passed));
            }
            if (criteria.getScoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getScoreId(),
                    root -> root.join(Scenarioscore_.score, JoinType.LEFT).get(Score_.id)));
            }
            if (criteria.getScenarioexpectedoutputId() != null) {
                specification = specification.and(buildSpecification(criteria.getScenarioexpectedoutputId(),
                    root -> root.join(Scenarioscore_.scenarioexpectedoutput, JoinType.LEFT).get(Scenarioexpectedoutput_.id)));
            }
        }
        return specification;
    }
}
