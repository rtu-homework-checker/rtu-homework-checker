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
import { getEntity, updateEntity, createEntity, reset } from './variant.reducer';
import { IVariant } from 'app/shared/model/variant.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVariantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VariantUpdate = (props: IVariantUpdateProps) => {
  const [taskId, setTaskId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { variantEntity, tasks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/variant' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTasks();
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
        ...variantEntity,
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
          <h2 id="rtuHomeworkCheckerApp.variant.home.createOrEditLabel">
            <Translate contentKey="rtuHomeworkCheckerApp.variant.home.createOrEditLabel">Create or edit a Variant</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : variantEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="variant-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="variant-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="variant-title">
                  <Translate contentKey="rtuHomeworkCheckerApp.variant.title">Title</Translate>
                </Label>
                <AvField
                  id="variant-title"
                  type="text"
                  name="title"
                  validate={{
                    maxLength: { value: 128, errorMessage: translate('entity.validation.maxlength', { max: 128 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="modifiedatLabel" for="variant-modifiedat">
                  <Translate contentKey="rtuHomeworkCheckerApp.variant.modifiedat">Modifiedat</Translate>
                </Label>
                <AvInput
                  id="variant-modifiedat"
                  type="datetime-local"
                  className="form-control"
                  name="modifiedat"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.variantEntity.modifiedat)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="deletedatLabel" for="variant-deletedat">
                  <Translate contentKey="rtuHomeworkCheckerApp.variant.deletedat">Deletedat</Translate>
                </Label>
                <AvInput
                  id="variant-deletedat"
                  type="datetime-local"
                  className="form-control"
                  name="deletedat"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.variantEntity.deletedat)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdatLabel" for="variant-createdat">
                  <Translate contentKey="rtuHomeworkCheckerApp.variant.createdat">Createdat</Translate>
                </Label>
                <AvInput
                  id="variant-createdat"
                  type="datetime-local"
                  className="form-control"
                  name="createdat"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.variantEntity.createdat)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="variant-task">
                  <Translate contentKey="rtuHomeworkCheckerApp.variant.task">Task</Translate>
                </Label>
                <AvInput id="variant-task" type="select" className="form-control" name="taskId" required>
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
              <Button tag={Link} id="cancel-save" to="/variant" replace color="info">
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
  variantEntity: storeState.variant.entity,
  loading: storeState.variant.loading,
  updating: storeState.variant.updating,
  updateSuccess: storeState.variant.updateSuccess,
});

const mapDispatchToProps = {
  getTasks,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VariantUpdate);
