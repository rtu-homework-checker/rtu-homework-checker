import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFile } from 'app/shared/model/file.model';
import { getEntities as getFiles } from 'app/entities/file/file.reducer';
import { IVariant } from 'app/shared/model/variant.model';
import { getEntities as getVariants } from 'app/entities/variant/variant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './score.reducer';
import { IScore } from 'app/shared/model/score.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IScoreUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ScoreUpdate = (props: IScoreUpdateProps) => {
  const [fileId, setFileId] = useState('0');
  const [variantId, setVariantId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { scoreEntity, files, variants, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/score' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFiles();
    props.getVariants();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdat = convertDateTimeToServer(values.createdat);
    values.modifiedat = convertDateTimeToServer(values.modifiedat);
    values.deletedat = convertDateTimeToServer(values.deletedat);

    if (errors.length === 0) {
      const entity = {
        ...scoreEntity,
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
          <h2 id="rtuHomeworkCheckerApp.score.home.createOrEditLabel">
            <Translate contentKey="rtuHomeworkCheckerApp.score.home.createOrEditLabel">Create or edit a Score</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : scoreEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="score-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="score-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup check>
                <Label id="ispassedLabel">
                  <AvInput id="score-ispassed" type="checkbox" className="form-check-input" name="ispassed" />
                  <Translate contentKey="rtuHomeworkCheckerApp.score.ispassed">Ispassed</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createdatLabel" for="score-createdat">
                  <Translate contentKey="rtuHomeworkCheckerApp.score.createdat">Createdat</Translate>
                </Label>
                <AvInput
                  id="score-createdat"
                  type="datetime-local"
                  className="form-control"
                  name="createdat"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.scoreEntity.createdat)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="modifiedatLabel" for="score-modifiedat">
                  <Translate contentKey="rtuHomeworkCheckerApp.score.modifiedat">Modifiedat</Translate>
                </Label>
                <AvInput
                  id="score-modifiedat"
                  type="datetime-local"
                  className="form-control"
                  name="modifiedat"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.scoreEntity.modifiedat)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="deletedatLabel" for="score-deletedat">
                  <Translate contentKey="rtuHomeworkCheckerApp.score.deletedat">Deletedat</Translate>
                </Label>
                <AvInput
                  id="score-deletedat"
                  type="datetime-local"
                  className="form-control"
                  name="deletedat"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.scoreEntity.deletedat)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="score-file">
                  <Translate contentKey="rtuHomeworkCheckerApp.score.file">File</Translate>
                </Label>
                <AvInput id="score-file" type="select" className="form-control" name="fileId" required>
                  {files
                    ? files.map(otherEntity => (
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
                <Label for="score-variant">
                  <Translate contentKey="rtuHomeworkCheckerApp.score.variant">Variant</Translate>
                </Label>
                <AvInput id="score-variant" type="select" className="form-control" name="variantId" required>
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
              <Button tag={Link} id="cancel-save" to="/score" replace color="info">
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
  files: storeState.file.entities,
  variants: storeState.variant.entities,
  scoreEntity: storeState.score.entity,
  loading: storeState.score.loading,
  updating: storeState.score.updating,
  updateSuccess: storeState.score.updateSuccess,
});

const mapDispatchToProps = {
  getFiles,
  getVariants,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ScoreUpdate);
