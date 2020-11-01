package lv.rtu.homework.checker.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "studentcode", length = 10, nullable = false)
    private String studentcode;

    @Size(max = 64)
    @Column(name = "name", length = 64)
    private String name;

    @Size(max = 64)
    @Column(name = "surname", length = 64)
    private String surname;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentcode() {
        return studentcode;
    }

    public Student studentcode(String studentcode) {
        this.studentcode = studentcode;
        return this;
    }

    public void setStudentcode(String studentcode) {
        this.studentcode = studentcode;
    }

    public String getName() {
        return name;
    }

    public Student name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Student surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentcode='" + getStudentcode() + "'" +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            "}";
    }
}
