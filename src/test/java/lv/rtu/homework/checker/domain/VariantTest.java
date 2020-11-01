package lv.rtu.homework.checker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import lv.rtu.homework.checker.web.rest.TestUtil;

public class VariantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Variant.class);
        Variant variant1 = new Variant();
        variant1.setId(1L);
        Variant variant2 = new Variant();
        variant2.setId(variant1.getId());
        assertThat(variant1).isEqualTo(variant2);
        variant2.setId(2L);
        assertThat(variant1).isNotEqualTo(variant2);
        variant1.setId(null);
        assertThat(variant1).isNotEqualTo(variant2);
    }
}
