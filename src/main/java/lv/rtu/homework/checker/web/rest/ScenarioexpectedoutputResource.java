package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.service.ScenarioexpectedoutputService;
import lv.rtu.homework.checker.web.rest.errors.BadRequestAlertException;
import lv.rtu.homework.checker.service.dto.ScenarioexpectedoutputDTO;
import lv.rtu.homework.checker.service.dto.ScenarioexpectedoutputCriteria;
import lv.rtu.homework.checker.service.ScenarioexpectedoutputQueryService;

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
 * REST controller for managing {@link lv.rtu.homework.checker.domain.Scenarioexpectedoutput}.
 */
@RestController
@RequestMapping("/api")
public class ScenarioexpectedoutputResource {

    private final Logger log = LoggerFactory.getLogger(ScenarioexpectedoutputResource.class);

    private static final String ENTITY_NAME = "scenarioexpectedoutput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScenarioexpectedoutputService scenarioexpectedoutputService;

    private final ScenarioexpectedoutputQueryService scenarioexpectedoutputQueryService;

    public ScenarioexpectedoutputResource(ScenarioexpectedoutputService scenarioexpectedoutputService, ScenarioexpectedoutputQueryService scenarioexpectedoutputQueryService) {
        this.scenarioexpectedoutputService = scenarioexpectedoutputService;
        this.scenarioexpectedoutputQueryService = scenarioexpectedoutputQueryService;
    }

    /**
     * {@code POST  /scenarioexpectedoutputs} : Create a new scenarioexpectedoutput.
     *
     * @param scenarioexpectedoutputDTO the scenarioexpectedoutputDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scenarioexpectedoutputDTO, or with status {@code 400 (Bad Request)} if the scenarioexpectedoutput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scenarioexpectedoutputs")
    public ResponseEntity<ScenarioexpectedoutputDTO> createScenarioexpectedoutput(@Valid @RequestBody ScenarioexpectedoutputDTO scenarioexpectedoutputDTO) throws URISyntaxException {
        log.debug("REST request to save Scenarioexpectedoutput : {}", scenarioexpectedoutputDTO);
        if (scenarioexpectedoutputDTO.getId() != null) {
            throw new BadRequestAlertException("A new scenarioexpectedoutput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScenarioexpectedoutputDTO result = scenarioexpectedoutputService.save(scenarioexpectedoutputDTO);
        return ResponseEntity.created(new URI("/api/scenarioexpectedoutputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scenarioexpectedoutputs} : Updates an existing scenarioexpectedoutput.
     *
     * @param scenarioexpectedoutputDTO the scenarioexpectedoutputDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scenarioexpectedoutputDTO,
     * or with status {@code 400 (Bad Request)} if the scenarioexpectedoutputDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scenarioexpectedoutputDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scenarioexpectedoutputs")
    public ResponseEntity<ScenarioexpectedoutputDTO> updateScenarioexpectedoutput(@Valid @RequestBody ScenarioexpectedoutputDTO scenarioexpectedoutputDTO) throws URISyntaxException {
        log.debug("REST request to update Scenarioexpectedoutput : {}", scenarioexpectedoutputDTO);
        if (scenarioexpectedoutputDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScenarioexpectedoutputDTO result = scenarioexpectedoutputService.save(scenarioexpectedoutputDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scenarioexpectedoutputDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scenarioexpectedoutputs} : get all the scenarioexpectedoutputs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scenarioexpectedoutputs in body.
     */
    @GetMapping("/scenarioexpectedoutputs")
    public ResponseEntity<List<ScenarioexpectedoutputDTO>> getAllScenarioexpectedoutputs(ScenarioexpectedoutputCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Scenarioexpectedoutputs by criteria: {}", criteria);
        Page<ScenarioexpectedoutputDTO> page = scenarioexpectedoutputQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scenarioexpectedoutputs/count} : count all the scenarioexpectedoutputs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/scenarioexpectedoutputs/count")
    public ResponseEntity<Long> countScenarioexpectedoutputs(ScenarioexpectedoutputCriteria criteria) {
        log.debug("REST request to count Scenarioexpectedoutputs by criteria: {}", criteria);
        return ResponseEntity.ok().body(scenarioexpectedoutputQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /scenarioexpectedoutputs/:id} : get the "id" scenarioexpectedoutput.
     *
     * @param id the id of the scenarioexpectedoutputDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scenarioexpectedoutputDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scenarioexpectedoutputs/{id}")
    public ResponseEntity<ScenarioexpectedoutputDTO> getScenarioexpectedoutput(@PathVariable Long id) {
        log.debug("REST request to get Scenarioexpectedoutput : {}", id);
        Optional<ScenarioexpectedoutputDTO> scenarioexpectedoutputDTO = scenarioexpectedoutputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scenarioexpectedoutputDTO);
    }

    /**
     * {@code DELETE  /scenarioexpectedoutputs/:id} : delete the "id" scenarioexpectedoutput.
     *
     * @param id the id of the scenarioexpectedoutputDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scenarioexpectedoutputs/{id}")
    public ResponseEntity<Void> deleteScenarioexpectedoutput(@PathVariable Long id) {
        log.debug("REST request to delete Scenarioexpectedoutput : {}", id);
        scenarioexpectedoutputService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
