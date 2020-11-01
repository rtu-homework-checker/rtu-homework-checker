import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import file, {
  FileState
} from 'app/entities/file/file.reducer';
// prettier-ignore
import scenarioexpectedoutput, {
  ScenarioexpectedoutputState
} from 'app/entities/scenarioexpectedoutput/scenarioexpectedoutput.reducer';
// prettier-ignore
import scenarioscore, {
  ScenarioscoreState
} from 'app/entities/scenarioscore/scenarioscore.reducer';
// prettier-ignore
import score, {
  ScoreState
} from 'app/entities/score/score.reducer';
// prettier-ignore
import student, {
  StudentState
} from 'app/entities/student/student.reducer';
// prettier-ignore
import task, {
  TaskState
} from 'app/entities/task/task.reducer';
// prettier-ignore
import variant, {
  VariantState
} from 'app/entities/variant/variant.reducer';
// prettier-ignore
import varianttestscenario, {
  VarianttestscenarioState
} from 'app/entities/varianttestscenario/varianttestscenario.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly file: FileState;
  readonly scenarioexpectedoutput: ScenarioexpectedoutputState;
  readonly scenarioscore: ScenarioscoreState;
  readonly score: ScoreState;
  readonly student: StudentState;
  readonly task: TaskState;
  readonly variant: VariantState;
  readonly varianttestscenario: VarianttestscenarioState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  file,
  scenarioexpectedoutput,
  scenarioscore,
  score,
  student,
  task,
  variant,
  varianttestscenario,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
