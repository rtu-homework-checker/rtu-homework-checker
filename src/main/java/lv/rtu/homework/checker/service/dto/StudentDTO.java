package lv.rtu.homework.checker.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link lv.rtu.homework.checker.domain.Student} entity.
 */
public class StudentDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 10)
    private String studentcode;

    @Size(max = 64)
    private String name;

    @Size(max = 64)
    private String surname;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentcode() {
        return studentcode;
    }

    public void setStudentcode(String studentcode) {
        this.studentcode = studentcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentDTO)) {
            return false;
        }

        return id != null && id.equals(((StudentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", studentcode='" + getStudentcode() + "'" +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            "}";
    }
}
