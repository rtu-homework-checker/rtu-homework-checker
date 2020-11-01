package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.service.ScenarioscoreService;
import lv.rtu.homework.checker.web.rest.errors.BadRequestAlertException;
import lv.rtu.homework.checker.service.dto.ScenarioscoreDTO;
import lv.rtu.homework.checker.service.dto.ScenarioscoreCriteria;
import lv.rtu.homework.checker.service.ScenarioscoreQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link lv.rtu.homework.checker.domain.Scenarioscore}.
 */
@RestController
@RequestMapping("/api")
public class ScenarioscoreResource {

    private final Logger log = LoggerFactory.getLogger(ScenarioscoreResource.class);

    private static final String ENTITY_NAME = "scenarioscore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScenarioscoreService scenarioscoreService;

    private final ScenarioscoreQueryService scenarioscoreQueryService;

    public ScenarioscoreResource(ScenarioscoreService scenarioscoreService, ScenarioscoreQueryService scenarioscoreQueryService) {
        this.scenarioscoreService = scenarioscoreService;
        this.scenarioscoreQueryService = scenarioscoreQueryService;
    }

    /**
     * {@code POST  /scenarioscores} : Create a new scenarioscore.
     *
     * @param scenarioscoreDTO the scenarioscoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scenarioscoreDTO, or with status {@code 400 (Bad Request)} if the scenarioscore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scenarioscores")
    public ResponseEntity<ScenarioscoreDTO> createScenarioscore(@Valid @RequestBody ScenarioscoreDTO scenarioscoreDTO) throws URISyntaxException {
        log.debug("REST request to save Scenarioscore : {}", scenarioscoreDTO);
        if (scenarioscoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new scenarioscore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScenarioscoreDTO result = scenarioscoreService.save(scenarioscoreDTO);
        return ResponseEntity.created(new URI("/api/scenarioscores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scenarioscores} : Updates an existing scenarioscore.
     *
     * @param scenarioscoreDTO the scenarioscoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scenarioscoreDTO,
     * or with status {@code 400 (Bad Request)} if the scenarioscoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scenarioscoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scenarioscores")
    public ResponseEntity<ScenarioscoreDTO> updateScenarioscore(@Valid @RequestBody ScenarioscoreDTO scenarioscoreDTO) throws URISyntaxException {
        log.debug("REST request to update Scenarioscore : {}", scenarioscoreDTO);
        if (scenarioscoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScenarioscoreDTO result = scenarioscoreService.save(scenarioscoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scenarioscoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scenarioscores} : get all the scenarioscores.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scenarioscores in body.
     */
    @GetMapping("/scenarioscores")
    public ResponseEntity<List<ScenarioscoreDTO>> getAllScenarioscores(ScenarioscoreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Scenarioscores by criteria: {}", criteria);
        Page<ScenarioscoreDTO> page = scenarioscoreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scenarioscores/count} : count all the scenarioscores.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/scenarioscores/count")
    public ResponseEntity<Long> countScenarioscores(ScenarioscoreCriteria criteria) {
        log.debug("REST request to count Scenarioscores by criteria: {}", criteria);
        return ResponseEntity.ok().body(scenarioscoreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /scenarioscores/:id} : get the "id" scenarioscore.
     *
     * @param id the id of the scenarioscoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scenarioscoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scenarioscores/{id}")
    public ResponseEntity<ScenarioscoreDTO> getScenarioscore(@PathVariable Long id) {
        log.debug("REST request to get Scenarioscore : {}", id);
        Optional<ScenarioscoreDTO> scenarioscoreDTO = scenarioscoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scenarioscoreDTO);
    }

    /**
     * {@code DELETE  /scenarioscores/:id} : delete the "id" scenarioscore.
     *
     * @param id the id of the scenarioscoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scenarioscores/{id}")
    public ResponseEntity<Void> deleteScenarioscore(@PathVariable Long id) {
        log.debug("REST request to delete Scenarioscore : {}", id);
        scenarioscoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
