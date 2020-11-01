package lv.rtu.homework.checker.service;

import lv.rtu.homework.checker.domain.Varianttestscenario;
import lv.rtu.homework.checker.repository.VarianttestscenarioRepository;
import lv.rtu.homework.checker.service.dto.VarianttestscenarioDTO;
import lv.rtu.homework.checker.service.mapper.VarianttestscenarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Varianttestscenario}.
 */
@Service
@Transactional
public class VarianttestscenarioService {

    private final Logger log = LoggerFactory.getLogger(VarianttestscenarioService.class);

    private final VarianttestscenarioRepository varianttestscenarioRepository;

    private final VarianttestscenarioMapper varianttestscenarioMapper;

    public VarianttestscenarioService(VarianttestscenarioRepository varianttestscenarioRepository, VarianttestscenarioMapper varianttestscenarioMapper) {
        this.varianttestscenarioRepository = varianttestscenarioRepository;
        this.varianttestscenarioMapper = varianttestscenarioMapper;
    }

    /**
     * Save a varianttestscenario.
     *
     * @param varianttestscenarioDTO the entity to save.
     * @return the persisted entity.
     */
    public VarianttestscenarioDTO save(VarianttestscenarioDTO varianttestscenarioDTO) {
        log.debug("Request to save Varianttestscenario : {}", varianttestscenarioDTO);
        Varianttestscenario varianttestscenario = varianttestscenarioMapper.toEntity(varianttestscenarioDTO);
        varianttestscenario = varianttestscenarioRepository.save(varianttestscenario);
        return varianttestscenarioMapper.toDto(varianttestscenario);
    }

    /**
     * Get all the varianttestscenarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VarianttestscenarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Varianttestscenarios");
        return varianttestscenarioRepository.findAll(pageable)
            .map(varianttestscenarioMapper::toDto);
    }


    /**
     * Get one varianttestscenario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VarianttestscenarioDTO> findOne(Long id) {
        log.debug("Request to get Varianttestscenario : {}", id);
        return varianttestscenarioRepository.findById(id)
            .map(varianttestscenarioMapper::toDto);
    }

    /**
     * Delete the varianttestscenario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Varianttestscenario : {}", id);
        varianttestscenarioRepository.deleteById(id);
    }
}
