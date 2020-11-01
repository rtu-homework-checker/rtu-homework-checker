import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './file.reducer';
import { IFile } from 'app/shared/model/file.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FileDetail = (props: IFileDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { fileEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="rtuHomeworkCheckerApp.file.detail.title">File</Translate> [<b>{fileEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="modifiedat">
              <Translate contentKey="rtuHomeworkCheckerApp.file.modifiedat">Modifiedat</Translate>
            </span>
          </dt>
          <dd>{fileEntity.modifiedat ? <TextFormat value={fileEntity.modifiedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedat">
              <Translate contentKey="rtuHomeworkCheckerApp.file.deletedat">Deletedat</Translate>
            </span>
          </dt>
          <dd>{fileEntity.deletedat ? <TextFormat value={fileEntity.deletedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdat">
              <Translate contentKey="rtuHomeworkCheckerApp.file.createdat">Createdat</Translate>
            </span>
          </dt>
          <dd>{fileEntity.createdat ? <TextFormat value={fileEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="filename">
              <Translate contentKey="rtuHomeworkCheckerApp.file.filename">Filename</Translate>
            </span>
          </dt>
          <dd>{fileEntity.filename}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.file.task">Task</Translate>
          </dt>
          <dd>{fileEntity.taskId ? fileEntity.taskId : ''}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.file.variant">Variant</Translate>
          </dt>
          <dd>{fileEntity.variantId ? fileEntity.variantId : ''}</dd>
          <dt>
            <Translate contentKey="rtuHomeworkCheckerApp.file.student">Student</Translate>
          </dt>
          <dd>{fileEntity.studentId ? fileEntity.studentId : ''}</dd>
        </dl>
        <Button tag={Link} to="/file" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/file/${fileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ file }: IRootState) => ({
  fileEntity: file.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FileDetail);
