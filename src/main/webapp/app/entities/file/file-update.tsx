import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITask } from 'app/shared/model/task.model';
import { getEntities as getTasks } from 'app/entities/task/task.reducer';
import { IVariant } from 'app/shared/model/variant.model';
import { getEntities as getVariants } from 'app/entities/variant/variant.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { getEntity, updateEntity, createEntity, reset } from './file.reducer';
import { IFile } from 'app/shared/model/file.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FileUpdate = (props: IFileUpdateProps) => {
  const [taskId, setTaskId] = useState('0');
  const [variantId, setVariantId] = useState('0');
  const [studentId, setStudentId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { fileEntity, tasks, variants, students, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/file' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTasks();
    props.getVariants();
    props.getStudents();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.modifiedat = convertDateTimeToServer(values.modifiedat);
    values.deletedat = convertDateTimeToServer(values.deletedat);
    values.createdat = convertDateTimeToServer(values.createdat);

    if (errors.length === 0) {
      const entity = {
        ...fileEntity,
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
          <h2 id="rtuHomeworkCheckerApp.file.home.createOrEditLabel">
            <Translate contentKey="rtuHomeworkCheckerApp.file.home.createOrEditLabel">Create or edit a File</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : fileEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="file-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="file-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="modifiedatLabel" for="file-modifiedat">
                  <Translate contentKey="rtuHomeworkCheckerApp.file.modifiedat">Modifiedat</Translate>
                </Label>
                <AvInput
                  id="file-modifiedat"
                  type="datetime-local"
                  className="form-control"
                  name="modifiedat"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.fileEntity.modifiedat)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="deletedatLabel" for="file-deletedat">
                  <Translate contentKey="rtuHomeworkCheckerApp.file.deletedat">Deletedat</Translate>
                </Label>
                <AvInput
                  id="file-deletedat"
                  type="datetime-local"
                  className="form-control"
                  name="deletedat"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.fileEntity.deletedat)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdatLabel" for="file-createdat">
                  <Translate contentKey="rtuHomeworkCheckerApp.file.createdat">Createdat</Translate>
                </Label>
                <AvInput
                  id="file-createdat"
                  type="datetime-local"
                  className="form-control"
                  name="createdat"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.fileEntity.createdat)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="filenameLabel" for="file-filename">
                  <Translate contentKey="rtuHomeworkCheckerApp.file.filename">Filename</Translate>
                </Label>
                <AvField
                  id="file-filename"
                  type="text"
                  name="filename"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 128, errorMessage: translate('entity.validation.maxlength', { max: 128 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="file-task">
                  <Translate contentKey="rtuHomeworkCheckerApp.file.task">Task</Translate>
                </Label>
                <AvInput id="file-task" type="select" className="form-control" name="taskId" required>
                  {tasks
                    ? tasks.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="file-variant">
                  <Translate contentKey="rtuHomeworkCheckerApp.file.variant">Variant</Translate>
                </Label>
                <AvInput id="file-variant" type="select" className="form-control" name="variantId" required>
                  {variants
                    ? variants.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="file-student">
                  <Translate contentKey="rtuHomeworkCheckerApp.file.student">Student</Translate>
                </Label>
                <AvInput id="file-student" type="select" className="form-control" name="studentId" required>
                  {students
                    ? students.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/file" replace color="info">
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
  tasks: storeState.task.entities,
  variants: storeState.variant.entities,
  students: storeState.student.entities,
  fileEntity: storeState.file.entity,
  loading: storeState.file.loading,
  updating: storeState.file.updating,
  updateSuccess: storeState.file.updateSuccess,
});

const mapDispatchToProps = {
  getTasks,
  getVariants,
  getStudents,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FileUpdate);
