package lv.rtu.homework.checker.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VarianttestscenarioMapperTest {

    private VarianttestscenarioMapper varianttestscenarioMapper;

    @BeforeEach
    public void setUp() {
        varianttestscenarioMapper = new VarianttestscenarioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(varianttestscenarioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(varianttestscenarioMapper.fromId(null)).isNull();
    }
}
