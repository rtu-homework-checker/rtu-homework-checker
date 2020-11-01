import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVariant } from 'app/shared/model/variant.model';
import { getEntities as getVariants } from 'app/entities/variant/variant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './varianttestscenario.reducer';
import { IVarianttestscenario } from 'app/shared/model/varianttestscenario.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVarianttestscenarioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VarianttestscenarioUpdate = (props: IVarianttestscenarioUpdateProps) => {
  const [variantId, setVariantId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { varianttestscenarioEntity, variants, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/varianttestscenario' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getVariants();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...varianttestscenarioEntity,
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
          <h2 id="rtuHomeworkCheckerApp.varianttestscenario.home.createOrEditLabel">
            <Translate contentKey="rtuHomeworkCheckerApp.varianttestscenario.home.createOrEditLabel">
              Create or edit a Varianttestscenario
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : varianttestscenarioEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="varianttestscenario-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="varianttestscenario-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="inputLabel" for="varianttestscenario-input">
                  <Translate contentKey="rtuHomeworkCheckerApp.varianttestscenario.input">Input</Translate>
                </Label>
                <AvField
                  id="varianttestscenario-input"
                  type="text"
                  name="input"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 1024, errorMessage: translate('entity.validation.maxlength', { max: 1024 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="varianttestscenario-variant">
                  <Translate contentKey="rtuHomeworkCheckerApp.varianttestscenario.variant">Variant</Translate>
                </Label>
                <AvInput id="varianttestscenario-variant" type="select" className="form-control" name="variantId" required>
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
              <Button tag={Link} id="cancel-save" to="/varianttestscenario" replace color="info">
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
  variants: storeState.variant.entities,
  varianttestscenarioEntity: storeState.varianttestscenario.entity,
  loading: storeState.varianttestscenario.loading,
  updating: storeState.varianttestscenario.updating,
  updateSuccess: storeState.varianttestscenario.updateSuccess,
});

const mapDispatchToProps = {
  getVariants,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VarianttestscenarioUpdate);
