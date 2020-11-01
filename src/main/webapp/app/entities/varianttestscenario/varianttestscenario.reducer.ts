import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVarianttestscenario, defaultValue } from 'app/shared/model/varianttestscenario.model';

export const ACTION_TYPES = {
  FETCH_VARIANTTESTSCENARIO_LIST: 'varianttestscenario/FETCH_VARIANTTESTSCENARIO_LIST',
  FETCH_VARIANTTESTSCENARIO: 'varianttestscenario/FETCH_VARIANTTESTSCENARIO',
  CREATE_VARIANTTESTSCENARIO: 'varianttestscenario/CREATE_VARIANTTESTSCENARIO',
  UPDATE_VARIANTTESTSCENARIO: 'varianttestscenario/UPDATE_VARIANTTESTSCENARIO',
  DELETE_VARIANTTESTSCENARIO: 'varianttestscenario/DELETE_VARIANTTESTSCENARIO',
  RESET: 'varianttestscenario/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVarianttestscenario>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type VarianttestscenarioState = Readonly<typeof initialState>;

// Reducer

export default (state: VarianttestscenarioState = initialState, action): VarianttestscenarioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VARIANTTESTSCENARIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VARIANTTESTSCENARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VARIANTTESTSCENARIO):
    case REQUEST(ACTION_TYPES.UPDATE_VARIANTTESTSCENARIO):
    case REQUEST(ACTION_TYPES.DELETE_VARIANTTESTSCENARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_VARIANTTESTSCENARIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VARIANTTESTSCENARIO):
    case FAILURE(ACTION_TYPES.CREATE_VARIANTTESTSCENARIO):
    case FAILURE(ACTION_TYPES.UPDATE_VARIANTTESTSCENARIO):
    case FAILURE(ACTION_TYPES.DELETE_VARIANTTESTSCENARIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VARIANTTESTSCENARIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_VARIANTTESTSCENARIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VARIANTTESTSCENARIO):
    case SUCCESS(ACTION_TYPES.UPDATE_VARIANTTESTSCENARIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VARIANTTESTSCENARIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/varianttestscenarios';

// Actions

export const getEntities: ICrudGetAllAction<IVarianttestscenario> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VARIANTTESTSCENARIO_LIST,
    payload: axios.get<IVarianttestscenario>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IVarianttestscenario> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VARIANTTESTSCENARIO,
    payload: axios.get<IVarianttestscenario>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVarianttestscenario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VARIANTTESTSCENARIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVarianttestscenario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VARIANTTESTSCENARIO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVarianttestscenario> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VARIANTTESTSCENARIO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
