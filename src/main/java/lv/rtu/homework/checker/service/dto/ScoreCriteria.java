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
 * Criteria class for the {@link lv.rtu.homework.checker.domain.Score} entity. This class is used
 * in {@link lv.rtu.homework.checker.web.rest.ScoreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /scores?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ScoreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter ispassed;

    private InstantFilter createdat;

    private InstantFilter modifiedat;

    private InstantFilter deletedat;

    private LongFilter fileId;

    private LongFilter variantId;

    public ScoreCriteria() {
    }

    public ScoreCriteria(ScoreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ispassed = other.ispassed == null ? null : other.ispassed.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.modifiedat = other.modifiedat == null ? null : other.modifiedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.fileId = other.fileId == null ? null : other.fileId.copy();
        this.variantId = other.variantId == null ? null : other.variantId.copy();
    }

    @Override
    public ScoreCriteria copy() {
        return new ScoreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getIspassed() {
        return ispassed;
    }

    public void setIspassed(BooleanFilter ispassed) {
        this.ispassed = ispassed;
    }

    public InstantFilter getCreatedat() {
        return createdat;
    }

    public void setCreatedat(InstantFilter createdat) {
        this.createdat = createdat;
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

    public LongFilter getFileId() {
        return fileId;
    }

    public void setFileId(LongFilter fileId) {
        this.fileId = fileId;
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
        final ScoreCriteria that = (ScoreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ispassed, that.ispassed) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(modifiedat, that.modifiedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(fileId, that.fileId) &&
            Objects.equals(variantId, that.variantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ispassed,
        createdat,
        modifiedat,
        deletedat,
        fileId,
        variantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScoreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ispassed != null ? "ispassed=" + ispassed + ", " : "") +
                (createdat != null ? "createdat=" + createdat + ", " : "") +
                (modifiedat != null ? "modifiedat=" + modifiedat + ", " : "") +
                (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
                (fileId != null ? "fileId=" + fileId + ", " : "") +
                (variantId != null ? "variantId=" + variantId + ", " : "") +
            "}";
    }

}
