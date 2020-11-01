package lv.rtu.homework.checker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import lv.rtu.homework.checker.web.rest.TestUtil;

public class ScenarioscoreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scenarioscore.class);
        Scenarioscore scenarioscore1 = new Scenarioscore();
        scenarioscore1.setId(1L);
        Scenarioscore scenarioscore2 = new Scenarioscore();
        scenarioscore2.setId(scenarioscore1.getId());
        assertThat(scenarioscore1).isEqualTo(scenarioscore2);
        scenarioscore2.setId(2L);
        assertThat(scenarioscore1).isNotEqualTo(scenarioscore2);
        scenarioscore1.setId(null);
        assertThat(scenarioscore1).isNotEqualTo(scenarioscore2);
    }
}
