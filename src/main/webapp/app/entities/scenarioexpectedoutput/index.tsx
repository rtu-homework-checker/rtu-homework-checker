import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Scenarioexpectedoutput from './scenarioexpectedoutput';
import ScenarioexpectedoutputDetail from './scenarioexpectedoutput-detail';
import ScenarioexpectedoutputUpdate from './scenarioexpectedoutput-update';
import ScenarioexpectedoutputDeleteDialog from './scenarioexpectedoutput-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ScenarioexpectedoutputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ScenarioexpectedoutputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ScenarioexpectedoutputDetail} />
      <ErrorBoundaryRoute path={match.url} component={Scenarioexpectedoutput} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ScenarioexpectedoutputDeleteDialog} />
  </>
);

export default Routes;
