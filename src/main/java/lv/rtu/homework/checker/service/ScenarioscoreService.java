package lv.rtu.homework.checker.service;

import lv.rtu.homework.checker.domain.Scenarioscore;
import lv.rtu.homework.checker.repository.ScenarioscoreRepository;
import lv.rtu.homework.checker.service.dto.ScenarioscoreDTO;
import lv.rtu.homework.checker.service.mapper.ScenarioscoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Scenarioscore}.
 */
@Service
@Transactional
public class ScenarioscoreService {

    private final Logger log = LoggerFactory.getLogger(ScenarioscoreService.class);

    private final ScenarioscoreRepository scenarioscoreRepository;

    private final ScenarioscoreMapper scenarioscoreMapper;

    public ScenarioscoreService(ScenarioscoreRepository scenarioscoreRepository, ScenarioscoreMapper scenarioscoreMapper) {
        this.scenarioscoreRepository = scenarioscoreRepository;
        this.scenarioscoreMapper = scenarioscoreMapper;
    }

    /**
     * Save a scenarioscore.
     *
     * @param scenarioscoreDTO the entity to save.
     * @return the persisted entity.
     */
    public ScenarioscoreDTO save(ScenarioscoreDTO scenarioscoreDTO) {
        log.debug("Request to save Scenarioscore : {}", scenarioscoreDTO);
        Scenarioscore scenarioscore = scenarioscoreMapper.toEntity(scenarioscoreDTO);
        scenarioscore = scenarioscoreRepository.save(scenarioscore);
        return scenarioscoreMapper.toDto(scenarioscore);
    }

    /**
     * Get all the scenarioscores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ScenarioscoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Scenarioscores");
        return scenarioscoreRepository.findAll(pageable)
            .map(scenarioscoreMapper::toDto);
    }


    /**
     * Get one scenarioscore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScenarioscoreDTO> findOne(Long id) {
        log.debug("Request to get Scenarioscore : {}", id);
        return scenarioscoreRepository.findById(id)
            .map(scenarioscoreMapper::toDto);
    }

    /**
     * Delete the scenarioscore by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Scenarioscore : {}", id);
        scenarioscoreRepository.deleteById(id);
    }
}
