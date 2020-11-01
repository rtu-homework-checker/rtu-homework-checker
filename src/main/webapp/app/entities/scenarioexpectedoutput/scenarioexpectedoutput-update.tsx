import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVarianttestscenario } from 'app/shared/model/varianttestscenario.model';
import { getEntities as getVarianttestscenarios } from 'app/entities/varianttestscenario/varianttestscenario.reducer';
import { getEntity, updateEntity, createEntity, reset } from './scenarioexpectedoutput.reducer';
import { IScenarioexpectedoutput } from 'app/shared/model/scenarioexpectedoutput.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IScenarioexpectedoutputUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ScenarioexpectedoutputUpdate = (props: IScenarioexpectedoutputUpdateProps) => {
  const [varianttestscenarioId, setVarianttestscenarioId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { scenarioexpectedoutputEntity, varianttestscenarios, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/scenarioexpectedoutput' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getVarianttestscenarios();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...scenarioexpectedoutputEntity,
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
          <h2 id="rtuHomeworkCheckerApp.scenarioexpectedoutput.home.createOrEditLabel">
            <Translate contentKey="rtuHomeworkCheckerApp.scenarioexpectedoutput.home.createOrEditLabel">
              Create or edit a Scenarioexpectedoutput
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : scenarioexpectedoutputEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="scenarioexpectedoutput-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="scenarioexpectedoutput-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="outputlineLabel" for="scenarioexpectedoutput-outputline">
                  <Translate contentKey="rtuHomeworkCheckerApp.scenarioexpectedoutput.outputline">Outputline</Translate>
                </Label>
                <AvField
                  id="scenarioexpectedoutput-outputline"
                  type="text"
                  name="outputline"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 1024, errorMessage: translate('entity.validation.maxlength', { max: 1024 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="scenarioexpectedoutput-varianttestscenario">
                  <Translate contentKey="rtuHomeworkCheckerApp.scenarioexpectedoutput.varianttestscenario">Varianttestscenario</Translate>
                </Label>
                <AvInput
                  id="scenarioexpectedoutput-varianttestscenario"
                  type="select"
                  className="form-control"
                  name="varianttestscenarioId"
                >
                  <option value="" key="0" />
                  {varianttestscenarios
                    ? varianttestscenarios.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/scenarioexpectedoutput" replace color="info">
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
  varianttestscenarios: storeState.varianttestscenario.entities,
  scenarioexpectedoutputEntity: storeState.scenarioexpectedoutput.entity,
  loading: storeState.scenarioexpectedoutput.loading,
  updating: storeState.scenarioexpectedoutput.updating,
  updateSuccess: storeState.scenarioexpectedoutput.updateSuccess,
});

const mapDispatchToProps = {
  getVarianttestscenarios,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ScenarioexpectedoutputUpdate);
