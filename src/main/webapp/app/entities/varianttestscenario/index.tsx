import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Varianttestscenario from './varianttestscenario';
import VarianttestscenarioDetail from './varianttestscenario-detail';
import VarianttestscenarioUpdate from './varianttestscenario-update';
import VarianttestscenarioDeleteDialog from './varianttestscenario-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VarianttestscenarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VarianttestscenarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VarianttestscenarioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Varianttestscenario} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VarianttestscenarioDeleteDialog} />
  </>
);

export default Routes;
