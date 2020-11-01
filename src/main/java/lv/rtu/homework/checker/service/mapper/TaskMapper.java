package lv.rtu.homework.checker.service.mapper;


import lv.rtu.homework.checker.domain.*;
import lv.rtu.homework.checker.service.dto.TaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {

    @Mapping(source = "user.id", target = "userId")
    TaskDTO toDto(Task task);

    @Mapping(source = "userId", target = "user")
    Task toEntity(TaskDTO taskDTO);

    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
