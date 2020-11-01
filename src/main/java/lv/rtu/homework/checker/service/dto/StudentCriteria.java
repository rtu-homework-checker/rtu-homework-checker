package lv.rtu.homework.checker.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link lv.rtu.homework.checker.domain.Student} entity. This class is used
 * in {@link lv.rtu.homework.checker.web.rest.StudentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /students?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter studentcode;

    private StringFilter name;

    private StringFilter surname;

    public StudentCriteria() {
    }

    public StudentCriteria(StudentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.studentcode = other.studentcode == null ? null : other.studentcode.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.surname = other.surname == null ? null : other.surname.copy();
    }

    @Override
    public StudentCriteria copy() {
        return new StudentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStudentcode() {
        return studentcode;
    }

    public void setStudentcode(StringFilter studentcode) {
        this.studentcode = studentcode;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSurname() {
        return surname;
    }

    public void setSurname(StringFilter surname) {
        this.surname = surname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudentCriteria that = (StudentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(studentcode, that.studentcode) &&
            Objects.equals(name, that.name) &&
            Objects.equals(surname, that.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        studentcode,
        name,
        surname
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (studentcode != null ? "studentcode=" + studentcode + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (surname != null ? "surname=" + surname + ", " : "") +
            "}";
    }

}
