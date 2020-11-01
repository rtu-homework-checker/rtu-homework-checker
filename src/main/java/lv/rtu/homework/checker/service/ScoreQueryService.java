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

import lv.rtu.homework.checker.domain.Score;
import lv.rtu.homework.checker.domain.*; // for static metamodels
import lv.rtu.homework.checker.repository.ScoreRepository;
import lv.rtu.homework.checker.service.dto.ScoreCriteria;
import lv.rtu.homework.checker.service.dto.ScoreDTO;
import lv.rtu.homework.checker.service.mapper.ScoreMapper;

/**
 * Service for executing complex queries for {@link Score} entities in the database.
 * The main input is a {@link ScoreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScoreDTO} or a {@link Page} of {@link ScoreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScoreQueryService extends QueryService<Score> {

    private final Logger log = LoggerFactory.getLogger(ScoreQueryService.class);

    private final ScoreRepository scoreRepository;

    private final ScoreMapper scoreMapper;

    public ScoreQueryService(ScoreRepository scoreRepository, ScoreMapper scoreMapper) {
        this.scoreRepository = scoreRepository;
        this.scoreMapper = scoreMapper;
    }

    /**
     * Return a {@link List} of {@link ScoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScoreDTO> findByCriteria(ScoreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Score> specification = createSpecification(criteria);
        return scoreMapper.toDto(scoreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ScoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScoreDTO> findByCriteria(ScoreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Score> specification = createSpecification(criteria);
        return scoreRepository.findAll(specification, page)
            .map(scoreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScoreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Score> specification = createSpecification(criteria);
        return scoreRepository.count(specification);
    }

    /**
     * Function to convert {@link ScoreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Score> createSpecification(ScoreCriteria criteria) {
        Specification<Score> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Score_.id));
            }
            if (criteria.getIspassed() != null) {
                specification = specification.and(buildSpecification(criteria.getIspassed(), Score_.ispassed));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Score_.createdat));
            }
            if (criteria.getModifiedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedat(), Score_.modifiedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), Score_.deletedat));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildSpecification(criteria.getFileId(),
                    root -> root.join(Score_.file, JoinType.LEFT).get(File_.id)));
            }
            if (criteria.getVariantId() != null) {
                specification = specification.and(buildSpecification(criteria.getVariantId(),
                    root -> root.join(Score_.variant, JoinType.LEFT).get(Variant_.id)));
            }
        }
        return specification;
    }
}
