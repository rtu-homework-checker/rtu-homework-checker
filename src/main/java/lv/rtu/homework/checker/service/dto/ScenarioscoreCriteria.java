package lv.rtu.homework.checker.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link lv.rtu.homework.checker.domain.Scenarioscore} entity. This class is used
 * in {@link lv.rtu.homework.checker.web.rest.ScenarioscoreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /scenarioscores?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ScenarioscoreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter passed;

    private LongFilter scoreId;

    private LongFilter scenarioexpectedoutputId;

    public ScenarioscoreCriteria() {
    }

    public ScenarioscoreCriteria(ScenarioscoreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.passed = other.passed == null ? null : other.passed.copy();
        this.scoreId = other.scoreId == null ? null : other.scoreId.copy();
        this.scenarioexpectedoutputId = other.scenarioexpectedoutputId == null ? null : other.scenarioexpectedoutputId.copy();
    }

    @Override
    public ScenarioscoreCriteria copy() {
        return new ScenarioscoreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getPassed() {
        return passed;
    }

    public void setPassed(BooleanFilter passed) {
        this.passed = passed;
    }

    public LongFilter getScoreId() {
        return scoreId;
    }

    public void setScoreId(LongFilter scoreId) {
        this.scoreId = scoreId;
    }

    public LongFilter getScenarioexpectedoutputId() {
        return scenarioexpectedoutputId;
    }

    public void setScenarioexpectedoutputId(LongFilter scenarioexpectedoutputId) {
        this.scenarioexpectedoutputId = scenarioexpectedoutputId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ScenarioscoreCriteria that = (ScenarioscoreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(passed, that.passed) &&
            Objects.equals(scoreId, that.scoreId) &&
            Objects.equals(scenarioexpectedoutputId, that.scenarioexpectedoutputId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        passed,
        scoreId,
        scenarioexpectedoutputId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScenarioscoreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (passed != null ? "passed=" + passed + ", " : "") +
                (scoreId != null ? "scoreId=" + scoreId + ", " : "") +
                (scenarioexpectedoutputId != null ? "scenarioexpectedoutputId=" + scenarioexpectedoutputId + ", " : "") +
            "}";
    }

}
