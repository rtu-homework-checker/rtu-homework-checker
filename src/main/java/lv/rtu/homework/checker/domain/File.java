package lv.rtu.homework.checker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A File.
 */
@Entity
@Table(name = "file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modifiedat")
    private Instant modifiedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Size(max = 128)
    @Column(name = "filename", length = 128, nullable = false)
    private String filename;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "files", allowSetters = true)
    private Task task;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "files", allowSetters = true)
    private Variant variant;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "files", allowSetters = true)
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getModifiedat() {
        return modifiedat;
    }

    public File modifiedat(Instant modifiedat) {
        this.modifiedat = modifiedat;
        return this;
    }

    public void setModifiedat(Instant modifiedat) {
        this.modifiedat = modifiedat;
    }

    public Instant getDeletedat() {
        return deletedat;
    }

    public File deletedat(Instant deletedat) {
        this.deletedat = deletedat;
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Instant getCreatedat() {
        return createdat;
    }

    public File createdat(Instant createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public String getFilename() {
        return filename;
    }

    public File filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Task getTask() {
        return task;
    }

    public File task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Variant getVariant() {
        return variant;
    }

    public File variant(Variant variant) {
        this.variant = variant;
        return this;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public Student getStudent() {
        return student;
    }

    public File student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof File)) {
            return false;
        }
        return id != null && id.equals(((File) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "File{" +
            "id=" + getId() +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", filename='" + getFilename() + "'" +
            "}";
    }
}
