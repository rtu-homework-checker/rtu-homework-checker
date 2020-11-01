package lv.rtu.homework.checker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Score.
 */
@Entity
@Table(name = "score")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ispassed", nullable = false)
    private Boolean ispassed;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @Column(name = "modifiedat")
    private Instant modifiedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "scores", allowSetters = true)
    private File file;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "scores", allowSetters = true)
    private Variant variant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIspassed() {
        return ispassed;
    }

    public Score ispassed(Boolean ispassed) {
        this.ispassed = ispassed;
        return this;
    }

    public void setIspassed(Boolean ispassed) {
        this.ispassed = ispassed;
    }

    public Instant getCreatedat() {
        return createdat;
    }

    public Score createdat(Instant createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getModifiedat() {
        return modifiedat;
    }

    public Score modifiedat(Instant modifiedat) {
        this.modifiedat = modifiedat;
        return this;
    }

    public void setModifiedat(Instant modifiedat) {
        this.modifiedat = modifiedat;
    }

    public Instant getDeletedat() {
        return deletedat;
    }

    public Score deletedat(Instant deletedat) {
        this.deletedat = deletedat;
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public File getFile() {
        return file;
    }

    public Score file(File file) {
        this.file = file;
        return this;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Variant getVariant() {
        return variant;
    }

    public Score variant(Variant variant) {
        this.variant = variant;
        return this;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Score)) {
            return false;
        }
        return id != null && id.equals(((Score) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Score{" +
            "id=" + getId() +
            ", ispassed='" + isIspassed() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
