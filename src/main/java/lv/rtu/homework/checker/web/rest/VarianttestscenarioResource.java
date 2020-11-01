package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.service.VarianttestscenarioService;
import lv.rtu.homework.checker.web.rest.errors.BadRequestAlertException;
import lv.rtu.homework.checker.service.dto.VarianttestscenarioDTO;
import lv.rtu.homework.checker.service.dto.VarianttestscenarioCriteria;
import lv.rtu.homework.checker.service.VarianttestscenarioQueryService;

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
 * REST controller for managing {@link lv.rtu.homework.checker.domain.Varianttestscenario}.
 */
@RestController
@RequestMapping("/api")
public class VarianttestscenarioResource {

    private final Logger log = LoggerFactory.getLogger(VarianttestscenarioResource.class);

    private static final String ENTITY_NAME = "varianttestscenario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VarianttestscenarioService varianttestscenarioService;

    private final VarianttestscenarioQueryService varianttestscenarioQueryService;

    public VarianttestscenarioResource(VarianttestscenarioService varianttestscenarioService, VarianttestscenarioQueryService varianttestscenarioQueryService) {
        this.varianttestscenarioService = varianttestscenarioService;
        this.varianttestscenarioQueryService = varianttestscenarioQueryService;
    }

    /**
     * {@code POST  /varianttestscenarios} : Create a new varianttestscenario.
     *
     * @param varianttestscenarioDTO the varianttestscenarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new varianttestscenarioDTO, or with status {@code 400 (Bad Request)} if the varianttestscenario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/varianttestscenarios")
    public ResponseEntity<VarianttestscenarioDTO> createVarianttestscenario(@Valid @RequestBody VarianttestscenarioDTO varianttestscenarioDTO) throws URISyntaxException {
        log.debug("REST request to save Varianttestscenario : {}", varianttestscenarioDTO);
        if (varianttestscenarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new varianttestscenario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VarianttestscenarioDTO result = varianttestscenarioService.save(varianttestscenarioDTO);
        return ResponseEntity.created(new URI("/api/varianttestscenarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /varianttestscenarios} : Updates an existing varianttestscenario.
     *
     * @param varianttestscenarioDTO the varianttestscenarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated varianttestscenarioDTO,
     * or with status {@code 400 (Bad Request)} if the varianttestscenarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the varianttestscenarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/varianttestscenarios")
    public ResponseEntity<VarianttestscenarioDTO> updateVarianttestscenario(@Valid @RequestBody VarianttestscenarioDTO varianttestscenarioDTO) throws URISyntaxException {
        log.debug("REST request to update Varianttestscenario : {}", varianttestscenarioDTO);
        if (varianttestscenarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VarianttestscenarioDTO result = varianttestscenarioService.save(varianttestscenarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, varianttestscenarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /varianttestscenarios} : get all the varianttestscenarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of varianttestscenarios in body.
     */
    @GetMapping("/varianttestscenarios")
    public ResponseEntity<List<VarianttestscenarioDTO>> getAllVarianttestscenarios(VarianttestscenarioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Varianttestscenarios by criteria: {}", criteria);
        Page<VarianttestscenarioDTO> page = varianttestscenarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /varianttestscenarios/count} : count all the varianttestscenarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/varianttestscenarios/count")
    public ResponseEntity<Long> countVarianttestscenarios(VarianttestscenarioCriteria criteria) {
        log.debug("REST request to count Varianttestscenarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(varianttestscenarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /varianttestscenarios/:id} : get the "id" varianttestscenario.
     *
     * @param id the id of the varianttestscenarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the varianttestscenarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/varianttestscenarios/{id}")
    public ResponseEntity<VarianttestscenarioDTO> getVarianttestscenario(@PathVariable Long id) {
        log.debug("REST request to get Varianttestscenario : {}", id);
        Optional<VarianttestscenarioDTO> varianttestscenarioDTO = varianttestscenarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(varianttestscenarioDTO);
    }

    /**
     * {@code DELETE  /varianttestscenarios/:id} : delete the "id" varianttestscenario.
     *
     * @param id the id of the varianttestscenarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/varianttestscenarios/{id}")
    public ResponseEntity<Void> deleteVarianttestscenario(@PathVariable Long id) {
        log.debug("REST request to delete Varianttestscenario : {}", id);
        varianttestscenarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
