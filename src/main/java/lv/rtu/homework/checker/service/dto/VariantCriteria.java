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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link lv.rtu.homework.checker.domain.Variant} entity. This class is used
 * in {@link lv.rtu.homework.checker.web.rest.VariantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /variants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VariantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private InstantFilter modifiedat;

    private InstantFilter deletedat;

    private InstantFilter createdat;

    private LongFilter taskId;

    public VariantCriteria() {
    }

    public VariantCriteria(VariantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.modifiedat = other.modifiedat == null ? null : other.modifiedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
    }

    @Override
    public VariantCriteria copy() {
        return new VariantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public InstantFilter getModifiedat() {
        return modifiedat;
    }

    public void setModifiedat(InstantFilter modifiedat) {
        this.modifiedat = modifiedat;
    }

    public InstantFilter getDeletedat() {
        return deletedat;
    }

    public void setDeletedat(InstantFilter deletedat) {
        this.deletedat = deletedat;
    }

    public InstantFilter getCreatedat() {
        return createdat;
    }

    public void setCreatedat(InstantFilter createdat) {
        this.createdat = createdat;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VariantCriteria that = (VariantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(modifiedat, that.modifiedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        modifiedat,
        deletedat,
        createdat,
        taskId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VariantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (modifiedat != null ? "modifiedat=" + modifiedat + ", " : "") +
                (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
                (createdat != null ? "createdat=" + createdat + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
            "}";
    }

}
