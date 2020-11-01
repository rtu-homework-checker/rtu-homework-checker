package lv.rtu.homework.checker.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioscoreMapperTest {

    private ScenarioscoreMapper scenarioscoreMapper;

    @BeforeEach
    public void setUp() {
        scenarioscoreMapper = new ScenarioscoreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scenarioscoreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scenarioscoreMapper.fromId(null)).isNull();
    }
}
