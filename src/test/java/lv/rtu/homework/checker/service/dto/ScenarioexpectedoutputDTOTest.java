package lv.rtu.homework.checker.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import lv.rtu.homework.checker.web.rest.TestUtil;

public class ScenarioexpectedoutputDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScenarioexpectedoutputDTO.class);
        ScenarioexpectedoutputDTO scenarioexpectedoutputDTO1 = new ScenarioexpectedoutputDTO();
        scenarioexpectedoutputDTO1.setId(1L);
        ScenarioexpectedoutputDTO scenarioexpectedoutputDTO2 = new ScenarioexpectedoutputDTO();
        assertThat(scenarioexpectedoutputDTO1).isNotEqualTo(scenarioexpectedoutputDTO2);
        scenarioexpectedoutputDTO2.setId(scenarioexpectedoutputDTO1.getId());
        assertThat(scenarioexpectedoutputDTO1).isEqualTo(scenarioexpectedoutputDTO2);
        scenarioexpectedoutputDTO2.setId(2L);
        assertThat(scenarioexpectedoutputDTO1).isNotEqualTo(scenarioexpectedoutputDTO2);
        scenarioexpectedoutputDTO1.setId(null);
        assertThat(scenarioexpectedoutputDTO1).isNotEqualTo(scenarioexpectedoutputDTO2);
    }
}
