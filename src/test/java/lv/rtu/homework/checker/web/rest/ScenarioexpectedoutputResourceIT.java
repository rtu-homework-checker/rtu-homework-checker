package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.RtuHomeworkCheckerApp;
import lv.rtu.homework.checker.domain.Scenarioexpectedoutput;
import lv.rtu.homework.checker.domain.Varianttestscenario;
import lv.rtu.homework.checker.repository.ScenarioexpectedoutputRepository;
import lv.rtu.homework.checker.service.ScenarioexpectedoutputService;
import lv.rtu.homework.checker.service.dto.ScenarioexpectedoutputDTO;
import lv.rtu.homework.checker.service.mapper.ScenarioexpectedoutputMapper;
import lv.rtu.homework.checker.service.dto.ScenarioexpectedoutputCriteria;
import lv.rtu.homework.checker.service.ScenarioexpectedoutputQueryService;

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
 * Integration tests for the {@link ScenarioexpectedoutputResource} REST controller.
 */
@SpringBootTest(classes = RtuHomeworkCheckerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScenarioexpectedoutputResourceIT {

    private static final String DEFAULT_OUTPUTLINE = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUTLINE = "BBBBBBBBBB";

    @Autowired
    private ScenarioexpectedoutputRepository scenarioexpectedoutputRepository;

    @Autowired
    private ScenarioexpectedoutputMapper scenarioexpectedoutputMapper;

    @Autowired
    private ScenarioexpectedoutputService scenarioexpectedoutputService;

    @Autowired
    private ScenarioexpectedoutputQueryService scenarioexpectedoutputQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScenarioexpectedoutputMockMvc;

    private Scenarioexpectedoutput scenarioexpectedoutput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scenarioexpectedoutput createEntity(EntityManager em) {
        Scenarioexpectedoutput scenarioexpectedoutput = new Scenarioexpectedoutput()
            .outputline(DEFAULT_OUTPUTLINE);
        return scenarioexpectedoutput;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scenarioexpectedoutput createUpdatedEntity(EntityManager em) {
        Scenarioexpectedoutput scenarioexpectedoutput = new Scenarioexpectedoutput()
            .outputline(UPDATED_OUTPUTLINE);
        return scenarioexpectedoutput;
    }

    @BeforeEach
    public void initTest() {
        scenarioexpectedoutput = createEntity(em);
    }

    @Test
    @Transactional
    public void createScenarioexpectedoutput() throws Exception {
        int databaseSizeBeforeCreate = scenarioexpectedoutputRepository.findAll().size();
        // Create the Scenarioexpectedoutput
        ScenarioexpectedoutputDTO scenarioexpectedoutputDTO = scenarioexpectedoutputMapper.toDto(scenarioexpectedoutput);
        restScenarioexpectedoutputMockMvc.perform(post("/api/scenarioexpectedoutputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioexpectedoutputDTO)))
            .andExpect(status().isCreated());

        // Validate the Scenarioexpectedoutput in the database
        List<Scenarioexpectedoutput> scenarioexpectedoutputList = scenarioexpectedoutputRepository.findAll();
        assertThat(scenarioexpectedoutputList).hasSize(databaseSizeBeforeCreate + 1);
        Scenarioexpectedoutput testScenarioexpectedoutput = scenarioexpectedoutputList.get(scenarioexpectedoutputList.size() - 1);
        assertThat(testScenarioexpectedoutput.getOutputline()).isEqualTo(DEFAULT_OUTPUTLINE);
    }

    @Test
    @Transactional
    public void createScenarioexpectedoutputWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scenarioexpectedoutputRepository.findAll().size();

        // Create the Scenarioexpectedoutput with an existing ID
        scenarioexpectedoutput.setId(1L);
        ScenarioexpectedoutputDTO scenarioexpectedoutputDTO = scenarioexpectedoutputMapper.toDto(scenarioexpectedoutput);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScenarioexpectedoutputMockMvc.perform(post("/api/scenarioexpectedoutputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioexpectedoutputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scenarioexpectedoutput in the database
        List<Scenarioexpectedoutput> scenarioexpectedoutputList = scenarioexpectedoutputRepository.findAll();
        assertThat(scenarioexpectedoutputList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOutputlineIsRequired() throws Exception {
        int databaseSizeBeforeTest = scenarioexpectedoutputRepository.findAll().size();
        // set the field null
        scenarioexpectedoutput.setOutputline(null);

        // Create the Scenarioexpectedoutput, which fails.
        ScenarioexpectedoutputDTO scenarioexpectedoutputDTO = scenarioexpectedoutputMapper.toDto(scenarioexpectedoutput);


        restScenarioexpectedoutputMockMvc.perform(post("/api/scenarioexpectedoutputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioexpectedoutputDTO)))
            .andExpect(status().isBadRequest());

        List<Scenarioexpectedoutput> scenarioexpectedoutputList = scenarioexpectedoutputRepository.findAll();
        assertThat(scenarioexpectedoutputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScenarioexpectedoutputs() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        // Get all the scenarioexpectedoutputList
        restScenarioexpectedoutputMockMvc.perform(get("/api/scenarioexpectedoutputs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenarioexpectedoutput.getId().intValue())))
            .andExpect(jsonPath("$.[*].outputline").value(hasItem(DEFAULT_OUTPUTLINE)));
    }
    
    @Test
    @Transactional
    public void getScenarioexpectedoutput() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        // Get the scenarioexpectedoutput
        restScenarioexpectedoutputMockMvc.perform(get("/api/scenarioexpectedoutputs/{id}", scenarioexpectedoutput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scenarioexpectedoutput.getId().intValue()))
            .andExpect(jsonPath("$.outputline").value(DEFAULT_OUTPUTLINE));
    }


    @Test
    @Transactional
    public void getScenarioexpectedoutputsByIdFiltering() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        Long id = scenarioexpectedoutput.getId();

        defaultScenarioexpectedoutputShouldBeFound("id.equals=" + id);
        defaultScenarioexpectedoutputShouldNotBeFound("id.notEquals=" + id);

        defaultScenarioexpectedoutputShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultScenarioexpectedoutputShouldNotBeFound("id.greaterThan=" + id);

        defaultScenarioexpectedoutputShouldBeFound("id.lessThanOrEqual=" + id);
        defaultScenarioexpectedoutputShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllScenarioexpectedoutputsByOutputlineIsEqualToSomething() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        // Get all the scenarioexpectedoutputList where outputline equals to DEFAULT_OUTPUTLINE
        defaultScenarioexpectedoutputShouldBeFound("outputline.equals=" + DEFAULT_OUTPUTLINE);

        // Get all the scenarioexpectedoutputList where outputline equals to UPDATED_OUTPUTLINE
        defaultScenarioexpectedoutputShouldNotBeFound("outputline.equals=" + UPDATED_OUTPUTLINE);
    }

    @Test
    @Transactional
    public void getAllScenarioexpectedoutputsByOutputlineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        // Get all the scenarioexpectedoutputList where outputline not equals to DEFAULT_OUTPUTLINE
        defaultScenarioexpectedoutputShouldNotBeFound("outputline.notEquals=" + DEFAULT_OUTPUTLINE);

        // Get all the scenarioexpectedoutputList where outputline not equals to UPDATED_OUTPUTLINE
        defaultScenarioexpectedoutputShouldBeFound("outputline.notEquals=" + UPDATED_OUTPUTLINE);
    }

    @Test
    @Transactional
    public void getAllScenarioexpectedoutputsByOutputlineIsInShouldWork() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        // Get all the scenarioexpectedoutputList where outputline in DEFAULT_OUTPUTLINE or UPDATED_OUTPUTLINE
        defaultScenarioexpectedoutputShouldBeFound("outputline.in=" + DEFAULT_OUTPUTLINE + "," + UPDATED_OUTPUTLINE);

        // Get all the scenarioexpectedoutputList where outputline equals to UPDATED_OUTPUTLINE
        defaultScenarioexpectedoutputShouldNotBeFound("outputline.in=" + UPDATED_OUTPUTLINE);
    }

    @Test
    @Transactional
    public void getAllScenarioexpectedoutputsByOutputlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        // Get all the scenarioexpectedoutputList where outputline is not null
        defaultScenarioexpectedoutputShouldBeFound("outputline.specified=true");

        // Get all the scenarioexpectedoutputList where outputline is null
        defaultScenarioexpectedoutputShouldNotBeFound("outputline.specified=false");
    }
                @Test
    @Transactional
    public void getAllScenarioexpectedoutputsByOutputlineContainsSomething() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        // Get all the scenarioexpectedoutputList where outputline contains DEFAULT_OUTPUTLINE
        defaultScenarioexpectedoutputShouldBeFound("outputline.contains=" + DEFAULT_OUTPUTLINE);

        // Get all the scenarioexpectedoutputList where outputline contains UPDATED_OUTPUTLINE
        defaultScenarioexpectedoutputShouldNotBeFound("outputline.contains=" + UPDATED_OUTPUTLINE);
    }

    @Test
    @Transactional
    public void getAllScenarioexpectedoutputsByOutputlineNotContainsSomething() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        // Get all the scenarioexpectedoutputList where outputline does not contain DEFAULT_OUTPUTLINE
        defaultScenarioexpectedoutputShouldNotBeFound("outputline.doesNotContain=" + DEFAULT_OUTPUTLINE);

        // Get all the scenarioexpectedoutputList where outputline does not contain UPDATED_OUTPUTLINE
        defaultScenarioexpectedoutputShouldBeFound("outputline.doesNotContain=" + UPDATED_OUTPUTLINE);
    }


    @Test
    @Transactional
    public void getAllScenarioexpectedoutputsByVarianttestscenarioIsEqualToSomething() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);
        Varianttestscenario varianttestscenario = VarianttestscenarioResourceIT.createEntity(em);
        em.persist(varianttestscenario);
        em.flush();
        scenarioexpectedoutput.setVarianttestscenario(varianttestscenario);
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);
        Long varianttestscenarioId = varianttestscenario.getId();

        // Get all the scenarioexpectedoutputList where varianttestscenario equals to varianttestscenarioId
        defaultScenarioexpectedoutputShouldBeFound("varianttestscenarioId.equals=" + varianttestscenarioId);

        // Get all the scenarioexpectedoutputList where varianttestscenario equals to varianttestscenarioId + 1
        defaultScenarioexpectedoutputShouldNotBeFound("varianttestscenarioId.equals=" + (varianttestscenarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScenarioexpectedoutputShouldBeFound(String filter) throws Exception {
        restScenarioexpectedoutputMockMvc.perform(get("/api/scenarioexpectedoutputs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenarioexpectedoutput.getId().intValue())))
            .andExpect(jsonPath("$.[*].outputline").value(hasItem(DEFAULT_OUTPUTLINE)));

        // Check, that the count call also returns 1
        restScenarioexpectedoutputMockMvc.perform(get("/api/scenarioexpectedoutputs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScenarioexpectedoutputShouldNotBeFound(String filter) throws Exception {
        restScenarioexpectedoutputMockMvc.perform(get("/api/scenarioexpectedoutputs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScenarioexpectedoutputMockMvc.perform(get("/api/scenarioexpectedoutputs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingScenarioexpectedoutput() throws Exception {
        // Get the scenarioexpectedoutput
        restScenarioexpectedoutputMockMvc.perform(get("/api/scenarioexpectedoutputs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScenarioexpectedoutput() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        int databaseSizeBeforeUpdate = scenarioexpectedoutputRepository.findAll().size();

        // Update the scenarioexpectedoutput
        Scenarioexpectedoutput updatedScenarioexpectedoutput = scenarioexpectedoutputRepository.findById(scenarioexpectedoutput.getId()).get();
        // Disconnect from session so that the updates on updatedScenarioexpectedoutput are not directly saved in db
        em.detach(updatedScenarioexpectedoutput);
        updatedScenarioexpectedoutput
            .outputline(UPDATED_OUTPUTLINE);
        ScenarioexpectedoutputDTO scenarioexpectedoutputDTO = scenarioexpectedoutputMapper.toDto(updatedScenarioexpectedoutput);

        restScenarioexpectedoutputMockMvc.perform(put("/api/scenarioexpectedoutputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioexpectedoutputDTO)))
            .andExpect(status().isOk());

        // Validate the Scenarioexpectedoutput in the database
        List<Scenarioexpectedoutput> scenarioexpectedoutputList = scenarioexpectedoutputRepository.findAll();
        assertThat(scenarioexpectedoutputList).hasSize(databaseSizeBeforeUpdate);
        Scenarioexpectedoutput testScenarioexpectedoutput = scenarioexpectedoutputList.get(scenarioexpectedoutputList.size() - 1);
        assertThat(testScenarioexpectedoutput.getOutputline()).isEqualTo(UPDATED_OUTPUTLINE);
    }

    @Test
    @Transactional
    public void updateNonExistingScenarioexpectedoutput() throws Exception {
        int databaseSizeBeforeUpdate = scenarioexpectedoutputRepository.findAll().size();

        // Create the Scenarioexpectedoutput
        ScenarioexpectedoutputDTO scenarioexpectedoutputDTO = scenarioexpectedoutputMapper.toDto(scenarioexpectedoutput);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScenarioexpectedoutputMockMvc.perform(put("/api/scenarioexpectedoutputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioexpectedoutputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scenarioexpectedoutput in the database
        List<Scenarioexpectedoutput> scenarioexpectedoutputList = scenarioexpectedoutputRepository.findAll();
        assertThat(scenarioexpectedoutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScenarioexpectedoutput() throws Exception {
        // Initialize the database
        scenarioexpectedoutputRepository.saveAndFlush(scenarioexpectedoutput);

        int databaseSizeBeforeDelete = scenarioexpectedoutputRepository.findAll().size();

        // Delete the scenarioexpectedoutput
        restScenarioexpectedoutputMockMvc.perform(delete("/api/scenarioexpectedoutputs/{id}", scenarioexpectedoutput.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scenarioexpectedoutput> scenarioexpectedoutputList = scenarioexpectedoutputRepository.findAll();
        assertThat(scenarioexpectedoutputList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
