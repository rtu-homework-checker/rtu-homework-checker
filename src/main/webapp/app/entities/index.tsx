import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import File from './file';
import Scenarioexpectedoutput from './scenarioexpectedoutput';
import Scenarioscore from './scenarioscore';
import Score from './score';
import Student from './student';
import Task from './task';
import Variant from './variant';
import Varianttestscenario from './varianttestscenario';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}file`} component={File} />
      <ErrorBoundaryRoute path={`${match.url}scenarioexpectedoutput`} component={Scenarioexpectedoutput} />
      <ErrorBoundaryRoute path={`${match.url}scenarioscore`} component={Scenarioscore} />
      <ErrorBoundaryRoute path={`${match.url}score`} component={Score} />
      <ErrorBoundaryRoute path={`${match.url}student`} component={Student} />
      <ErrorBoundaryRoute path={`${match.url}task`} component={Task} />
      <ErrorBoundaryRoute path={`${match.url}variant`} component={Variant} />
      <ErrorBoundaryRoute path={`${match.url}varianttestscenario`} component={Varianttestscenario} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
