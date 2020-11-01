package lv.rtu.homework.checker.service.mapper;


import lv.rtu.homework.checker.domain.*;
import lv.rtu.homework.checker.service.dto.VariantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Variant} and its DTO {@link VariantDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface VariantMapper extends EntityMapper<VariantDTO, Variant> {

    @Mapping(source = "task.id", target = "taskId")
    VariantDTO toDto(Variant variant);

    @Mapping(source = "taskId", target = "task")
    Variant toEntity(VariantDTO variantDTO);

    default Variant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Variant variant = new Variant();
        variant.setId(id);
        return variant;
    }
}
