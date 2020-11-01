package lv.rtu.homework.checker.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VariantMapperTest {

    private VariantMapper variantMapper;

    @BeforeEach
    public void setUp() {
        variantMapper = new VariantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(variantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(variantMapper.fromId(null)).isNull();
    }
}
