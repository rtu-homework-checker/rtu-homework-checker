package lv.rtu.homework.checker.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link lv.rtu.homework.checker.domain.File} entity.
 */
public class FileDTO implements Serializable {
    
    private Long id;

    private Instant modifiedat;

    private Instant deletedat;

    @NotNull
    private Instant createdat;

    @NotNull
    @Size(max = 128)
    private String filename;


    private Long taskId;

    private Long variantId;

    private Long studentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Instant getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileDTO)) {
            return false;
        }

        return id != null && id.equals(((FileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileDTO{" +
            "id=" + getId() +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", filename='" + getFilename() + "'" +
            ", taskId=" + getTaskId() +
            ", variantId=" + getVariantId() +
            ", studentId=" + getStudentId() +
            "}";
    }
}
