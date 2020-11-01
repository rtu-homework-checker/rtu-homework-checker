package lv.rtu.homework.checker.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import lv.rtu.homework.checker.web.rest.TestUtil;

public class VarianttestscenarioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VarianttestscenarioDTO.class);
        VarianttestscenarioDTO varianttestscenarioDTO1 = new VarianttestscenarioDTO();
        varianttestscenarioDTO1.setId(1L);
        VarianttestscenarioDTO varianttestscenarioDTO2 = new VarianttestscenarioDTO();
        assertThat(varianttestscenarioDTO1).isNotEqualTo(varianttestscenarioDTO2);
        varianttestscenarioDTO2.setId(varianttestscenarioDTO1.getId());
        assertThat(varianttestscenarioDTO1).isEqualTo(varianttestscenarioDTO2);
        varianttestscenarioDTO2.setId(2L);
        assertThat(varianttestscenarioDTO1).isNotEqualTo(varianttestscenarioDTO2);
        varianttestscenarioDTO1.setId(null);
        assertThat(varianttestscenarioDTO1).isNotEqualTo(varianttestscenarioDTO2);
    }
}
