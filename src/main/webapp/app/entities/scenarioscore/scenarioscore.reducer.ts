import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IScenarioscore, defaultValue } from 'app/shared/model/scenarioscore.model';

export const ACTION_TYPES = {
  FETCH_SCENARIOSCORE_LIST: 'scenarioscore/FETCH_SCENARIOSCORE_LIST',
  FETCH_SCENARIOSCORE: 'scenarioscore/FETCH_SCENARIOSCORE',
  CREATE_SCENARIOSCORE: 'scenarioscore/CREATE_SCENARIOSCORE',
  UPDATE_SCENARIOSCORE: 'scenarioscore/UPDATE_SCENARIOSCORE',
  DELETE_SCENARIOSCORE: 'scenarioscore/DELETE_SCENARIOSCORE',
  RESET: 'scenarioscore/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IScenarioscore>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ScenarioscoreState = Readonly<typeof initialState>;

// Reducer

export default (state: ScenarioscoreState = initialState, action): ScenarioscoreState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SCENARIOSCORE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SCENARIOSCORE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SCENARIOSCORE):
    case REQUEST(ACTION_TYPES.UPDATE_SCENARIOSCORE):
    case REQUEST(ACTION_TYPES.DELETE_SCENARIOSCORE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SCENARIOSCORE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SCENARIOSCORE):
    case FAILURE(ACTION_TYPES.CREATE_SCENARIOSCORE):
    case FAILURE(ACTION_TYPES.UPDATE_SCENARIOSCORE):
    case FAILURE(ACTION_TYPES.DELETE_SCENARIOSCORE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SCENARIOSCORE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SCENARIOSCORE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SCENARIOSCORE):
    case SUCCESS(ACTION_TYPES.UPDATE_SCENARIOSCORE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SCENARIOSCORE):
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

const apiUrl = 'api/scenarioscores';

// Actions

export const getEntities: ICrudGetAllAction<IScenarioscore> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SCENARIOSCORE_LIST,
    payload: axios.get<IScenarioscore>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IScenarioscore> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SCENARIOSCORE,
    payload: axios.get<IScenarioscore>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IScenarioscore> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SCENARIOSCORE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IScenarioscore> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SCENARIOSCORE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IScenarioscore> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SCENARIOSCORE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
