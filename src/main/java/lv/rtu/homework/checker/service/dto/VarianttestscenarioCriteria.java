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
 * Criteria class for the {@link lv.rtu.homework.checker.domain.Varianttestscenario} entity. This class is used
 * in {@link lv.rtu.homework.checker.web.rest.VarianttestscenarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /varianttestscenarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VarianttestscenarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter input;

    private LongFilter variantId;

    public VarianttestscenarioCriteria() {
    }

    public VarianttestscenarioCriteria(VarianttestscenarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.input = other.input == null ? null : other.input.copy();
        this.variantId = other.variantId == null ? null : other.variantId.copy();
    }

    @Override
    public VarianttestscenarioCriteria copy() {
        return new VarianttestscenarioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInput() {
        return input;
    }

    public void setInput(StringFilter input) {
        this.input = input;
    }

    public LongFilter getVariantId() {
        return variantId;
    }

    public void setVariantId(LongFilter variantId) {
        this.variantId = variantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VarianttestscenarioCriteria that = (VarianttestscenarioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(input, that.input) &&
            Objects.equals(variantId, that.variantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        input,
        variantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VarianttestscenarioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (input != null ? "input=" + input + ", " : "") +
                (variantId != null ? "variantId=" + variantId + ", " : "") +
            "}";
    }

}
