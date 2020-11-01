package lv.rtu.homework.checker.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import lv.rtu.homework.checker.web.rest.TestUtil;

public class ScenarioscoreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScenarioscoreDTO.class);
        ScenarioscoreDTO scenarioscoreDTO1 = new ScenarioscoreDTO();
        scenarioscoreDTO1.setId(1L);
        ScenarioscoreDTO scenarioscoreDTO2 = new ScenarioscoreDTO();
        assertThat(scenarioscoreDTO1).isNotEqualTo(scenarioscoreDTO2);
        scenarioscoreDTO2.setId(scenarioscoreDTO1.getId());
        assertThat(scenarioscoreDTO1).isEqualTo(scenarioscoreDTO2);
        scenarioscoreDTO2.setId(2L);
        assertThat(scenarioscoreDTO1).isNotEqualTo(scenarioscoreDTO2);
        scenarioscoreDTO1.setId(null);
        assertThat(scenarioscoreDTO1).isNotEqualTo(scenarioscoreDTO2);
    }
}
