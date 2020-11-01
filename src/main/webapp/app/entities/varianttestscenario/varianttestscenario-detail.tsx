import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './varianttestscenario.reducer';
import { IVarianttestscenario } from 'app/shared/model/varianttestscenario.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVarianttestscenarioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VarianttestscenarioDetail = (props: IVarianttestscenarioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { varianttestscenarioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="rtuHomeworkCheckerApp.varianttestscenario.detail.title">Varianttestscenario</Translate> [
          <b>{varianttestscenarioEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="input">
              <Translate contentKey="rtuHomeworkCheckerApp.varianttestscenario.input">Input</Translate>
            </span>
          </dt>
          <dd>{varianttestscenarioEntity.input}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.varianttestscenario.variant">Variant</Translate>
          </dt>
          <dd>{varianttestscenarioEntity.variantId ? varianttestscenarioEntity.variantId : ''}</dd>
        </dl>
        <Button tag={Link} to="/varianttestscenario" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/varianttestscenario/${varianttestscenarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ varianttestscenario }: IRootState) => ({
  varianttestscenarioEntity: varianttestscenario.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VarianttestscenarioDetail);
