package lv.rtu.homework.checker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "title", length = 128, nullable = false)
    private String title;

    @NotNull
    @Column(name = "hasvariants", nullable = false)
    private Boolean hasvariants;

    @Column(name = "modifiedat")
    private Instant modifiedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @Column(name = "createdat")
    private Instant createdat;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Task title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isHasvariants() {
        return hasvariants;
    }

    public Task hasvariants(Boolean hasvariants) {
        this.hasvariants = hasvariants;
        return this;
    }

    public void setHasvariants(Boolean hasvariants) {
        this.hasvariants = hasvariants;
    }

    public Instant getModifiedat() {
        return modifiedat;
    }

    public Task modifiedat(Instant modifiedat) {
        this.modifiedat = modifiedat;
        return this;
    }

    public void setModifiedat(Instant modifiedat) {
        this.modifiedat = modifiedat;
    }

    public Instant getDeletedat() {
        return deletedat;
    }

    public Task deletedat(Instant deletedat) {
        this.deletedat = deletedat;
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Instant getCreatedat() {
        return createdat;
    }

    public Task createdat(Instant createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public User getUser() {
        return user;
    }

    public Task user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", hasvariants='" + isHasvariants() + "'" +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
