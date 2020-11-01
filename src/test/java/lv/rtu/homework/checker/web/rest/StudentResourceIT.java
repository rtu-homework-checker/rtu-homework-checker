package lv.rtu.homework.checker.web.rest;

import lv.rtu.homework.checker.RtuHomeworkCheckerApp;
import lv.rtu.homework.checker.domain.Student;
import lv.rtu.homework.checker.repository.StudentRepository;
import lv.rtu.homework.checker.service.StudentService;
import lv.rtu.homework.checker.service.dto.StudentDTO;
import lv.rtu.homework.checker.service.mapper.StudentMapper;
import lv.rtu.homework.checker.service.dto.StudentCriteria;
import lv.rtu.homework.checker.service.StudentQueryService;

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
 * Integration tests for the {@link StudentResource} REST controller.
 */
@SpringBootTest(classes = RtuHomeworkCheckerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StudentResourceIT {

    private static final String DEFAULT_STUDENTCODE = "AAAAAAAAAA";
    private static final String UPDATED_STUDENTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentQueryService studentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentMockMvc;

    private Student student;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .studentcode(DEFAULT_STUDENTCODE)
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME);
        return student;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student()
            .studentcode(UPDATED_STUDENTCODE)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();
        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);
        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentcode()).isEqualTo(DEFAULT_STUDENTCODE);
        assertThat(testStudent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStudent.getSurname()).isEqualTo(DEFAULT_SURNAME);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);
        StudentDTO studentDTO = studentMapper.toDto(student);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStudentcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setStudentcode(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);


        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentcode").value(hasItem(DEFAULT_STUDENTCODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)));
    }
    
    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.studentcode").value(DEFAULT_STUDENTCODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME));
    }


    @Test
    @Transactional
    public void getStudentsByIdFiltering() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        Long id = student.getId();

        defaultStudentShouldBeFound("id.equals=" + id);
        defaultStudentShouldNotBeFound("id.notEquals=" + id);

        defaultStudentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentcode equals to DEFAULT_STUDENTCODE
        defaultStudentShouldBeFound("studentcode.equals=" + DEFAULT_STUDENTCODE);

        // Get all the studentList where studentcode equals to UPDATED_STUDENTCODE
        defaultStudentShouldNotBeFound("studentcode.equals=" + UPDATED_STUDENTCODE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentcode not equals to DEFAULT_STUDENTCODE
        defaultStudentShouldNotBeFound("studentcode.notEquals=" + DEFAULT_STUDENTCODE);

        // Get all the studentList where studentcode not equals to UPDATED_STUDENTCODE
        defaultStudentShouldBeFound("studentcode.notEquals=" + UPDATED_STUDENTCODE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentcodeIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentcode in DEFAULT_STUDENTCODE or UPDATED_STUDENTCODE
        defaultStudentShouldBeFound("studentcode.in=" + DEFAULT_STUDENTCODE + "," + UPDATED_STUDENTCODE);

        // Get all the studentList where studentcode equals to UPDATED_STUDENTCODE
        defaultStudentShouldNotBeFound("studentcode.in=" + UPDATED_STUDENTCODE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentcode is not null
        defaultStudentShouldBeFound("studentcode.specified=true");

        // Get all the studentList where studentcode is null
        defaultStudentShouldNotBeFound("studentcode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByStudentcodeContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentcode contains DEFAULT_STUDENTCODE
        defaultStudentShouldBeFound("studentcode.contains=" + DEFAULT_STUDENTCODE);

        // Get all the studentList where studentcode contains UPDATED_STUDENTCODE
        defaultStudentShouldNotBeFound("studentcode.contains=" + UPDATED_STUDENTCODE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentcodeNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentcode does not contain DEFAULT_STUDENTCODE
        defaultStudentShouldNotBeFound("studentcode.doesNotContain=" + DEFAULT_STUDENTCODE);

        // Get all the studentList where studentcode does not contain UPDATED_STUDENTCODE
        defaultStudentShouldBeFound("studentcode.doesNotContain=" + UPDATED_STUDENTCODE);
    }


    @Test
    @Transactional
    public void getAllStudentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where name equals to DEFAULT_NAME
        defaultStudentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the studentList where name equals to UPDATED_NAME
        defaultStudentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where name not equals to DEFAULT_NAME
        defaultStudentShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the studentList where name not equals to UPDATED_NAME
        defaultStudentShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStudentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the studentList where name equals to UPDATED_NAME
        defaultStudentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where name is not null
        defaultStudentShouldBeFound("name.specified=true");

        // Get all the studentList where name is null
        defaultStudentShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByNameContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where name contains DEFAULT_NAME
        defaultStudentShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the studentList where name contains UPDATED_NAME
        defaultStudentShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where name does not contain DEFAULT_NAME
        defaultStudentShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the studentList where name does not contain UPDATED_NAME
        defaultStudentShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStudentsBySurnameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where surname equals to DEFAULT_SURNAME
        defaultStudentShouldBeFound("surname.equals=" + DEFAULT_SURNAME);

        // Get all the studentList where surname equals to UPDATED_SURNAME
        defaultStudentShouldNotBeFound("surname.equals=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllStudentsBySurnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where surname not equals to DEFAULT_SURNAME
        defaultStudentShouldNotBeFound("surname.notEquals=" + DEFAULT_SURNAME);

        // Get all the studentList where surname not equals to UPDATED_SURNAME
        defaultStudentShouldBeFound("surname.notEquals=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllStudentsBySurnameIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where surname in DEFAULT_SURNAME or UPDATED_SURNAME
        defaultStudentShouldBeFound("surname.in=" + DEFAULT_SURNAME + "," + UPDATED_SURNAME);

        // Get all the studentList where surname equals to UPDATED_SURNAME
        defaultStudentShouldNotBeFound("surname.in=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllStudentsBySurnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where surname is not null
        defaultStudentShouldBeFound("surname.specified=true");

        // Get all the studentList where surname is null
        defaultStudentShouldNotBeFound("surname.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsBySurnameContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where surname contains DEFAULT_SURNAME
        defaultStudentShouldBeFound("surname.contains=" + DEFAULT_SURNAME);

        // Get all the studentList where surname contains UPDATED_SURNAME
        defaultStudentShouldNotBeFound("surname.contains=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllStudentsBySurnameNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where surname does not contain DEFAULT_SURNAME
        defaultStudentShouldNotBeFound("surname.doesNotContain=" + DEFAULT_SURNAME);

        // Get all the studentList where surname does not contain UPDATED_SURNAME
        defaultStudentShouldBeFound("surname.doesNotContain=" + UPDATED_SURNAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentShouldBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentcode").value(hasItem(DEFAULT_STUDENTCODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)));

        // Check, that the count call also returns 1
        restStudentMockMvc.perform(get("/api/students/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentShouldNotBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentMockMvc.perform(get("/api/students/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .studentcode(UPDATED_STUDENTCODE)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME);
        StudentDTO studentDTO = studentMapper.toDto(updatedStudent);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentcode()).isEqualTo(UPDATED_STUDENTCODE);
        assertThat(testStudent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudent.getSurname()).isEqualTo(UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
