package lv.rtu.homework.checker.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioexpectedoutputMapperTest {

    private ScenarioexpectedoutputMapper scenarioexpectedoutputMapper;

    @BeforeEach
    public void setUp() {
        scenarioexpectedoutputMapper = new ScenarioexpectedoutputMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scenarioexpectedoutputMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scenarioexpectedoutputMapper.fromId(null)).isNull();
    }
}
