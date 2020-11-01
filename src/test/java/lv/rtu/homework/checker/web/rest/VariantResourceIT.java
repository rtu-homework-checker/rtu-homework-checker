package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.RtuHomeworkCheckerApp;
import lv.rtu.homework.checker.domain.Variant;
import lv.rtu.homework.checker.domain.Task;
import lv.rtu.homework.checker.repository.VariantRepository;
import lv.rtu.homework.checker.service.VariantService;
import lv.rtu.homework.checker.service.dto.VariantDTO;
import lv.rtu.homework.checker.service.mapper.VariantMapper;
import lv.rtu.homework.checker.service.dto.VariantCriteria;
import lv.rtu.homework.checker.service.VariantQueryService;

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
 * Integration tests for the {@link VariantResource} REST controller.
 */
@SpringBootTest(classes = RtuHomeworkCheckerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VariantResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private VariantMapper variantMapper;

    @Autowired
    private VariantService variantService;

    @Autowired
    private VariantQueryService variantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVariantMockMvc;

    private Variant variant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Variant createEntity(EntityManager em) {
        Variant variant = new Variant()
            .title(DEFAULT_TITLE)
            .modifiedat(DEFAULT_MODIFIEDAT)
            .deletedat(DEFAULT_DELETEDAT)
            .createdat(DEFAULT_CREATEDAT);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        variant.setTask(task);
        return variant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Variant createUpdatedEntity(EntityManager em) {
        Variant variant = new Variant()
            .title(UPDATED_TITLE)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .createdat(UPDATED_CREATEDAT);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createUpdatedEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        variant.setTask(task);
        return variant;
    }

    @BeforeEach
    public void initTest() {
        variant = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariant() throws Exception {
        int databaseSizeBeforeCreate = variantRepository.findAll().size();
        // Create the Variant
        VariantDTO variantDTO = variantMapper.toDto(variant);
        restVariantMockMvc.perform(post("/api/variants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variantDTO)))
            .andExpect(status().isCreated());

        // Validate the Variant in the database
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeCreate + 1);
        Variant testVariant = variantList.get(variantList.size() - 1);
        assertThat(testVariant.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testVariant.getModifiedat()).isEqualTo(DEFAULT_MODIFIEDAT);
        assertThat(testVariant.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
        assertThat(testVariant.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
    }

    @Test
    @Transactional
    public void createVariantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = variantRepository.findAll().size();

        // Create the Variant with an existing ID
        variant.setId(1L);
        VariantDTO variantDTO = variantMapper.toDto(variant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVariantMockMvc.perform(post("/api/variants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Variant in the database
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = variantRepository.findAll().size();
        // set the field null
        variant.setCreatedat(null);

        // Create the Variant, which fails.
        VariantDTO variantDTO = variantMapper.toDto(variant);


        restVariantMockMvc.perform(post("/api/variants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variantDTO)))
            .andExpect(status().isBadRequest());

        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVariants() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList
        restVariantMockMvc.perform(get("/api/variants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variant.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())));
    }
    
    @Test
    @Transactional
    public void getVariant() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get the variant
        restVariantMockMvc.perform(get("/api/variants/{id}", variant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(variant.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.modifiedat").value(DEFAULT_MODIFIEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()));
    }


    @Test
    @Transactional
    public void getVariantsByIdFiltering() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        Long id = variant.getId();

        defaultVariantShouldBeFound("id.equals=" + id);
        defaultVariantShouldNotBeFound("id.notEquals=" + id);

        defaultVariantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVariantShouldNotBeFound("id.greaterThan=" + id);

        defaultVariantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVariantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVariantsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where title equals to DEFAULT_TITLE
        defaultVariantShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the variantList where title equals to UPDATED_TITLE
        defaultVariantShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllVariantsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where title not equals to DEFAULT_TITLE
        defaultVariantShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the variantList where title not equals to UPDATED_TITLE
        defaultVariantShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllVariantsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultVariantShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the variantList where title equals to UPDATED_TITLE
        defaultVariantShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllVariantsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where title is not null
        defaultVariantShouldBeFound("title.specified=true");

        // Get all the variantList where title is null
        defaultVariantShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllVariantsByTitleContainsSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where title contains DEFAULT_TITLE
        defaultVariantShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the variantList where title contains UPDATED_TITLE
        defaultVariantShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllVariantsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where title does not contain DEFAULT_TITLE
        defaultVariantShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the variantList where title does not contain UPDATED_TITLE
        defaultVariantShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllVariantsByModifiedatIsEqualToSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where modifiedat equals to DEFAULT_MODIFIEDAT
        defaultVariantShouldBeFound("modifiedat.equals=" + DEFAULT_MODIFIEDAT);

        // Get all the variantList where modifiedat equals to UPDATED_MODIFIEDAT
        defaultVariantShouldNotBeFound("modifiedat.equals=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllVariantsByModifiedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where modifiedat not equals to DEFAULT_MODIFIEDAT
        defaultVariantShouldNotBeFound("modifiedat.notEquals=" + DEFAULT_MODIFIEDAT);

        // Get all the variantList where modifiedat not equals to UPDATED_MODIFIEDAT
        defaultVariantShouldBeFound("modifiedat.notEquals=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllVariantsByModifiedatIsInShouldWork() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where modifiedat in DEFAULT_MODIFIEDAT or UPDATED_MODIFIEDAT
        defaultVariantShouldBeFound("modifiedat.in=" + DEFAULT_MODIFIEDAT + "," + UPDATED_MODIFIEDAT);

        // Get all the variantList where modifiedat equals to UPDATED_MODIFIEDAT
        defaultVariantShouldNotBeFound("modifiedat.in=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllVariantsByModifiedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where modifiedat is not null
        defaultVariantShouldBeFound("modifiedat.specified=true");

        // Get all the variantList where modifiedat is null
        defaultVariantShouldNotBeFound("modifiedat.specified=false");
    }

    @Test
    @Transactional
    public void getAllVariantsByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where deletedat equals to DEFAULT_DELETEDAT
        defaultVariantShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the variantList where deletedat equals to UPDATED_DELETEDAT
        defaultVariantShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllVariantsByDeletedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where deletedat not equals to DEFAULT_DELETEDAT
        defaultVariantShouldNotBeFound("deletedat.notEquals=" + DEFAULT_DELETEDAT);

        // Get all the variantList where deletedat not equals to UPDATED_DELETEDAT
        defaultVariantShouldBeFound("deletedat.notEquals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllVariantsByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultVariantShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the variantList where deletedat equals to UPDATED_DELETEDAT
        defaultVariantShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllVariantsByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where deletedat is not null
        defaultVariantShouldBeFound("deletedat.specified=true");

        // Get all the variantList where deletedat is null
        defaultVariantShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    public void getAllVariantsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where createdat equals to DEFAULT_CREATEDAT
        defaultVariantShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the variantList where createdat equals to UPDATED_CREATEDAT
        defaultVariantShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllVariantsByCreatedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where createdat not equals to DEFAULT_CREATEDAT
        defaultVariantShouldNotBeFound("createdat.notEquals=" + DEFAULT_CREATEDAT);

        // Get all the variantList where createdat not equals to UPDATED_CREATEDAT
        defaultVariantShouldBeFound("createdat.notEquals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllVariantsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultVariantShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the variantList where createdat equals to UPDATED_CREATEDAT
        defaultVariantShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllVariantsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList where createdat is not null
        defaultVariantShouldBeFound("createdat.specified=true");

        // Get all the variantList where createdat is null
        defaultVariantShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    public void getAllVariantsByTaskIsEqualToSomething() throws Exception {
        // Get already existing entity
        Task task = variant.getTask();
        variantRepository.saveAndFlush(variant);
        Long taskId = task.getId();

        // Get all the variantList where task equals to taskId
        defaultVariantShouldBeFound("taskId.equals=" + taskId);

        // Get all the variantList where task equals to taskId + 1
        defaultVariantShouldNotBeFound("taskId.equals=" + (taskId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVariantShouldBeFound(String filter) throws Exception {
        restVariantMockMvc.perform(get("/api/variants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variant.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())));

        // Check, that the count call also returns 1
        restVariantMockMvc.perform(get("/api/variants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVariantShouldNotBeFound(String filter) throws Exception {
        restVariantMockMvc.perform(get("/api/variants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVariantMockMvc.perform(get("/api/variants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVariant() throws Exception {
        // Get the variant
        restVariantMockMvc.perform(get("/api/variants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariant() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        int databaseSizeBeforeUpdate = variantRepository.findAll().size();

        // Update the variant
        Variant updatedVariant = variantRepository.findById(variant.getId()).get();
        // Disconnect from session so that the updates on updatedVariant are not directly saved in db
        em.detach(updatedVariant);
        updatedVariant
            .title(UPDATED_TITLE)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .createdat(UPDATED_CREATEDAT);
        VariantDTO variantDTO = variantMapper.toDto(updatedVariant);

        restVariantMockMvc.perform(put("/api/variants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variantDTO)))
            .andExpect(status().isOk());

        // Validate the Variant in the database
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeUpdate);
        Variant testVariant = variantList.get(variantList.size() - 1);
        assertThat(testVariant.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVariant.getModifiedat()).isEqualTo(UPDATED_MODIFIEDAT);
        assertThat(testVariant.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
        assertThat(testVariant.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void updateNonExistingVariant() throws Exception {
        int databaseSizeBeforeUpdate = variantRepository.findAll().size();

        // Create the Variant
        VariantDTO variantDTO = variantMapper.toDto(variant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVariantMockMvc.perform(put("/api/variants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Variant in the database
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVariant() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        int databaseSizeBeforeDelete = variantRepository.findAll().size();

        // Delete the variant
        restVariantMockMvc.perform(delete("/api/variants/{id}", variant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
