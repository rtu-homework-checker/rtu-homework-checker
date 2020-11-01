package lv.rtu.homework.checker.service;

import lv.rtu.homework.checker.domain.Scenarioexpectedoutput;
import lv.rtu.homework.checker.repository.ScenarioexpectedoutputRepository;
import lv.rtu.homework.checker.service.dto.ScenarioexpectedoutputDTO;
import lv.rtu.homework.checker.service.mapper.ScenarioexpectedoutputMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Scenarioexpectedoutput}.
 */
@Service
@Transactional
public class ScenarioexpectedoutputService {

    private final Logger log = LoggerFactory.getLogger(ScenarioexpectedoutputService.class);

    private final ScenarioexpectedoutputRepository scenarioexpectedoutputRepository;

    private final ScenarioexpectedoutputMapper scenarioexpectedoutputMapper;

    public ScenarioexpectedoutputService(ScenarioexpectedoutputRepository scenarioexpectedoutputRepository, ScenarioexpectedoutputMapper scenarioexpectedoutputMapper) {
        this.scenarioexpectedoutputRepository = scenarioexpectedoutputRepository;
        this.scenarioexpectedoutputMapper = scenarioexpectedoutputMapper;
    }

    /**
     * Save a scenarioexpectedoutput.
     *
     * @param scenarioexpectedoutputDTO the entity to save.
     * @return the persisted entity.
     */
    public ScenarioexpectedoutputDTO save(ScenarioexpectedoutputDTO scenarioexpectedoutputDTO) {
        log.debug("Request to save Scenarioexpectedoutput : {}", scenarioexpectedoutputDTO);
        Scenarioexpectedoutput scenarioexpectedoutput = scenarioexpectedoutputMapper.toEntity(scenarioexpectedoutputDTO);
        scenarioexpectedoutput = scenarioexpectedoutputRepository.save(scenarioexpectedoutput);
        return scenarioexpectedoutputMapper.toDto(scenarioexpectedoutput);
    }

    /**
     * Get all the scenarioexpectedoutputs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ScenarioexpectedoutputDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Scenarioexpectedoutputs");
        return scenarioexpectedoutputRepository.findAll(pageable)
            .map(scenarioexpectedoutputMapper::toDto);
    }


    /**
     * Get one scenarioexpectedoutput by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScenarioexpectedoutputDTO> findOne(Long id) {
        log.debug("Request to get Scenarioexpectedoutput : {}", id);
        return scenarioexpectedoutputRepository.findById(id)
            .map(scenarioexpectedoutputMapper::toDto);
    }

    /**
     * Delete the scenarioexpectedoutput by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Scenarioexpectedoutput : {}", id);
        scenarioexpectedoutputRepository.deleteById(id);
    }
}
