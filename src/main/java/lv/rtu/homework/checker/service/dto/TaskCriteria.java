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
 * Criteria class for the {@link lv.rtu.homework.checker.domain.Task} entity. This class is used
 * in {@link lv.rtu.homework.checker.web.rest.TaskResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tasks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private BooleanFilter hasvariants;

    private InstantFilter modifiedat;

    private InstantFilter deletedat;

    private InstantFilter createdat;

    private LongFilter userId;

    public TaskCriteria() {
    }

    public TaskCriteria(TaskCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.hasvariants = other.hasvariants == null ? null : other.hasvariants.copy();
        this.modifiedat = other.modifiedat == null ? null : other.modifiedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public TaskCriteria copy() {
        return new TaskCriteria(this);
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

    public BooleanFilter getHasvariants() {
        return hasvariants;
    }

    public void setHasvariants(BooleanFilter hasvariants) {
        this.hasvariants = hasvariants;
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

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskCriteria that = (TaskCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(hasvariants, that.hasvariants) &&
            Objects.equals(modifiedat, that.modifiedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        hasvariants,
        modifiedat,
        deletedat,
        createdat,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (hasvariants != null ? "hasvariants=" + hasvariants + ", " : "") +
                (modifiedat != null ? "modifiedat=" + modifiedat + ", " : "") +
                (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
                (createdat != null ? "createdat=" + createdat + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
