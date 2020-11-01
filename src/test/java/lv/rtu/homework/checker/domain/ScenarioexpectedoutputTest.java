package lv.rtu.homework.checker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import lv.rtu.homework.checker.web.rest.TestUtil;

public class ScenarioexpectedoutputTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scenarioexpectedoutput.class);
        Scenarioexpectedoutput scenarioexpectedoutput1 = new Scenarioexpectedoutput();
        scenarioexpectedoutput1.setId(1L);
        Scenarioexpectedoutput scenarioexpectedoutput2 = new Scenarioexpectedoutput();
        scenarioexpectedoutput2.setId(scenarioexpectedoutput1.getId());
        assertThat(scenarioexpectedoutput1).isEqualTo(scenarioexpectedoutput2);
        scenarioexpectedoutput2.setId(2L);
        assertThat(scenarioexpectedoutput1).isNotEqualTo(scenarioexpectedoutput2);
        scenarioexpectedoutput1.setId(null);
        assertThat(scenarioexpectedoutput1).isNotEqualTo(scenarioexpectedoutput2);
    }
}
