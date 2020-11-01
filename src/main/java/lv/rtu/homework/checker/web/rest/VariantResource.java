package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.service.VariantService;
import lv.rtu.homework.checker.web.rest.errors.BadRequestAlertException;
import lv.rtu.homework.checker.service.dto.VariantDTO;
import lv.rtu.homework.checker.service.dto.VariantCriteria;
import lv.rtu.homework.checker.service.VariantQueryService;

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
 * REST controller for managing {@link lv.rtu.homework.checker.domain.Variant}.
 */
@RestController
@RequestMapping("/api")
public class VariantResource {

    private final Logger log = LoggerFactory.getLogger(VariantResource.class);

    private static final String ENTITY_NAME = "variant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VariantService variantService;

    private final VariantQueryService variantQueryService;

    public VariantResource(VariantService variantService, VariantQueryService variantQueryService) {
        this.variantService = variantService;
        this.variantQueryService = variantQueryService;
    }

    /**
     * {@code POST  /variants} : Create a new variant.
     *
     * @param variantDTO the variantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new variantDTO, or with status {@code 400 (Bad Request)} if the variant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/variants")
    public ResponseEntity<VariantDTO> createVariant(@Valid @RequestBody VariantDTO variantDTO) throws URISyntaxException {
        log.debug("REST request to save Variant : {}", variantDTO);
        if (variantDTO.getId() != null) {
            throw new BadRequestAlertException("A new variant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VariantDTO result = variantService.save(variantDTO);
        return ResponseEntity.created(new URI("/api/variants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /variants} : Updates an existing variant.
     *
     * @param variantDTO the variantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variantDTO,
     * or with status {@code 400 (Bad Request)} if the variantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the variantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/variants")
    public ResponseEntity<VariantDTO> updateVariant(@Valid @RequestBody VariantDTO variantDTO) throws URISyntaxException {
        log.debug("REST request to update Variant : {}", variantDTO);
        if (variantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VariantDTO result = variantService.save(variantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, variantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /variants} : get all the variants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of variants in body.
     */
    @GetMapping("/variants")
    public ResponseEntity<List<VariantDTO>> getAllVariants(VariantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Variants by criteria: {}", criteria);
        Page<VariantDTO> page = variantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /variants/count} : count all the variants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/variants/count")
    public ResponseEntity<Long> countVariants(VariantCriteria criteria) {
        log.debug("REST request to count Variants by criteria: {}", criteria);
        return ResponseEntity.ok().body(variantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /variants/:id} : get the "id" variant.
     *
     * @param id the id of the variantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/variants/{id}")
    public ResponseEntity<VariantDTO> getVariant(@PathVariable Long id) {
        log.debug("REST request to get Variant : {}", id);
        Optional<VariantDTO> variantDTO = variantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(variantDTO);
    }

    /**
     * {@code DELETE  /variants/:id} : delete the "id" variant.
     *
     * @param id the id of the variantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/variants/{id}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
        log.debug("REST request to delete Variant : {}", id);
        variantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
