package lv.rtu.homework.checker.service.mapper;


import lv.rtu.homework.checker.domain.*;
import lv.rtu.homework.checker.service.dto.FileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link File} and its DTO {@link FileDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskMapper.class, VariantMapper.class, StudentMapper.class})
public interface FileMapper extends EntityMapper<FileDTO, File> {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "variant.id", target = "variantId")
    @Mapping(source = "student.id", target = "studentId")
    FileDTO toDto(File file);

    @Mapping(source = "taskId", target = "task")
    @Mapping(source = "variantId", target = "variant")
    @Mapping(source = "studentId", target = "student")
    File toEntity(FileDTO fileDTO);

    default File fromId(Long id) {
        if (id == null) {
            return null;
        }
        File file = new File();
        file.setId(id);
        return file;
    }
}
