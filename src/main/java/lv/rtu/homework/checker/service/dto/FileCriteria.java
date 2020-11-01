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
 * Criteria class for the {@link lv.rtu.homework.checker.domain.File} entity. This class is used
 * in {@link lv.rtu.homework.checker.web.rest.FileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter modifiedat;

    private InstantFilter deletedat;

    private InstantFilter createdat;

    private StringFilter filename;

    private LongFilter taskId;

    private LongFilter variantId;

    private LongFilter studentId;

    public FileCriteria() {
    }

    public FileCriteria(FileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.modifiedat = other.modifiedat == null ? null : other.modifiedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.filename = other.filename == null ? null : other.filename.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.variantId = other.variantId == null ? null : other.variantId.copy();
        this.studentId = other.studentId == null ? null : other.studentId.copy();
    }

    @Override
    public FileCriteria copy() {
        return new FileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public StringFilter getFilename() {
        return filename;
    }

    public void setFilename(StringFilter filename) {
        this.filename = filename;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public LongFilter getVariantId() {
        return variantId;
    }

    public void setVariantId(LongFilter variantId) {
        this.variantId = variantId;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FileCriteria that = (FileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(modifiedat, that.modifiedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(filename, that.filename) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(variantId, that.variantId) &&
            Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        modifiedat,
        deletedat,
        createdat,
        filename,
        taskId,
        variantId,
        studentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (modifiedat != null ? "modifiedat=" + modifiedat + ", " : "") +
                (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
                (createdat != null ? "createdat=" + createdat + ", " : "") +
                (filename != null ? "filename=" + filename + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (variantId != null ? "variantId=" + variantId + ", " : "") +
                (studentId != null ? "studentId=" + studentId + ", " : "") +
            "}";
    }

}
