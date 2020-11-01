package lv.rtu.homework.checker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Varianttestscenario.
 */
@Entity
@Table(name = "varianttestscenario")
public class Varianttestscenario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 1024)
    @Column(name = "input", length = 1024, nullable = false)
    private String input;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "varianttestscenarios", allowSetters = true)
    private Variant variant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public Varianttestscenario input(String input) {
        this.input = input;
        return this;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Variant getVariant() {
        return variant;
    }

    public Varianttestscenario variant(Variant variant) {
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
        if (!(o instanceof Varianttestscenario)) {
            return false;
        }
        return id != null && id.equals(((Varianttestscenario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Varianttestscenario{" +
            "id=" + getId() +
            ", input='" + getInput() + "'" +
            "}";
    }
}
