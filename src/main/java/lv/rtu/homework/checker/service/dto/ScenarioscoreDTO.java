package lv.rtu.homework.checker.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link lv.rtu.homework.checker.domain.Scenarioscore} entity.
 */
public class ScenarioscoreDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Boolean passed;


    private Long scoreId;

    private Long scenarioexpectedoutputId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getScenarioexpectedoutputId() {
        return scenarioexpectedoutputId;
    }

    public void setScenarioexpectedoutputId(Long scenarioexpectedoutputId) {
        this.scenarioexpectedoutputId = scenarioexpectedoutputId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScenarioscoreDTO)) {
            return false;
        }

        return id != null && id.equals(((ScenarioscoreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScenarioscoreDTO{" +
            "id=" + getId() +
            ", passed='" + isPassed() + "'" +
            ", scoreId=" + getScoreId() +
            ", scenarioexpectedoutputId=" + getScenarioexpectedoutputId() +
            "}";
    }
}
