package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.RtuHomeworkCheckerApp;
import lv.rtu.homework.checker.domain.Task;
import lv.rtu.homework.checker.domain.User;
import lv.rtu.homework.checker.repository.TaskRepository;
import lv.rtu.homework.checker.service.TaskService;
import lv.rtu.homework.checker.service.dto.TaskDTO;
import lv.rtu.homework.checker.service.mapper.TaskMapper;
import lv.rtu.homework.checker.service.dto.TaskCriteria;
import lv.rtu.homework.checker.service.TaskQueryService;

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
 * Integration tests for the {@link TaskResource} REST controller.
 */
@SpringBootTest(classes = RtuHomeworkCheckerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HASVARIANTS = false;
    private static final Boolean UPDATED_HASVARIANTS = true;

    private static final Instant DEFAULT_MODIFIEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskQueryService taskQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskMockMvc;

    private Task task;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .title(DEFAULT_TITLE)
            .hasvariants(DEFAULT_HASVARIANTS)
            .modifiedat(DEFAULT_MODIFIEDAT)
            .deletedat(DEFAULT_DELETEDAT)
            .createdat(DEFAULT_CREATEDAT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        task.setUser(user);
        return task;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity(EntityManager em) {
        Task task = new Task()
            .title(UPDATED_TITLE)
            .hasvariants(UPDATED_HASVARIANTS)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .createdat(UPDATED_CREATEDAT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        task.setUser(user);
        return task;
    }

    @BeforeEach
    public void initTest() {
        task = createEntity(em);
    }

    @Test
    @Transactional
    public void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();
        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTask.isHasvariants()).isEqualTo(DEFAULT_HASVARIANTS);
        assertThat(testTask.getModifiedat()).isEqualTo(DEFAULT_MODIFIEDAT);
        assertThat(testTask.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
        assertThat(testTask.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
    }

    @Test
    @Transactional
    public void createTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task with an existing ID
        task.setId(1L);
        TaskDTO taskDTO = taskMapper.toDto(task);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setTitle(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);


        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasvariantsIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setHasvariants(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);


        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].hasvariants").value(hasItem(DEFAULT_HASVARIANTS.booleanValue())))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())));
    }
    
    @Test
    @Transactional
    public void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.hasvariants").value(DEFAULT_HASVARIANTS.booleanValue()))
            .andExpect(jsonPath("$.modifiedat").value(DEFAULT_MODIFIEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()));
    }


    @Test
    @Transactional
    public void getTasksByIdFiltering() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        Long id = task.getId();

        defaultTaskShouldBeFound("id.equals=" + id);
        defaultTaskShouldNotBeFound("id.notEquals=" + id);

        defaultTaskShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTaskShouldNotBeFound("id.greaterThan=" + id);

        defaultTaskShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTaskShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTasksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title equals to DEFAULT_TITLE
        defaultTaskShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the taskList where title equals to UPDATED_TITLE
        defaultTaskShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTasksByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title not equals to DEFAULT_TITLE
        defaultTaskShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the taskList where title not equals to UPDATED_TITLE
        defaultTaskShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTasksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultTaskShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the taskList where title equals to UPDATED_TITLE
        defaultTaskShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTasksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title is not null
        defaultTaskShouldBeFound("title.specified=true");

        // Get all the taskList where title is null
        defaultTaskShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllTasksByTitleContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title contains DEFAULT_TITLE
        defaultTaskShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the taskList where title contains UPDATED_TITLE
        defaultTaskShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTasksByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title does not contain DEFAULT_TITLE
        defaultTaskShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the taskList where title does not contain UPDATED_TITLE
        defaultTaskShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllTasksByHasvariantsIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where hasvariants equals to DEFAULT_HASVARIANTS
        defaultTaskShouldBeFound("hasvariants.equals=" + DEFAULT_HASVARIANTS);

        // Get all the taskList where hasvariants equals to UPDATED_HASVARIANTS
        defaultTaskShouldNotBeFound("hasvariants.equals=" + UPDATED_HASVARIANTS);
    }

    @Test
    @Transactional
    public void getAllTasksByHasvariantsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where hasvariants not equals to DEFAULT_HASVARIANTS
        defaultTaskShouldNotBeFound("hasvariants.notEquals=" + DEFAULT_HASVARIANTS);

        // Get all the taskList where hasvariants not equals to UPDATED_HASVARIANTS
        defaultTaskShouldBeFound("hasvariants.notEquals=" + UPDATED_HASVARIANTS);
    }

    @Test
    @Transactional
    public void getAllTasksByHasvariantsIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where hasvariants in DEFAULT_HASVARIANTS or UPDATED_HASVARIANTS
        defaultTaskShouldBeFound("hasvariants.in=" + DEFAULT_HASVARIANTS + "," + UPDATED_HASVARIANTS);

        // Get all the taskList where hasvariants equals to UPDATED_HASVARIANTS
        defaultTaskShouldNotBeFound("hasvariants.in=" + UPDATED_HASVARIANTS);
    }

    @Test
    @Transactional
    public void getAllTasksByHasvariantsIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where hasvariants is not null
        defaultTaskShouldBeFound("hasvariants.specified=true");

        // Get all the taskList where hasvariants is null
        defaultTaskShouldNotBeFound("hasvariants.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByModifiedatIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where modifiedat equals to DEFAULT_MODIFIEDAT
        defaultTaskShouldBeFound("modifiedat.equals=" + DEFAULT_MODIFIEDAT);

        // Get all the taskList where modifiedat equals to UPDATED_MODIFIEDAT
        defaultTaskShouldNotBeFound("modifiedat.equals=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllTasksByModifiedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where modifiedat not equals to DEFAULT_MODIFIEDAT
        defaultTaskShouldNotBeFound("modifiedat.notEquals=" + DEFAULT_MODIFIEDAT);

        // Get all the taskList where modifiedat not equals to UPDATED_MODIFIEDAT
        defaultTaskShouldBeFound("modifiedat.notEquals=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllTasksByModifiedatIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where modifiedat in DEFAULT_MODIFIEDAT or UPDATED_MODIFIEDAT
        defaultTaskShouldBeFound("modifiedat.in=" + DEFAULT_MODIFIEDAT + "," + UPDATED_MODIFIEDAT);

        // Get all the taskList where modifiedat equals to UPDATED_MODIFIEDAT
        defaultTaskShouldNotBeFound("modifiedat.in=" + UPDATED_MODIFIEDAT);
    }

    @Test
    @Transactional
    public void getAllTasksByModifiedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where modifiedat is not null
        defaultTaskShouldBeFound("modifiedat.specified=true");

        // Get all the taskList where modifiedat is null
        defaultTaskShouldNotBeFound("modifiedat.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where deletedat equals to DEFAULT_DELETEDAT
        defaultTaskShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the taskList where deletedat equals to UPDATED_DELETEDAT
        defaultTaskShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllTasksByDeletedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where deletedat not equals to DEFAULT_DELETEDAT
        defaultTaskShouldNotBeFound("deletedat.notEquals=" + DEFAULT_DELETEDAT);

        // Get all the taskList where deletedat not equals to UPDATED_DELETEDAT
        defaultTaskShouldBeFound("deletedat.notEquals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllTasksByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultTaskShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the taskList where deletedat equals to UPDATED_DELETEDAT
        defaultTaskShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    public void getAllTasksByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where deletedat is not null
        defaultTaskShouldBeFound("deletedat.specified=true");

        // Get all the taskList where deletedat is null
        defaultTaskShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdat equals to DEFAULT_CREATEDAT
        defaultTaskShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the taskList where createdat equals to UPDATED_CREATEDAT
        defaultTaskShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdat not equals to DEFAULT_CREATEDAT
        defaultTaskShouldNotBeFound("createdat.notEquals=" + DEFAULT_CREATEDAT);

        // Get all the taskList where createdat not equals to UPDATED_CREATEDAT
        defaultTaskShouldBeFound("createdat.notEquals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultTaskShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the taskList where createdat equals to UPDATED_CREATEDAT
        defaultTaskShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdat is not null
        defaultTaskShouldBeFound("createdat.specified=true");

        // Get all the taskList where createdat is null
        defaultTaskShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = task.getUser();
        taskRepository.saveAndFlush(task);
        Long userId = user.getId();

        // Get all the taskList where user equals to userId
        defaultTaskShouldBeFound("userId.equals=" + userId);

        // Get all the taskList where user equals to userId + 1
        defaultTaskShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTaskShouldBeFound(String filter) throws Exception {
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].hasvariants").value(hasItem(DEFAULT_HASVARIANTS.booleanValue())))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())));

        // Check, that the count call also returns 1
        restTaskMockMvc.perform(get("/api/tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTaskShouldNotBeFound(String filter) throws Exception {
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaskMockMvc.perform(get("/api/tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).get();
        // Disconnect from session so that the updates on updatedTask are not directly saved in db
        em.detach(updatedTask);
        updatedTask
            .title(UPDATED_TITLE)
            .hasvariants(UPDATED_HASVARIANTS)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .createdat(UPDATED_CREATEDAT);
        TaskDTO taskDTO = taskMapper.toDto(updatedTask);

        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTask.isHasvariants()).isEqualTo(UPDATED_HASVARIANTS);
        assertThat(testTask.getModifiedat()).isEqualTo(UPDATED_MODIFIEDAT);
        assertThat(testTask.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
        assertThat(testTask.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    public void updateNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Delete the task
        restTaskMockMvc.perform(delete("/api/tasks/{id}", task.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
