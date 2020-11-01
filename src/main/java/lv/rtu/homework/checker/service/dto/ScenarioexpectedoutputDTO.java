package lv.rtu.homework.checker.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link lv.rtu.homework.checker.domain.Scenarioexpectedoutput} entity.
 */
public class ScenarioexpectedoutputDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 1024)
    private String outputline;


    private Long varianttestscenarioId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutputline() {
        return outputline;
    }

    public void setOutputline(String outputline) {
        this.outputline = outputline;
    }

    public Long getVarianttestscenarioId() {
        return varianttestscenarioId;
    }

    public void setVarianttestscenarioId(Long varianttestscenarioId) {
        this.varianttestscenarioId = varianttestscenarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScenarioexpectedoutputDTO)) {
            return false;
        }

        return id != null && id.equals(((ScenarioexpectedoutputDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScenarioexpectedoutputDTO{" +
            "id=" + getId() +
            ", outputline='" + getOutputline() + "'" +
            ", varianttestscenarioId=" + getVarianttestscenarioId() +
            "}";
    }
}
