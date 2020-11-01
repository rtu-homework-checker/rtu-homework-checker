package lv.rtu.homework.checker.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link lv.rtu.homework.checker.domain.Variant} entity.
 */
public class VariantDTO implements Serializable {
    
    private Long id;

    @Size(max = 128)
    private String title;

    private Instant modifiedat;

    private Instant deletedat;

    @NotNull
    private Instant createdat;


    private Long taskId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariantDTO)) {
            return false;
        }

        return id != null && id.equals(((VariantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VariantDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", taskId=" + getTaskId() +
            "}";
    }
}
