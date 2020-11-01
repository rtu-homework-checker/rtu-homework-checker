import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Scenarioscore from './scenarioscore';
import ScenarioscoreDetail from './scenarioscore-detail';
import ScenarioscoreUpdate from './scenarioscore-update';
import ScenarioscoreDeleteDialog from './scenarioscore-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ScenarioscoreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ScenarioscoreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ScenarioscoreDetail} />
      <ErrorBoundaryRoute path={match.url} component={Scenarioscore} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ScenarioscoreDeleteDialog} />
  </>
);

export default Routes;
