import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './scenarioexpectedoutput.reducer';
import { IScenarioexpectedoutput } from 'app/shared/model/scenarioexpectedoutput.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IScenarioexpectedoutputDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ScenarioexpectedoutputDetail = (props: IScenarioexpectedoutputDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { scenarioexpectedoutputEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="rtuHomeworkCheckerApp.scenarioexpectedoutput.detail.title">Scenarioexpectedoutput</Translate> [
          <b>{scenarioexpectedoutputEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="outputline">
              <Translate contentKey="rtuHomeworkCheckerApp.scenarioexpectedoutput.outputline">Outputline</Translate>
            </span>
          </dt>
          <dd>{scenarioexpectedoutputEntity.outputline}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.scenarioexpectedoutput.varianttestscenario">Varianttestscenario</Translate>
          </dt>
          <dd>{scenarioexpectedoutputEntity.varianttestscenarioId ? scenarioexpectedoutputEntity.varianttestscenarioId : ''}</dd>
        </dl>
        <Button tag={Link} to="/scenarioexpectedoutput" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scenarioexpectedoutput/${scenarioexpectedoutputEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ scenarioexpectedoutput }: IRootState) => ({
  scenarioexpectedoutputEntity: scenarioexpectedoutput.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ScenarioexpectedoutputDetail);
