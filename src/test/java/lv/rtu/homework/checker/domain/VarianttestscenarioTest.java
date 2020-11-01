package lv.rtu.homework.checker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import lv.rtu.homework.checker.web.rest.TestUtil;

public class VarianttestscenarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Varianttestscenario.class);
        Varianttestscenario varianttestscenario1 = new Varianttestscenario();
        varianttestscenario1.setId(1L);
        Varianttestscenario varianttestscenario2 = new Varianttestscenario();
        varianttestscenario2.setId(varianttestscenario1.getId());
        assertThat(varianttestscenario1).isEqualTo(varianttestscenario2);
        varianttestscenario2.setId(2L);
        assertThat(varianttestscenario1).isNotEqualTo(varianttestscenario2);
        varianttestscenario1.setId(null);
        assertThat(varianttestscenario1).isNotEqualTo(varianttestscenario2);
    }
}
