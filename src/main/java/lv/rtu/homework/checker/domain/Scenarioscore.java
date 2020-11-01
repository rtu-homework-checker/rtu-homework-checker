package lv.rtu.homework.checker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Scenarioscore.
 */
@Entity
@Table(name = "scenarioscore")
public class Scenarioscore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "passed", nullable = false)
    private Boolean passed;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "scenarioscores", allowSetters = true)
    private Score score;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "scenarioscores", allowSetters = true)
    private Scenarioexpectedoutput scenarioexpectedoutput;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPassed() {
        return passed;
    }

    public Scenarioscore passed(Boolean passed) {
        this.passed = passed;
        return this;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public Score getScore() {
        return score;
    }

    public Scenarioscore score(Score score) {
        this.score = score;
        return this;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Scenarioexpectedoutput getScenarioexpectedoutput() {
        return scenarioexpectedoutput;
    }

    public Scenarioscore scenarioexpectedoutput(Scenarioexpectedoutput scenarioexpectedoutput) {
        this.scenarioexpectedoutput = scenarioexpectedoutput;
        return this;
    }

    public void setScenarioexpectedoutput(Scenarioexpectedoutput scenarioexpectedoutput) {
        this.scenarioexpectedoutput = scenarioexpectedoutput;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scenarioscore)) {
            return false;
        }
        return id != null && id.equals(((Scenarioscore) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scenarioscore{" +
            "id=" + getId() +
            ", passed='" + isPassed() + "'" +
            "}";
    }
}
