package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.RtuHomeworkCheckerApp;
import lv.rtu.homework.checker.domain.Scenarioscore;
import lv.rtu.homework.checker.domain.Score;
import lv.rtu.homework.checker.domain.Scenarioexpectedoutput;
import lv.rtu.homework.checker.repository.ScenarioscoreRepository;
import lv.rtu.homework.checker.service.ScenarioscoreService;
import lv.rtu.homework.checker.service.dto.ScenarioscoreDTO;
import lv.rtu.homework.checker.service.mapper.ScenarioscoreMapper;
import lv.rtu.homework.checker.service.dto.ScenarioscoreCriteria;
import lv.rtu.homework.checker.service.ScenarioscoreQueryService;

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
 * Integration tests for the {@link ScenarioscoreResource} REST controller.
 */
@SpringBootTest(classes = RtuHomeworkCheckerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScenarioscoreResourceIT {

    private static final Boolean DEFAULT_PASSED = false;
    private static final Boolean UPDATED_PASSED = true;

    @Autowired
    private ScenarioscoreRepository scenarioscoreRepository;

    @Autowired
    private ScenarioscoreMapper scenarioscoreMapper;

    @Autowired
    private ScenarioscoreService scenarioscoreService;

    @Autowired
    private ScenarioscoreQueryService scenarioscoreQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScenarioscoreMockMvc;

    private Scenarioscore scenarioscore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scenarioscore createEntity(EntityManager em) {
        Scenarioscore scenarioscore = new Scenarioscore()
            .passed(DEFAULT_PASSED);
        // Add required entity
        Score score;
        if (TestUtil.findAll(em, Score.class).isEmpty()) {
            score = ScoreResourceIT.createEntity(em);
            em.persist(score);
            em.flush();
        } else {
            score = TestUtil.findAll(em, Score.class).get(0);
        }
        scenarioscore.setScore(score);
        // Add required entity
        Scenarioexpectedoutput scenarioexpectedoutput;
        if (TestUtil.findAll(em, Scenarioexpectedoutput.class).isEmpty()) {
            scenarioexpectedoutput = ScenarioexpectedoutputResourceIT.createEntity(em);
            em.persist(scenarioexpectedoutput);
            em.flush();
        } else {
            scenarioexpectedoutput = TestUtil.findAll(em, Scenarioexpectedoutput.class).get(0);
        }
        scenarioscore.setScenarioexpectedoutput(scenarioexpectedoutput);
        return scenarioscore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scenarioscore createUpdatedEntity(EntityManager em) {
        Scenarioscore scenarioscore = new Scenarioscore()
            .passed(UPDATED_PASSED);
        // Add required entity
        Score score;
        if (TestUtil.findAll(em, Score.class).isEmpty()) {
            score = ScoreResourceIT.createUpdatedEntity(em);
            em.persist(score);
            em.flush();
        } else {
            score = TestUtil.findAll(em, Score.class).get(0);
        }
        scenarioscore.setScore(score);
        // Add required entity
        Scenarioexpectedoutput scenarioexpectedoutput;
        if (TestUtil.findAll(em, Scenarioexpectedoutput.class).isEmpty()) {
            scenarioexpectedoutput = ScenarioexpectedoutputResourceIT.createUpdatedEntity(em);
            em.persist(scenarioexpectedoutput);
            em.flush();
        } else {
            scenarioexpectedoutput = TestUtil.findAll(em, Scenarioexpectedoutput.class).get(0);
        }
        scenarioscore.setScenarioexpectedoutput(scenarioexpectedoutput);
        return scenarioscore;
    }

    @BeforeEach
    public void initTest() {
        scenarioscore = createEntity(em);
    }

    @Test
    @Transactional
    public void createScenarioscore() throws Exception {
        int databaseSizeBeforeCreate = scenarioscoreRepository.findAll().size();
        // Create the Scenarioscore
        ScenarioscoreDTO scenarioscoreDTO = scenarioscoreMapper.toDto(scenarioscore);
        restScenarioscoreMockMvc.perform(post("/api/scenarioscores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioscoreDTO)))
            .andExpect(status().isCreated());

        // Validate the Scenarioscore in the database
        List<Scenarioscore> scenarioscoreList = scenarioscoreRepository.findAll();
        assertThat(scenarioscoreList).hasSize(databaseSizeBeforeCreate + 1);
        Scenarioscore testScenarioscore = scenarioscoreList.get(scenarioscoreList.size() - 1);
        assertThat(testScenarioscore.isPassed()).isEqualTo(DEFAULT_PASSED);
    }

    @Test
    @Transactional
    public void createScenarioscoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scenarioscoreRepository.findAll().size();

        // Create the Scenarioscore with an existing ID
        scenarioscore.setId(1L);
        ScenarioscoreDTO scenarioscoreDTO = scenarioscoreMapper.toDto(scenarioscore);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScenarioscoreMockMvc.perform(post("/api/scenarioscores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioscoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scenarioscore in the database
        List<Scenarioscore> scenarioscoreList = scenarioscoreRepository.findAll();
        assertThat(scenarioscoreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPassedIsRequired() throws Exception {
        int databaseSizeBeforeTest = scenarioscoreRepository.findAll().size();
        // set the field null
        scenarioscore.setPassed(null);

        // Create the Scenarioscore, which fails.
        ScenarioscoreDTO scenarioscoreDTO = scenarioscoreMapper.toDto(scenarioscore);


        restScenarioscoreMockMvc.perform(post("/api/scenarioscores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioscoreDTO)))
            .andExpect(status().isBadRequest());

        List<Scenarioscore> scenarioscoreList = scenarioscoreRepository.findAll();
        assertThat(scenarioscoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScenarioscores() throws Exception {
        // Initialize the database
        scenarioscoreRepository.saveAndFlush(scenarioscore);

        // Get all the scenarioscoreList
        restScenarioscoreMockMvc.perform(get("/api/scenarioscores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenarioscore.getId().intValue())))
            .andExpect(jsonPath("$.[*].passed").value(hasItem(DEFAULT_PASSED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getScenarioscore() throws Exception {
        // Initialize the database
        scenarioscoreRepository.saveAndFlush(scenarioscore);

        // Get the scenarioscore
        restScenarioscoreMockMvc.perform(get("/api/scenarioscores/{id}", scenarioscore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scenarioscore.getId().intValue()))
            .andExpect(jsonPath("$.passed").value(DEFAULT_PASSED.booleanValue()));
    }


    @Test
    @Transactional
    public void getScenarioscoresByIdFiltering() throws Exception {
        // Initialize the database
        scenarioscoreRepository.saveAndFlush(scenarioscore);

        Long id = scenarioscore.getId();

        defaultScenarioscoreShouldBeFound("id.equals=" + id);
        defaultScenarioscoreShouldNotBeFound("id.notEquals=" + id);

        defaultScenarioscoreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultScenarioscoreShouldNotBeFound("id.greaterThan=" + id);

        defaultScenarioscoreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultScenarioscoreShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllScenarioscoresByPassedIsEqualToSomething() throws Exception {
        // Initialize the database
        scenarioscoreRepository.saveAndFlush(scenarioscore);

        // Get all the scenarioscoreList where passed equals to DEFAULT_PASSED
        defaultScenarioscoreShouldBeFound("passed.equals=" + DEFAULT_PASSED);

        // Get all the scenarioscoreList where passed equals to UPDATED_PASSED
        defaultScenarioscoreShouldNotBeFound("passed.equals=" + UPDATED_PASSED);
    }

    @Test
    @Transactional
    public void getAllScenarioscoresByPassedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scenarioscoreRepository.saveAndFlush(scenarioscore);

        // Get all the scenarioscoreList where passed not equals to DEFAULT_PASSED
        defaultScenarioscoreShouldNotBeFound("passed.notEquals=" + DEFAULT_PASSED);

        // Get all the scenarioscoreList where passed not equals to UPDATED_PASSED
        defaultScenarioscoreShouldBeFound("passed.notEquals=" + UPDATED_PASSED);
    }

    @Test
    @Transactional
    public void getAllScenarioscoresByPassedIsInShouldWork() throws Exception {
        // Initialize the database
        scenarioscoreRepository.saveAndFlush(scenarioscore);

        // Get all the scenarioscoreList where passed in DEFAULT_PASSED or UPDATED_PASSED
        defaultScenarioscoreShouldBeFound("passed.in=" + DEFAULT_PASSED + "," + UPDATED_PASSED);

        // Get all the scenarioscoreList where passed equals to UPDATED_PASSED
        defaultScenarioscoreShouldNotBeFound("passed.in=" + UPDATED_PASSED);
    }

    @Test
    @Transactional
    public void getAllScenarioscoresByPassedIsNullOrNotNull() throws Exception {
        // Initialize the database
        scenarioscoreRepository.saveAndFlush(scenarioscore);

        // Get all the scenarioscoreList where passed is not null
        defaultScenarioscoreShouldBeFound("passed.specified=true");

        // Get all the scenarioscoreList where passed is null
        defaultScenarioscoreShouldNotBeFound("passed.specified=false");
    }

    @Test
    @Transactional
    public void getAllScenarioscoresByScoreIsEqualToSomething() throws Exception {
        // Get already existing entity
        Score score = scenarioscore.getScore();
        scenarioscoreRepository.saveAndFlush(scenarioscore);
        Long scoreId = score.getId();

        // Get all the scenarioscoreList where score equals to scoreId
        defaultScenarioscoreShouldBeFound("scoreId.equals=" + scoreId);

        // Get all the scenarioscoreList where score equals to scoreId + 1
        defaultScenarioscoreShouldNotBeFound("scoreId.equals=" + (scoreId + 1));
    }


    @Test
    @Transactional
    public void getAllScenarioscoresByScenarioexpectedoutputIsEqualToSomething() throws Exception {
        // Get already existing entity
        Scenarioexpectedoutput scenarioexpectedoutput = scenarioscore.getScenarioexpectedoutput();
        scenarioscoreRepository.saveAndFlush(scenarioscore);
        Long scenarioexpectedoutputId = scenarioexpectedoutput.getId();

        // Get all the scenarioscoreList where scenarioexpectedoutput equals to scenarioexpectedoutputId
        defaultScenarioscoreShouldBeFound("scenarioexpectedoutputId.equals=" + scenarioexpectedoutputId);

        // Get all the scenarioscoreList where scenarioexpectedoutput equals to scenarioexpectedoutputId + 1
        defaultScenarioscoreShouldNotBeFound("scenarioexpectedoutputId.equals=" + (scenarioexpectedoutputId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScenarioscoreShouldBeFound(String filter) throws Exception {
        restScenarioscoreMockMvc.perform(get("/api/scenarioscores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenarioscore.getId().intValue())))
            .andExpect(jsonPath("$.[*].passed").value(hasItem(DEFAULT_PASSED.booleanValue())));

        // Check, that the count call also returns 1
        restScenarioscoreMockMvc.perform(get("/api/scenarioscores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScenarioscoreShouldNotBeFound(String filter) throws Exception {
        restScenarioscoreMockMvc.perform(get("/api/scenarioscores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScenarioscoreMockMvc.perform(get("/api/scenarioscores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingScenarioscore() throws Exception {
        // Get the scenarioscore
        restScenarioscoreMockMvc.perform(get("/api/scenarioscores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScenarioscore() throws Exception {
        // Initialize the database
        scenarioscoreRepository.saveAndFlush(scenarioscore);

        int databaseSizeBeforeUpdate = scenarioscoreRepository.findAll().size();

        // Update the scenarioscore
        Scenarioscore updatedScenarioscore = scenarioscoreRepository.findById(scenarioscore.getId()).get();
        // Disconnect from session so that the updates on updatedScenarioscore are not directly saved in db
        em.detach(updatedScenarioscore);
        updatedScenarioscore
            .passed(UPDATED_PASSED);
        ScenarioscoreDTO scenarioscoreDTO = scenarioscoreMapper.toDto(updatedScenarioscore);

        restScenarioscoreMockMvc.perform(put("/api/scenarioscores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioscoreDTO)))
            .andExpect(status().isOk());

        // Validate the Scenarioscore in the database
        List<Scenarioscore> scenarioscoreList = scenarioscoreRepository.findAll();
        assertThat(scenarioscoreList).hasSize(databaseSizeBeforeUpdate);
        Scenarioscore testScenarioscore = scenarioscoreList.get(scenarioscoreList.size() - 1);
        assertThat(testScenarioscore.isPassed()).isEqualTo(UPDATED_PASSED);
    }

    @Test
    @Transactional
    public void updateNonExistingScenarioscore() throws Exception {
        int databaseSizeBeforeUpdate = scenarioscoreRepository.findAll().size();

        // Create the Scenarioscore
        ScenarioscoreDTO scenarioscoreDTO = scenarioscoreMapper.toDto(scenarioscore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScenarioscoreMockMvc.perform(put("/api/scenarioscores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scenarioscoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scenarioscore in the database
        List<Scenarioscore> scenarioscoreList = scenarioscoreRepository.findAll();
        assertThat(scenarioscoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScenarioscore() throws Exception {
        // Initialize the database
        scenarioscoreRepository.saveAndFlush(scenarioscore);

        int databaseSizeBeforeDelete = scenarioscoreRepository.findAll().size();

        // Delete the scenarioscore
        restScenarioscoreMockMvc.perform(delete("/api/scenarioscores/{id}", scenarioscore.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scenarioscore> scenarioscoreList = scenarioscoreRepository.findAll();
        assertThat(scenarioscoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
