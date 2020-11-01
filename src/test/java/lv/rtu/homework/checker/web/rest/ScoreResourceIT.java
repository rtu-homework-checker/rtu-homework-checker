package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.RtuHomeworkCheckerApp;
import lv.rtu.homework.checker.domain.Score;
import lv.rtu.homework.checker.domain.File;
import lv.rtu.homework.checker.domain.Variant;
import lv.rtu.homework.checker.repository.ScoreRepository;
import lv.rtu.homework.checker.service.ScoreService;
import lv.rtu.homework.checker.service.dto.ScoreDTO;
import lv.rtu.homework.checker.service.mapper.ScoreMapper;
import lv.rtu.homework.checker.service.dto.ScoreCriteria;
import lv.rtu.homework.checker.service.ScoreQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScoreResource} REST controller.
 */
@SpringBootTest(classes = RtuHomeworkCheckerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScoreResourceIT {

    private static final Boolean DEFAULT_ISPASSED = false;
    private static final Boolean UPDATED_ISPASSED = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFIEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreQueryService scoreQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScoreMockMvc;

    private Score score;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Score createEntity(EntityManager em) {
        Score score = new Score()
            .ispassed(DEFAULT_ISPASSED)
            .createdat(DEFAULT_CREATEDAT)
            .modifiedat(DEFAULT_MODIFIEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        // Add required entity
        File file;
        if (TestUtil.findAll(em, File.class).isEmpty()) {
            file = FileResourceIT.createEntity(em);
            em.persist(file);
            em.flush();
        } else {
            file = TestUtil.findAll(em, File.class).get(0);
        }
        score.setFile(file);
        // Add required entity
        Variant variant;
        if (TestUtil.findAll(em, Variant.class).isEmpty()) {
            variant = VariantResourceIT.createEntity(em);
            em.persist(variant);
            em.flush();
        } else {
            variant = TestUtil.findAll(em, Variant.class).get(0);
        }
        score.setVariant(variant);
        return score;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Score createUpdatedEntity(EntityManager em) {
        Score score = new Score()
            .ispassed(UPDATED_ISPASSED)
            .createdat(UPDATED_CREATEDAT)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT);
        // Add required entity
        File file;
        if (TestUtil.findAll(em, File.class).isEmpty()) {
            file = FileResourceIT.createUpdatedEntity(em);
            em.persist(file);
            em.flush();
        } else {
            file = TestUtil.findAll(em, File.class).get(0);
        }
        score.setFile(file);
        // Add required entity
        Variant variant;
        if (TestUtil.findAll(em, Variant.class).isEmpty()) {
            variant = VariantResourceIT.createUpdatedEntity(em);
            em.persist(variant);
            em.flush();
        } else {
            variant = TestUtil.findAll(em, Variant.class).get(0);
        }
        score.setVariant(variant);
        return score;
    }

    @BeforeEach
    public void initTest() {
        score = createEntity(em);
    }

    @Test
    @Transactional
    public void createScore() throws Exception {
        int databaseSizeBeforeCreate = scoreRepository.findAll().size();
        // Create the Score
        ScoreDTO scoreDTO = scoreMapper.toDto(score);
        restScoreMockMvc.perform(post("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isCreated());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeCreate + 1);
        Score testScore = scoreList.get(scoreList.size() - 1);
        assertThat(testScore.isIspassed()).isEqualTo(DEFAULT_ISPASSED);
        assertThat(testScore.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testScore.getModifiedat()).isEqualTo(DEFAULT_MODIFIEDAT);
        assertThat(testScore.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    public void createScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scoreRepository.findAll().size();

        // Create the Score with an existing ID
        score.setId(1L);
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoreMockMvc.perform(post("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIspassedIsRequired() throws Exception {
        int databaseSizeBeforeTest = scoreRepository.findAll().size();
        // set the field null
        score.setIspassed(null);

        // Create the Score, which fails.
        ScoreDTO scoreDTO = scoreMapper.toDto(score);


        restScoreMockMvc.perform(post("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = scoreRepository.findAll().size();
        // set the field null
        score.setCreatedat(null);

        // Create the Score, which fails.
        ScoreDTO scoreDTO = scoreMapper.toDto(score);


        restScoreMockMvc.perform(post("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScores() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList
        restScoreMockMvc.perform(get("/api/scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(score.getId().intValue())))
            .andExpect(jsonPath("$.[*].ispassed").value(hasItem(DEFAULT_ISPASSED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }
    
    @Test
    @Transactional
    public void getScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get the score
        restScoreMockMvc.perform(get("/api/scores/{id}", score.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(score.getId().intValue()))
            .andExpect(jsonPath("$.ispassed").value(DEFAULT_ISPASSED.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.modifiedat").value(DEFAULT_MODIFIEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }


    @Test
    @Transactional
    public void getScoresByIdFiltering() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        Long id = score.getId();

        defaultScoreShouldBeFound("id.equals=" + id);
        defaultScoreShouldNotBeFound("id.notEquals=" + id);

        defaultScoreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultScoreShouldNotBeFound("id.greaterThan=" + id);

        defaultScoreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultScoreShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllScoresByIspassedIsEqualToSomething() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where ispassed equals to DEFAULT_ISPASSED
        defaultScoreShouldBeFound("ispassed.equals=" + DEFAULT_ISPASSED);

        // Get all the scoreList where ispassed equals to UPDATED_ISPASSED
        defaultScoreShouldNotBeFound("ispassed.equals=" + UPDATED_ISPASSED);
    }

    @Test
    @Transactional
    public void getAllScoresByIspassedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where ispassed not equals to DEFAULT_ISPASSED
        defaultScoreShouldNotBeFound("ispassed.notEquals=" + DEFAULT_ISPASSED);

        // Get all the scoreList where ispassed not equals to UPDATED_ISPASSED
        defaultScoreShouldBeFound("ispassed.notEquals=" + UPDATED_ISPASSED);
    }

    @Test
    @Transactional
    public void getAllScoresByIspassedIsInShouldWork() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where ispassed in DEFAULT_ISPASSED or UPDATED_ISPASSED
        defaultScoreShouldBeFound("ispassed.in=" + DEFAULT_ISPASSED + "," + UPDATED_ISPASSED);

        // Get all the scoreList where ispassed equals to UPDATED_ISPASSED
        defaultScoreShouldNotBeFound("ispassed.in=" + UPDATED_ISPASSED);
    }

    @Test
    @Transactional
    public void getAllScoresByIspassedIsNullOrNotNull() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where ispassed is not null
        defaultScoreShouldBeFound("ispassed.specified=true");

        // Get all the scoreList where ispassed is null
        defaultScoreShouldNotBeFound("ispassed.specified=false");
    }

    @Test
    @Transactional
    public void getAllScoresByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where createdat equals to DEFAULT_CREATEDAT
        defaultScoreShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the scoreList where createdat equals to UPDATED_CREATEDAT
        defaultScoreShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllScoresByCreatedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where createdat not equals to DEFAULT_CREATEDAT
        defaultScoreShouldNotBeFound("createdat.notEquals=" + DEFAULT_CREATEDAT);

        // Get all the scoreList where createdat not equals to UPDATED_CREATEDAT
        defaultScoreShouldBeFound("createdat.notEquals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllScoresByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultScoreShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the scoreList where createdat equals to UPDATED_CREATEDAT
        defaultScoreShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllScoresByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where createdat is not null
        defaultScoreShouldBeFound("createdat.specified=true");

        // Get all the scoreList where createdat is null
        defaultScoreShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    public void getAllScoresByModifiedatIsEqualToSomething() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where modifiedat equals to DEFAULT_MODIFIEDAT
        defaultScoreShouldBeFound("modifiedat.equals=" + DEFAULT_MODIFIEDAT);

        // Get all the scoreList where modifiedat equals to UPDATED_MODIFIEDAT
        defaultScoreShouldNotBeFound("modifiedat.equals=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllScoresByModifiedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where modifiedat not equals to DEFAULT_MODIFIEDAT
        defaultScoreShouldNotBeFound("modifiedat.notEquals=" + DEFAULT_MODIFIEDAT);

        // Get all the scoreList where modifiedat not equals to UPDATED_MODIFIEDAT
        defaultScoreShouldBeFound("modifiedat.notEquals=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllScoresByModifiedatIsInShouldWork() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where modifiedat in DEFAULT_MODIFIEDAT or UPDATED_MODIFIEDAT
        defaultScoreShouldBeFound("modifiedat.in=" + DEFAULT_MODIFIEDAT + "," + UPDATED_MODIFIEDAT);

        // Get all the scoreList where modifiedat equals to UPDATED_MODIFIEDAT
        defaultScoreShouldNotBeFound("modifiedat.in=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllScoresByModifiedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where modifiedat is not null
        defaultScoreShouldBeFound("modifiedat.specified=true");

        // Get all the scoreList where modifiedat is null
        defaultScoreShouldNotBeFound("modifiedat.specified=false");
    }

    @Test
    @Transactional
    public void getAllScoresByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where deletedat equals to DEFAULT_DELETEDAT
        defaultScoreShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the scoreList where deletedat equals to UPDATED_DELETEDAT
        defaultScoreShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllScoresByDeletedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where deletedat not equals to DEFAULT_DELETEDAT
        defaultScoreShouldNotBeFound("deletedat.notEquals=" + DEFAULT_DELETEDAT);

        // Get all the scoreList where deletedat not equals to UPDATED_DELETEDAT
        defaultScoreShouldBeFound("deletedat.notEquals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllScoresByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultScoreShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the scoreList where deletedat equals to UPDATED_DELETEDAT
        defaultScoreShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllScoresByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList where deletedat is not null
        defaultScoreShouldBeFound("deletedat.specified=true");

        // Get all the scoreList where deletedat is null
        defaultScoreShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    public void getAllScoresByFileIsEqualToSomething() throws Exception {
        // Get already existing entity
        File file = score.getFile();
        scoreRepository.saveAndFlush(score);
        Long fileId = file.getId();

        // Get all the scoreList where file equals to fileId
        defaultScoreShouldBeFound("fileId.equals=" + fileId);

        // Get all the scoreList where file equals to fileId + 1
        defaultScoreShouldNotBeFound("fileId.equals=" + (fileId + 1));
    }


    @Test
    @Transactional
    public void getAllScoresByVariantIsEqualToSomething() throws Exception {
        // Get already existing entity
        Variant variant = score.getVariant();
        scoreRepository.saveAndFlush(score);
        Long variantId = variant.getId();

        // Get all the scoreList where variant equals to variantId
        defaultScoreShouldBeFound("variantId.equals=" + variantId);

        // Get all the scoreList where variant equals to variantId + 1
        defaultScoreShouldNotBeFound("variantId.equals=" + (variantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScoreShouldBeFound(String filter) throws Exception {
        restScoreMockMvc.perform(get("/api/scores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(score.getId().intValue())))
            .andExpect(jsonPath("$.[*].ispassed").value(hasItem(DEFAULT_ISPASSED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restScoreMockMvc.perform(get("/api/scores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScoreShouldNotBeFound(String filter) throws Exception {
        restScoreMockMvc.perform(get("/api/scores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScoreMockMvc.perform(get("/api/scores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingScore() throws Exception {
        // Get the score
        restScoreMockMvc.perform(get("/api/scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        // Update the score
        Score updatedScore = scoreRepository.findById(score.getId()).get();
        // Disconnect from session so that the updates on updatedScore are not directly saved in db
        em.detach(updatedScore);
        updatedScore
            .ispassed(UPDATED_ISPASSED)
            .createdat(UPDATED_CREATEDAT)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT);
        ScoreDTO scoreDTO = scoreMapper.toDto(updatedScore);

        restScoreMockMvc.perform(put("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isOk());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);
        Score testScore = scoreList.get(scoreList.size() - 1);
        assertThat(testScore.isIspassed()).isEqualTo(UPDATED_ISPASSED);
        assertThat(testScore.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testScore.getModifiedat()).isEqualTo(UPDATED_MODIFIEDAT);
        assertThat(testScore.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void updateNonExistingScore() throws Exception {
        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        // Create the Score
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoreMockMvc.perform(put("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        int databaseSizeBeforeDelete = scoreRepository.findAll().size();

        // Delete the score
        restScoreMockMvc.perform(delete("/api/scores/{id}", score.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
