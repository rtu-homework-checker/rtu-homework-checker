package lv.rtu.homework.checker.service.mapper;


import lv.rtu.homework.checker.domain.*;
import lv.rtu.homework.checker.service.dto.ScenarioexpectedoutputDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Scenarioexpectedoutput} and its DTO {@link ScenarioexpectedoutputDTO}.
 */
@Mapper(componentModel = "spring", uses = {VarianttestscenarioMapper.class})
public interface ScenarioexpectedoutputMapper extends EntityMapper<ScenarioexpectedoutputDTO, Scenarioexpectedoutput> {

    @Mapping(source = "varianttestscenario.id", target = "varianttestscenarioId")
    ScenarioexpectedoutputDTO toDto(Scenarioexpectedoutput scenarioexpectedoutput);

    @Mapping(source = "varianttestscenarioId", target = "varianttestscenario")
    Scenarioexpectedoutput toEntity(ScenarioexpectedoutputDTO scenarioexpectedoutputDTO);

    default Scenarioexpectedoutput fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scenarioexpectedoutput scenarioexpectedoutput = new Scenarioexpectedoutput();
        scenarioexpectedoutput.setId(id);
        return scenarioexpectedoutput;
    }
}
