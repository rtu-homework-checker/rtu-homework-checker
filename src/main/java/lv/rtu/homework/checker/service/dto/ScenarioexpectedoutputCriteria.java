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
 * Criteria class for the {@link lv.rtu.homework.checker.domain.Scenarioexpectedoutput} entity. This class is used
 * in {@link lv.rtu.homework.checker.web.rest.ScenarioexpectedoutputResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /scenarioexpectedoutputs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ScenarioexpectedoutputCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter outputline;

    private LongFilter varianttestscenarioId;

    public ScenarioexpectedoutputCriteria() {
    }

    public ScenarioexpectedoutputCriteria(ScenarioexpectedoutputCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.outputline = other.outputline == null ? null : other.outputline.copy();
        this.varianttestscenarioId = other.varianttestscenarioId == null ? null : other.varianttestscenarioId.copy();
    }

    @Override
    public ScenarioexpectedoutputCriteria copy() {
        return new ScenarioexpectedoutputCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOutputline() {
        return outputline;
    }

    public void setOutputline(StringFilter outputline) {
        this.outputline = outputline;
    }

    public LongFilter getVarianttestscenarioId() {
        return varianttestscenarioId;
    }

    public void setVarianttestscenarioId(LongFilter varianttestscenarioId) {
        this.varianttestscenarioId = varianttestscenarioId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ScenarioexpectedoutputCriteria that = (ScenarioexpectedoutputCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(outputline, that.outputline) &&
            Objects.equals(varianttestscenarioId, that.varianttestscenarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        outputline,
        varianttestscenarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScenarioexpectedoutputCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (outputline != null ? "outputline=" + outputline + ", " : "") +
                (varianttestscenarioId != null ? "varianttestscenarioId=" + varianttestscenarioId + ", " : "") +
            "}";
    }

}
