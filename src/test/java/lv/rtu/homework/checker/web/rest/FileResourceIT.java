package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.RtuHomeworkCheckerApp;
import lv.rtu.homework.checker.domain.File;
import lv.rtu.homework.checker.domain.Task;
import lv.rtu.homework.checker.domain.Variant;
import lv.rtu.homework.checker.domain.Student;
import lv.rtu.homework.checker.repository.FileRepository;
import lv.rtu.homework.checker.service.FileService;
import lv.rtu.homework.checker.service.dto.FileDTO;
import lv.rtu.homework.checker.service.mapper.FileMapper;
import lv.rtu.homework.checker.service.dto.FileCriteria;
import lv.rtu.homework.checker.service.FileQueryService;

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
 * Integration tests for the {@link FileResource} REST controller.
 */
@SpringBootTest(classes = RtuHomeworkCheckerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FileResourceIT {

    private static final Instant DEFAULT_MODIFIEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileQueryService fileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileMockMvc;

    private File file;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static File createEntity(EntityManager em) {
        File file = new File()
            .modifiedat(DEFAULT_MODIFIEDAT)
            .deletedat(DEFAULT_DELETEDAT)
            .createdat(DEFAULT_CREATEDAT)
            .filename(DEFAULT_FILENAME);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        file.setTask(task);
        // Add required entity
        Variant variant;
        if (TestUtil.findAll(em, Variant.class).isEmpty()) {
            variant = VariantResourceIT.createEntity(em);
            em.persist(variant);
            em.flush();
        } else {
            variant = TestUtil.findAll(em, Variant.class).get(0);
        }
        file.setVariant(variant);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        file.setStudent(student);
        return file;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static File createUpdatedEntity(EntityManager em) {
        File file = new File()
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .createdat(UPDATED_CREATEDAT)
            .filename(UPDATED_FILENAME);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createUpdatedEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        file.setTask(task);
        // Add required entity
        Variant variant;
        if (TestUtil.findAll(em, Variant.class).isEmpty()) {
            variant = VariantResourceIT.createUpdatedEntity(em);
            em.persist(variant);
            em.flush();
        } else {
            variant = TestUtil.findAll(em, Variant.class).get(0);
        }
        file.setVariant(variant);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createUpdatedEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        file.setStudent(student);
        return file;
    }

    @BeforeEach
    public void initTest() {
        file = createEntity(em);
    }

    @Test
    @Transactional
    public void createFile() throws Exception {
        int databaseSizeBeforeCreate = fileRepository.findAll().size();
        // Create the File
        FileDTO fileDTO = fileMapper.toDto(file);
        restFileMockMvc.perform(post("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isCreated());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate + 1);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.getModifiedat()).isEqualTo(DEFAULT_MODIFIEDAT);
        assertThat(testFile.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
        assertThat(testFile.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFile.getFilename()).isEqualTo(DEFAULT_FILENAME);
    }

    @Test
    @Transactional
    public void createFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileRepository.findAll().size();

        // Create the File with an existing ID
        file.setId(1L);
        FileDTO fileDTO = fileMapper.toDto(file);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileMockMvc.perform(post("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileRepository.findAll().size();
        // set the field null
        file.setCreatedat(null);

        // Create the File, which fails.
        FileDTO fileDTO = fileMapper.toDto(file);


        restFileMockMvc.perform(post("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isBadRequest());

        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileRepository.findAll().size();
        // set the field null
        file.setFilename(null);

        // Create the File, which fails.
        FileDTO fileDTO = fileMapper.toDto(file);


        restFileMockMvc.perform(post("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isBadRequest());

        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFiles() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList
        restFileMockMvc.perform(get("/api/files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(file.getId().intValue())))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)));
    }
    
    @Test
    @Transactional
    public void getFile() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get the file
        restFileMockMvc.perform(get("/api/files/{id}", file.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(file.getId().intValue()))
            .andExpect(jsonPath("$.modifiedat").value(DEFAULT_MODIFIEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME));
    }


    @Test
    @Transactional
    public void getFilesByIdFiltering() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        Long id = file.getId();

        defaultFileShouldBeFound("id.equals=" + id);
        defaultFileShouldNotBeFound("id.notEquals=" + id);

        defaultFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFileShouldNotBeFound("id.greaterThan=" + id);

        defaultFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFilesByModifiedatIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where modifiedat equals to DEFAULT_MODIFIEDAT
        defaultFileShouldBeFound("modifiedat.equals=" + DEFAULT_MODIFIEDAT);

        // Get all the fileList where modifiedat equals to UPDATED_MODIFIEDAT
        defaultFileShouldNotBeFound("modifiedat.equals=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllFilesByModifiedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where modifiedat not equals to DEFAULT_MODIFIEDAT
        defaultFileShouldNotBeFound("modifiedat.notEquals=" + DEFAULT_MODIFIEDAT);

        // Get all the fileList where modifiedat not equals to UPDATED_MODIFIEDAT
        defaultFileShouldBeFound("modifiedat.notEquals=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllFilesByModifiedatIsInShouldWork() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where modifiedat in DEFAULT_MODIFIEDAT or UPDATED_MODIFIEDAT
        defaultFileShouldBeFound("modifiedat.in=" + DEFAULT_MODIFIEDAT + "," + UPDATED_MODIFIEDAT);

        // Get all the fileList where modifiedat equals to UPDATED_MODIFIEDAT
        defaultFileShouldNotBeFound("modifiedat.in=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllFilesByModifiedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where modifiedat is not null
        defaultFileShouldBeFound("modifiedat.specified=true");

        // Get all the fileList where modifiedat is null
        defaultFileShouldNotBeFound("modifiedat.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilesByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where deletedat equals to DEFAULT_DELETEDAT
        defaultFileShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the fileList where deletedat equals to UPDATED_DELETEDAT
        defaultFileShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllFilesByDeletedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where deletedat not equals to DEFAULT_DELETEDAT
        defaultFileShouldNotBeFound("deletedat.notEquals=" + DEFAULT_DELETEDAT);

        // Get all the fileList where deletedat not equals to UPDATED_DELETEDAT
        defaultFileShouldBeFound("deletedat.notEquals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllFilesByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultFileShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the fileList where deletedat equals to UPDATED_DELETEDAT
        defaultFileShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllFilesByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where deletedat is not null
        defaultFileShouldBeFound("deletedat.specified=true");

        // Get all the fileList where deletedat is null
        defaultFileShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where createdat equals to DEFAULT_CREATEDAT
        defaultFileShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the fileList where createdat equals to UPDATED_CREATEDAT
        defaultFileShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllFilesByCreatedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where createdat not equals to DEFAULT_CREATEDAT
        defaultFileShouldNotBeFound("createdat.notEquals=" + DEFAULT_CREATEDAT);

        // Get all the fileList where createdat not equals to UPDATED_CREATEDAT
        defaultFileShouldBeFound("createdat.notEquals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllFilesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultFileShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the fileList where createdat equals to UPDATED_CREATEDAT
        defaultFileShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllFilesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where createdat is not null
        defaultFileShouldBeFound("createdat.specified=true");

        // Get all the fileList where createdat is null
        defaultFileShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilesByFilenameIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where filename equals to DEFAULT_FILENAME
        defaultFileShouldBeFound("filename.equals=" + DEFAULT_FILENAME);

        // Get all the fileList where filename equals to UPDATED_FILENAME
        defaultFileShouldNotBeFound("filename.equals=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void getAllFilesByFilenameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where filename not equals to DEFAULT_FILENAME
        defaultFileShouldNotBeFound("filename.notEquals=" + DEFAULT_FILENAME);

        // Get all the fileList where filename not equals to UPDATED_FILENAME
        defaultFileShouldBeFound("filename.notEquals=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void getAllFilesByFilenameIsInShouldWork() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where filename in DEFAULT_FILENAME or UPDATED_FILENAME
        defaultFileShouldBeFound("filename.in=" + DEFAULT_FILENAME + "," + UPDATED_FILENAME);

        // Get all the fileList where filename equals to UPDATED_FILENAME
        defaultFileShouldNotBeFound("filename.in=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void getAllFilesByFilenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where filename is not null
        defaultFileShouldBeFound("filename.specified=true");

        // Get all the fileList where filename is null
        defaultFileShouldNotBeFound("filename.specified=false");
    }
                @Test
    @Transactional
    public void getAllFilesByFilenameContainsSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where filename contains DEFAULT_FILENAME
        defaultFileShouldBeFound("filename.contains=" + DEFAULT_FILENAME);

        // Get all the fileList where filename contains UPDATED_FILENAME
        defaultFileShouldNotBeFound("filename.contains=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void getAllFilesByFilenameNotContainsSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where filename does not contain DEFAULT_FILENAME
        defaultFileShouldNotBeFound("filename.doesNotContain=" + DEFAULT_FILENAME);

        // Get all the fileList where filename does not contain UPDATED_FILENAME
        defaultFileShouldBeFound("filename.doesNotContain=" + UPDATED_FILENAME);
    }


    @Test
    @Transactional
    public void getAllFilesByTaskIsEqualToSomething() throws Exception {
        // Get already existing entity
        Task task = file.getTask();
        fileRepository.saveAndFlush(file);
        Long taskId = task.getId();

        // Get all the fileList where task equals to taskId
        defaultFileShouldBeFound("taskId.equals=" + taskId);

        // Get all the fileList where task equals to taskId + 1
        defaultFileShouldNotBeFound("taskId.equals=" + (taskId + 1));
    }


    @Test
    @Transactional
    public void getAllFilesByVariantIsEqualToSomething() throws Exception {
        // Get already existing entity
        Variant variant = file.getVariant();
        fileRepository.saveAndFlush(file);
        Long variantId = variant.getId();

        // Get all the fileList where variant equals to variantId
        defaultFileShouldBeFound("variantId.equals=" + variantId);

        // Get all the fileList where variant equals to variantId + 1
        defaultFileShouldNotBeFound("variantId.equals=" + (variantId + 1));
    }


    @Test
    @Transactional
    public void getAllFilesByStudentIsEqualToSomething() throws Exception {
        // Get already existing entity
        Student student = file.getStudent();
        fileRepository.saveAndFlush(file);
        Long studentId = student.getId();

        // Get all the fileList where student equals to studentId
        defaultFileShouldBeFound("studentId.equals=" + studentId);

        // Get all the fileList where student equals to studentId + 1
        defaultFileShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFileShouldBeFound(String filter) throws Exception {
        restFileMockMvc.perform(get("/api/files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(file.getId().intValue())))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)));

        // Check, that the count call also returns 1
        restFileMockMvc.perform(get("/api/files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFileShouldNotBeFound(String filter) throws Exception {
        restFileMockMvc.perform(get("/api/files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFileMockMvc.perform(get("/api/files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFile() throws Exception {
        // Get the file
        restFileMockMvc.perform(get("/api/files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFile() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // Update the file
        File updatedFile = fileRepository.findById(file.getId()).get();
        // Disconnect from session so that the updates on updatedFile are not directly saved in db
        em.detach(updatedFile);
        updatedFile
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .createdat(UPDATED_CREATEDAT)
            .filename(UPDATED_FILENAME);
        FileDTO fileDTO = fileMapper.toDto(updatedFile);

        restFileMockMvc.perform(put("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isOk());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.getModifiedat()).isEqualTo(UPDATED_MODIFIEDAT);
        assertThat(testFile.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
        assertThat(testFile.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFile.getFilename()).isEqualTo(UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFile() throws Exception {
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // Create the File
        FileDTO fileDTO = fileMapper.toDto(file);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileMockMvc.perform(put("/api/files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFile() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        int databaseSizeBeforeDelete = fileRepository.findAll().size();

        // Delete the file
        restFileMockMvc.perform(delete("/api/files/{id}", file.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
