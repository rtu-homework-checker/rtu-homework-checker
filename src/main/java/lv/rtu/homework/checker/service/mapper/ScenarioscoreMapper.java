package lv.rtu.homework.checker.service.mapper;


import lv.rtu.homework.checker.domain.*;
import lv.rtu.homework.checker.service.dto.ScenarioscoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Scenarioscore} and its DTO {@link ScenarioscoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {ScoreMapper.class, ScenarioexpectedoutputMapper.class})
public interface ScenarioscoreMapper extends EntityMapper<ScenarioscoreDTO, Scenarioscore> {

    @Mapping(source = "score.id", target = "scoreId")
    @Mapping(source = "scenarioexpectedoutput.id", target = "scenarioexpectedoutputId")
    ScenarioscoreDTO toDto(Scenarioscore scenarioscore);

    @Mapping(source = "scoreId", target = "score")
    @Mapping(source = "scenarioexpectedoutputId", target = "scenarioexpectedoutput")
    Scenarioscore toEntity(ScenarioscoreDTO scenarioscoreDTO);

    default Scenarioscore fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scenarioscore scenarioscore = new Scenarioscore();
        scenarioscore.setId(id);
        return scenarioscore;
    }
}
