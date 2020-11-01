package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.RtuHomeworkCheckerApp;
import lv.rtu.homework.checker.domain.Varianttestscenario;
import lv.rtu.homework.checker.domain.Variant;
import lv.rtu.homework.checker.repository.VarianttestscenarioRepository;
import lv.rtu.homework.checker.service.VarianttestscenarioService;
import lv.rtu.homework.checker.service.dto.VarianttestscenarioDTO;
import lv.rtu.homework.checker.service.mapper.VarianttestscenarioMapper;
import lv.rtu.homework.checker.service.dto.VarianttestscenarioCriteria;
import lv.rtu.homework.checker.service.VarianttestscenarioQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VarianttestscenarioResource} REST controller.
 */
@SpringBootTest(classes = RtuHomeworkCheckerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VarianttestscenarioResourceIT {

    private static final String DEFAULT_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_INPUT = "BBBBBBBBBB";

    @Autowired
    private VarianttestscenarioRepository varianttestscenarioRepository;

    @Autowired
    private VarianttestscenarioMapper varianttestscenarioMapper;

    @Autowired
    private VarianttestscenarioService varianttestscenarioService;

    @Autowired
    private VarianttestscenarioQueryService varianttestscenarioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVarianttestscenarioMockMvc;

    private Varianttestscenario varianttestscenario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Varianttestscenario createEntity(EntityManager em) {
        Varianttestscenario varianttestscenario = new Varianttestscenario()
            .input(DEFAULT_INPUT);
        // Add required entity
        Variant variant;
        if (TestUtil.findAll(em, Variant.class).isEmpty()) {
            variant = VariantResourceIT.createEntity(em);
            em.persist(variant);
            em.flush();
        } else {
            variant = TestUtil.findAll(em, Variant.class).get(0);
        }
        varianttestscenario.setVariant(variant);
        return varianttestscenario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Varianttestscenario createUpdatedEntity(EntityManager em) {
        Varianttestscenario varianttestscenario = new Varianttestscenario()
            .input(UPDATED_INPUT);
        // Add required entity
        Variant variant;
        if (TestUtil.findAll(em, Variant.class).isEmpty()) {
            variant = VariantResourceIT.createUpdatedEntity(em);
            em.persist(variant);
            em.flush();
        } else {
            variant = TestUtil.findAll(em, Variant.class).get(0);
        }
        varianttestscenario.setVariant(variant);
        return varianttestscenario;
    }

    @BeforeEach
    public void initTest() {
        varianttestscenario = createEntity(em);
    }

    @Test
    @Transactional
    public void createVarianttestscenario() throws Exception {
        int databaseSizeBeforeCreate = varianttestscenarioRepository.findAll().size();
        // Create the Varianttestscenario
        VarianttestscenarioDTO varianttestscenarioDTO = varianttestscenarioMapper.toDto(varianttestscenario);
        restVarianttestscenarioMockMvc.perform(post("/api/varianttestscenarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(varianttestscenarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Varianttestscenario in the database
        List<Varianttestscenario> varianttestscenarioList = varianttestscenarioRepository.findAll();
        assertThat(varianttestscenarioList).hasSize(databaseSizeBeforeCreate + 1);
        Varianttestscenario testVarianttestscenario = varianttestscenarioList.get(varianttestscenarioList.size() - 1);
        assertThat(testVarianttestscenario.getInput()).isEqualTo(DEFAULT_INPUT);
    }

    @Test
    @Transactional
    public void createVarianttestscenarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = varianttestscenarioRepository.findAll().size();

        // Create the Varianttestscenario with an existing ID
        varianttestscenario.setId(1L);
        VarianttestscenarioDTO varianttestscenarioDTO = varianttestscenarioMapper.toDto(varianttestscenario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVarianttestscenarioMockMvc.perform(post("/api/varianttestscenarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(varianttestscenarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Varianttestscenario in the database
        List<Varianttestscenario> varianttestscenarioList = varianttestscenarioRepository.findAll();
        assertThat(varianttestscenarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInputIsRequired() throws Exception {
        int databaseSizeBeforeTest = varianttestscenarioRepository.findAll().size();
        // set the field null
        varianttestscenario.setInput(null);

        // Create the Varianttestscenario, which fails.
        VarianttestscenarioDTO varianttestscenarioDTO = varianttestscenarioMapper.toDto(varianttestscenario);


        restVarianttestscenarioMockMvc.perform(post("/api/varianttestscenarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(varianttestscenarioDTO)))
            .andExpect(status().isBadRequest());

        List<Varianttestscenario> varianttestscenarioList = varianttestscenarioRepository.findAll();
        assertThat(varianttestscenarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVarianttestscenarios() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        // Get all the varianttestscenarioList
        restVarianttestscenarioMockMvc.perform(get("/api/varianttestscenarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(varianttestscenario.getId().intValue())))
            .andExpect(jsonPath("$.[*].input").value(hasItem(DEFAULT_INPUT)));
    }
    
    @Test
    @Transactional
    public void getVarianttestscenario() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        // Get the varianttestscenario
        restVarianttestscenarioMockMvc.perform(get("/api/varianttestscenarios/{id}", varianttestscenario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(varianttestscenario.getId().intValue()))
            .andExpect(jsonPath("$.input").value(DEFAULT_INPUT));
    }


    @Test
    @Transactional
    public void getVarianttestscenariosByIdFiltering() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        Long id = varianttestscenario.getId();

        defaultVarianttestscenarioShouldBeFound("id.equals=" + id);
        defaultVarianttestscenarioShouldNotBeFound("id.notEquals=" + id);

        defaultVarianttestscenarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVarianttestscenarioShouldNotBeFound("id.greaterThan=" + id);

        defaultVarianttestscenarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVarianttestscenarioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVarianttestscenariosByInputIsEqualToSomething() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        // Get all the varianttestscenarioList where input equals to DEFAULT_INPUT
        defaultVarianttestscenarioShouldBeFound("input.equals=" + DEFAULT_INPUT);

        // Get all the varianttestscenarioList where input equals to UPDATED_INPUT
        defaultVarianttestscenarioShouldNotBeFound("input.equals=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    public void getAllVarianttestscenariosByInputIsNotEqualToSomething() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        // Get all the varianttestscenarioList where input not equals to DEFAULT_INPUT
        defaultVarianttestscenarioShouldNotBeFound("input.notEquals=" + DEFAULT_INPUT);

        // Get all the varianttestscenarioList where input not equals to UPDATED_INPUT
        defaultVarianttestscenarioShouldBeFound("input.notEquals=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    public void getAllVarianttestscenariosByInputIsInShouldWork() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        // Get all the varianttestscenarioList where input in DEFAULT_INPUT or UPDATED_INPUT
        defaultVarianttestscenarioShouldBeFound("input.in=" + DEFAULT_INPUT + "," + UPDATED_INPUT);

        // Get all the varianttestscenarioList where input equals to UPDATED_INPUT
        defaultVarianttestscenarioShouldNotBeFound("input.in=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    public void getAllVarianttestscenariosByInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        // Get all the varianttestscenarioList where input is not null
        defaultVarianttestscenarioShouldBeFound("input.specified=true");

        // Get all the varianttestscenarioList where input is null
        defaultVarianttestscenarioShouldNotBeFound("input.specified=false");
    }
                @Test
    @Transactional
    public void getAllVarianttestscenariosByInputContainsSomething() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        // Get all the varianttestscenarioList where input contains DEFAULT_INPUT
        defaultVarianttestscenarioShouldBeFound("input.contains=" + DEFAULT_INPUT);

        // Get all the varianttestscenarioList where input contains UPDATED_INPUT
        defaultVarianttestscenarioShouldNotBeFound("input.contains=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    public void getAllVarianttestscenariosByInputNotContainsSomething() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        // Get all the varianttestscenarioList where input does not contain DEFAULT_INPUT
        defaultVarianttestscenarioShouldNotBeFound("input.doesNotContain=" + DEFAULT_INPUT);

        // Get all the varianttestscenarioList where input does not contain UPDATED_INPUT
        defaultVarianttestscenarioShouldBeFound("input.doesNotContain=" + UPDATED_INPUT);
    }


    @Test
    @Transactional
    public void getAllVarianttestscenariosByVariantIsEqualToSomething() throws Exception {
        // Get already existing entity
        Variant variant = varianttestscenario.getVariant();
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);
        Long variantId = variant.getId();

        // Get all the varianttestscenarioList where variant equals to variantId
        defaultVarianttestscenarioShouldBeFound("variantId.equals=" + variantId);

        // Get all the varianttestscenarioList where variant equals to variantId + 1
        defaultVarianttestscenarioShouldNotBeFound("variantId.equals=" + (variantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVarianttestscenarioShouldBeFound(String filter) throws Exception {
        restVarianttestscenarioMockMvc.perform(get("/api/varianttestscenarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(varianttestscenario.getId().intValue())))
            .andExpect(jsonPath("$.[*].input").value(hasItem(DEFAULT_INPUT)));

        // Check, that the count call also returns 1
        restVarianttestscenarioMockMvc.perform(get("/api/varianttestscenarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVarianttestscenarioShouldNotBeFound(String filter) throws Exception {
        restVarianttestscenarioMockMvc.perform(get("/api/varianttestscenarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVarianttestscenarioMockMvc.perform(get("/api/varianttestscenarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVarianttestscenario() throws Exception {
        // Get the varianttestscenario
        restVarianttestscenarioMockMvc.perform(get("/api/varianttestscenarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVarianttestscenario() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        int databaseSizeBeforeUpdate = varianttestscenarioRepository.findAll().size();

        // Update the varianttestscenario
        Varianttestscenario updatedVarianttestscenario = varianttestscenarioRepository.findById(varianttestscenario.getId()).get();
        // Disconnect from session so that the updates on updatedVarianttestscenario are not directly saved in db
        em.detach(updatedVarianttestscenario);
        updatedVarianttestscenario
            .input(UPDATED_INPUT);
        VarianttestscenarioDTO varianttestscenarioDTO = varianttestscenarioMapper.toDto(updatedVarianttestscenario);

        restVarianttestscenarioMockMvc.perform(put("/api/varianttestscenarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(varianttestscenarioDTO)))
            .andExpect(status().isOk());

        // Validate the Varianttestscenario in the database
        List<Varianttestscenario> varianttestscenarioList = varianttestscenarioRepository.findAll();
        assertThat(varianttestscenarioList).hasSize(databaseSizeBeforeUpdate);
        Varianttestscenario testVarianttestscenario = varianttestscenarioList.get(varianttestscenarioList.size() - 1);
        assertThat(testVarianttestscenario.getInput()).isEqualTo(UPDATED_INPUT);
    }

    @Test
    @Transactional
    public void updateNonExistingVarianttestscenario() throws Exception {
        int databaseSizeBeforeUpdate = varianttestscenarioRepository.findAll().size();

        // Create the Varianttestscenario
        VarianttestscenarioDTO varianttestscenarioDTO = varianttestscenarioMapper.toDto(varianttestscenario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVarianttestscenarioMockMvc.perform(put("/api/varianttestscenarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(varianttestscenarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Varianttestscenario in the database
        List<Varianttestscenario> varianttestscenarioList = varianttestscenarioRepository.findAll();
        assertThat(varianttestscenarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVarianttestscenario() throws Exception {
        // Initialize the database
        varianttestscenarioRepository.saveAndFlush(varianttestscenario);

        int databaseSizeBeforeDelete = varianttestscenarioRepository.findAll().size();

        // Delete the varianttestscenario
        restVarianttestscenarioMockMvc.perform(delete("/api/varianttestscenarios/{id}", varianttestscenario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Varianttestscenario> varianttestscenarioList = varianttestscenarioRepository.findAll();
        assertThat(varianttestscenarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
