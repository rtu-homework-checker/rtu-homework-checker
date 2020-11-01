package lv.rtu.homework.checker.service.mapper;


import lv.rtu.homework.checker.domain.*;
import lv.rtu.homework.checker.service.dto.VarianttestscenarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Varianttestscenario} and its DTO {@link VarianttestscenarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {VariantMapper.class})
public interface VarianttestscenarioMapper extends EntityMapper<VarianttestscenarioDTO, Varianttestscenario> {

    @Mapping(source = "variant.id", target = "variantId")
    VarianttestscenarioDTO toDto(Varianttestscenario varianttestscenario);

    @Mapping(source = "variantId", target = "variant")
    Varianttestscenario toEntity(VarianttestscenarioDTO varianttestscenarioDTO);

    default Varianttestscenario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Varianttestscenario varianttestscenario = new Varianttestscenario();
        varianttestscenario.setId(id);
        return varianttestscenario;
    }
}
