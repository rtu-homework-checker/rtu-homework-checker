import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IScore } from 'app/shared/model/score.model';
import { getEntities as getScores } from 'app/entities/score/score.reducer';
import { IScenarioexpectedoutput } from 'app/shared/model/scenarioexpectedoutput.model';
import { getEntities as getScenarioexpectedoutputs } from 'app/entities/scenarioexpectedoutput/scenarioexpectedoutput.reducer';
import { getEntity, updateEntity, createEntity, reset } from './scenarioscore.reducer';
import { IScenarioscore } from 'app/shared/model/scenarioscore.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IScenarioscoreUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ScenarioscoreUpdate = (props: IScenarioscoreUpdateProps) => {
  const [scoreId, setScoreId] = useState('0');
  const [scenarioexpectedoutputId, setScenarioexpectedoutputId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { scenarioscoreEntity, scores, scenarioexpectedoutputs, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/scenarioscore' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getScores();
    props.getScenarioexpectedoutputs();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...scenarioscoreEntity,
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
          <h2 id="rtuHomeworkCheckerApp.scenarioscore.home.createOrEditLabel">
            <Translate contentKey="rtuHomeworkCheckerApp.scenarioscore.home.createOrEditLabel">Create or edit a Scenarioscore</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : scenarioscoreEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="scenarioscore-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="scenarioscore-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup check>
                <Label id="passedLabel">
                  <AvInput id="scenarioscore-passed" type="checkbox" className="form-check-input" name="passed" />
                  <Translate contentKey="rtuHomeworkCheckerApp.scenarioscore.passed">Passed</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="scenarioscore-score">
                  <Translate contentKey="rtuHomeworkCheckerApp.scenarioscore.score">Score</Translate>
                </Label>
                <AvInput id="scenarioscore-score" type="select" className="form-control" name="scoreId" required>
                  {scores
                    ? scores.map(otherEntity => (
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
                <Label for="scenarioscore-scenarioexpectedoutput">
                  <Translate contentKey="rtuHomeworkCheckerApp.scenarioscore.scenarioexpectedoutput">Scenarioexpectedoutput</Translate>
                </Label>
                <AvInput
                  id="scenarioscore-scenarioexpectedoutput"
                  type="select"
                  className="form-control"
                  name="scenarioexpectedoutputId"
                  required
                >
                  {scenarioexpectedoutputs
                    ? scenarioexpectedoutputs.map(otherEntity => (
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
              <Button tag={Link} id="cancel-save" to="/scenarioscore" replace color="info">
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
  scores: storeState.score.entities,
  scenarioexpectedoutputs: storeState.scenarioexpectedoutput.entities,
  scenarioscoreEntity: storeState.scenarioscore.entity,
  loading: storeState.scenarioscore.loading,
  updating: storeState.scenarioscore.updating,
  updateSuccess: storeState.scenarioscore.updateSuccess,
});

const mapDispatchToProps = {
  getScores,
  getScenarioexpectedoutputs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ScenarioscoreUpdate);
