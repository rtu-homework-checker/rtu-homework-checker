import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './variant.reducer';
import { IVariant } from 'app/shared/model/variant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVariantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VariantDetail = (props: IVariantDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { variantEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="rtuHomeworkCheckerApp.variant.detail.title">Variant</Translate> [<b>{variantEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="rtuHomeworkCheckerApp.variant.title">Title</Translate>
            </span>
          </dt>
          <dd>{variantEntity.title}</dd>
          <dt>
            <span id="modifiedat">
              <Translate contentKey="rtuHomeworkCheckerApp.variant.modifiedat">Modifiedat</Translate>
            </span>
          </dt>
          <dd>{variantEntity.modifiedat ? <TextFormat value={variantEntity.modifiedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedat">
              <Translate contentKey="rtuHomeworkCheckerApp.variant.deletedat">Deletedat</Translate>
            </span>
          </dt>
          <dd>{variantEntity.deletedat ? <TextFormat value={variantEntity.deletedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdat">
              <Translate contentKey="rtuHomeworkCheckerApp.variant.createdat">Createdat</Translate>
            </span>
          </dt>
          <dd>{variantEntity.createdat ? <TextFormat value={variantEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.variant.task">Task</Translate>
          </dt>
          <dd>{variantEntity.taskId ? variantEntity.taskId : ''}</dd>
        </dl>
        <Button tag={Link} to="/variant" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/variant/${variantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ variant }: IRootState) => ({
  variantEntity: variant.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VariantDetail);
