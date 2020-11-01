package lv.rtu.homework.checker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Scenarioexpectedoutput.
 */
@Entity
@Table(name = "scenarioexpectedoutput")
public class Scenarioexpectedoutput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 1024)
    @Column(name = "outputline", length = 1024, nullable = false)
    private String outputline;

    @ManyToOne
    @JsonIgnoreProperties(value = "scenarioexpectedoutputs", allowSetters = true)
    private Varianttestscenario varianttestscenario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutputline() {
        return outputline;
    }

    public Scenarioexpectedoutput outputline(String outputline) {
        this.outputline = outputline;
        return this;
    }

    public void setOutputline(String outputline) {
        this.outputline = outputline;
    }

    public Varianttestscenario getVarianttestscenario() {
        return varianttestscenario;
    }

    public Scenarioexpectedoutput varianttestscenario(Varianttestscenario varianttestscenario) {
        this.varianttestscenario = varianttestscenario;
        return this;
    }

    public void setVarianttestscenario(Varianttestscenario varianttestscenario) {
        this.varianttestscenario = varianttestscenario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scenarioexpectedoutput)) {
            return false;
        }
        return id != null && id.equals(((Scenarioexpectedoutput) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scenarioexpectedoutput{" +
            "id=" + getId() +
            ", outputline='" + getOutputline() + "'" +
            "}";
    }
}
