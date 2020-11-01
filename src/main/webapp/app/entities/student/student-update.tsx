import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './student.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStudentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StudentUpdate = (props: IStudentUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { studentEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/student' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...studentEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rtuHomeworkCheckerApp.student.home.createOrEditLabel">
            <Translate contentKey="rtuHomeworkCheckerApp.student.home.createOrEditLabel">Create or edit a Student</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : studentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="student-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="student-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="studentcodeLabel" for="student-studentcode">
                  <Translate contentKey="rtuHomeworkCheckerApp.student.studentcode">Studentcode</Translate>
                </Label>
                <AvField
                  id="student-studentcode"
                  type="text"
                  name="studentcode"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="student-name">
                  <Translate contentKey="rtuHomeworkCheckerApp.student.name">Name</Translate>
                </Label>
                <AvField
                  id="student-name"
                  type="text"
                  name="name"
                  validate={{
                    maxLength: { value: 64, errorMessage: translate('entity.validation.maxlength', { max: 64 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="surnameLabel" for="student-surname">
                  <Translate contentKey="rtuHomeworkCheckerApp.student.surname">Surname</Translate>
                </Label>
                <AvField
                  id="student-surname"
                  type="text"
                  name="surname"
                  validate={{
                    maxLength: { value: 64, errorMessage: translate('entity.validation.maxlength', { max: 64 }) },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/student" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  studentEntity: storeState.student.entity,
  loading: storeState.student.loading,
  updating: storeState.student.updating,
  updateSuccess: storeState.student.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StudentUpdate);
