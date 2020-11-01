package lv.rtu.homework.checker.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link lv.rtu.homework.checker.domain.Varianttestscenario} entity.
 */
public class VarianttestscenarioDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 1024)
    private String input;


    private Long variantId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VarianttestscenarioDTO)) {
            return false;
        }

        return id != null && id.equals(((VarianttestscenarioDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VarianttestscenarioDTO{" +
            "id=" + getId() +
            ", input='" + getInput() + "'" +
            ", variantId=" + getVariantId() +
            "}";
    }
}
