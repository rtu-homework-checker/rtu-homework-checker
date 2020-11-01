package lv.rtu.homework.checker.service.mapper;


import lv.rtu.homework.checker.domain.*;
import lv.rtu.homework.checker.service.dto.ScoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Score} and its DTO {@link ScoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {FileMapper.class, VariantMapper.class})
public interface ScoreMapper extends EntityMapper<ScoreDTO, Score> {

    @Mapping(source = "file.id", target = "fileId")
    @Mapping(source = "variant.id", target = "variantId")
    ScoreDTO toDto(Score score);

    @Mapping(source = "fileId", target = "file")
    @Mapping(source = "variantId", target = "variant")
    Score toEntity(ScoreDTO scoreDTO);

    default Score fromId(Long id) {
        if (id == null) {
            return null;
        }
        Score score = new Score();
        score.setId(id);
        return score;
    }
}
