import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './scenarioscore.reducer';
import { IScenarioscore } from 'app/shared/model/scenarioscore.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IScenarioscoreDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ScenarioscoreDetail = (props: IScenarioscoreDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { scenarioscoreEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="rtuHomeworkCheckerApp.scenarioscore.detail.title">Scenarioscore</Translate> [
          <b>{scenarioscoreEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="passed">
              <Translate contentKey="rtuHomeworkCheckerApp.scenarioscore.passed">Passed</Translate>
            </span>
          </dt>
          <dd>{scenarioscoreEntity.passed ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.scenarioscore.score">Score</Translate>
          </dt>
          <dd>{scenarioscoreEntity.scoreId ? scenarioscoreEntity.scoreId : ''}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.scenarioscore.scenarioexpectedoutput">Scenarioexpectedoutput</Translate>
          </dt>
          <dd>{scenarioscoreEntity.scenarioexpectedoutputId ? scenarioscoreEntity.scenarioexpectedoutputId : ''}</dd>
        </dl>
        <Button tag={Link} to="/scenarioscore" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scenarioscore/${scenarioscoreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ scenarioscore }: IRootState) => ({
  scenarioscoreEntity: scenarioscore.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ScenarioscoreDetail);
