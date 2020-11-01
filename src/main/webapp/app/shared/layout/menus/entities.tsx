import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/file">
      <Translate contentKey="global.menu.entities.file" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/scenarioexpectedoutput">
      <Translate contentKey="global.menu.entities.scenarioexpectedoutput" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/scenarioscore">
      <Translate contentKey="global.menu.entities.scenarioscore" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/score">
      <Translate contentKey="global.menu.entities.score" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/student">
      <Translate contentKey="global.menu.entities.student" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/task">
      <Translate contentKey="global.menu.entities.task" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/variant">
      <Translate contentKey="global.menu.entities.variant" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/varianttestscenario">
      <Translate contentKey="global.menu.entities.varianttestscenario" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
