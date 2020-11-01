package lv.rtu.homework.checker.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link lv.rtu.homework.checker.domain.Task} entity.
 */
public class TaskDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 128)
    private String title;

    @NotNull
    private Boolean hasvariants;

    private Instant modifiedat;

    private Instant deletedat;

    private Instant createdat;


    private Long userId;
    
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

    public Boolean isHasvariants() {
        return hasvariants;
    }

    public void setHasvariants(Boolean hasvariants) {
        this.hasvariants = hasvariants;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", hasvariants='" + isHasvariants() + "'" +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
