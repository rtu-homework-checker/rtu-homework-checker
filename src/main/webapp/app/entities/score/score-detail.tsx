import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './score.reducer';
import { IScore } from 'app/shared/model/score.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IScoreDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ScoreDetail = (props: IScoreDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { scoreEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="rtuHomeworkCheckerApp.score.detail.title">Score</Translate> [<b>{scoreEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="ispassed">
              <Translate contentKey="rtuHomeworkCheckerApp.score.ispassed">Ispassed</Translate>
            </span>
          </dt>
          <dd>{scoreEntity.ispassed ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">
              <Translate contentKey="rtuHomeworkCheckerApp.score.createdat">Createdat</Translate>
            </span>
          </dt>
          <dd>{scoreEntity.createdat ? <TextFormat value={scoreEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="modifiedat">
              <Translate contentKey="rtuHomeworkCheckerApp.score.modifiedat">Modifiedat</Translate>
            </span>
          </dt>
          <dd>{scoreEntity.modifiedat ? <TextFormat value={scoreEntity.modifiedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedat">
              <Translate contentKey="rtuHomeworkCheckerApp.score.deletedat">Deletedat</Translate>
            </span>
          </dt>
          <dd>{scoreEntity.deletedat ? <TextFormat value={scoreEntity.deletedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.score.file">File</Translate>
          </dt>
          <dd>{scoreEntity.fileId ? scoreEntity.fileId : ''}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.score.variant">Variant</Translate>
          </dt>
          <dd>{scoreEntity.variantId ? scoreEntity.variantId : ''}</dd>
        </dl>
        <Button tag={Link} to="/score" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/score/${scoreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ score }: IRootState) => ({
  scoreEntity: score.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ScoreDetail);
