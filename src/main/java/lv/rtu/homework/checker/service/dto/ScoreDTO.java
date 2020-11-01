package lv.rtu.homework.checker.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link lv.rtu.homework.checker.domain.Score} entity.
 */
public class ScoreDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Boolean ispassed;

    @NotNull
    private Instant createdat;

    private Instant modifiedat;

    private Instant deletedat;


    private Long fileId;

    private Long variantId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIspassed() {
        return ispassed;
    }

    public void setIspassed(Boolean ispassed) {
        this.ispassed = ispassed;
    }

    public Instant getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getModifiedat() {
        return modifiedat;
    }

    public void setModifiedat(Instant modifiedat) {
        this.modifiedat = modifiedat;
    }

    public Instant getDeletedat() {
        return deletedat;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScoreDTO)) {
            return false;
        }

        return id != null && id.equals(((ScoreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScoreDTO{" +
            "id=" + getId() +
            ", ispassed='" + isIspassed() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            ", fileId=" + getFileId() +
            ", variantId=" + getVariantId() +
            "}";
    }
}
