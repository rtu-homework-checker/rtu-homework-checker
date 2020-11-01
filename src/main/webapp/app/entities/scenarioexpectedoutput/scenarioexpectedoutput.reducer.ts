import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IScenarioexpectedoutput, defaultValue } from 'app/shared/model/scenarioexpectedoutput.model';

export const ACTION_TYPES = {
  FETCH_SCENARIOEXPECTEDOUTPUT_LIST: 'scenarioexpectedoutput/FETCH_SCENARIOEXPECTEDOUTPUT_LIST',
  FETCH_SCENARIOEXPECTEDOUTPUT: 'scenarioexpectedoutput/FETCH_SCENARIOEXPECTEDOUTPUT',
  CREATE_SCENARIOEXPECTEDOUTPUT: 'scenarioexpectedoutput/CREATE_SCENARIOEXPECTEDOUTPUT',
  UPDATE_SCENARIOEXPECTEDOUTPUT: 'scenarioexpectedoutput/UPDATE_SCENARIOEXPECTEDOUTPUT',
  DELETE_SCENARIOEXPECTEDOUTPUT: 'scenarioexpectedoutput/DELETE_SCENARIOEXPECTEDOUTPUT',
  RESET: 'scenarioexpectedoutput/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IScenarioexpectedoutput>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ScenarioexpectedoutputState = Readonly<typeof initialState>;

// Reducer

export default (state: ScenarioexpectedoutputState = initialState, action): ScenarioexpectedoutputState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SCENARIOEXPECTEDOUTPUT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SCENARIOEXPECTEDOUTPUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SCENARIOEXPECTEDOUTPUT):
    case REQUEST(ACTION_TYPES.UPDATE_SCENARIOEXPECTEDOUTPUT):
    case REQUEST(ACTION_TYPES.DELETE_SCENARIOEXPECTEDOUTPUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SCENARIOEXPECTEDOUTPUT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SCENARIOEXPECTEDOUTPUT):
    case FAILURE(ACTION_TYPES.CREATE_SCENARIOEXPECTEDOUTPUT):
    case FAILURE(ACTION_TYPES.UPDATE_SCENARIOEXPECTEDOUTPUT):
    case FAILURE(ACTION_TYPES.DELETE_SCENARIOEXPECTEDOUTPUT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SCENARIOEXPECTEDOUTPUT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SCENARIOEXPECTEDOUTPUT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SCENARIOEXPECTEDOUTPUT):
    case SUCCESS(ACTION_TYPES.UPDATE_SCENARIOEXPECTEDOUTPUT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SCENARIOEXPECTEDOUTPUT):
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

const apiUrl = 'api/scenarioexpectedoutputs';

// Actions

export const getEntities: ICrudGetAllAction<IScenarioexpectedoutput> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SCENARIOEXPECTEDOUTPUT_LIST,
    payload: axios.get<IScenarioexpectedoutput>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IScenarioexpectedoutput> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SCENARIOEXPECTEDOUTPUT,
    payload: axios.get<IScenarioexpectedoutput>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IScenarioexpectedoutput> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SCENARIOEXPECTEDOUTPUT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IScenarioexpectedoutput> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SCENARIOEXPECTEDOUTPUT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IScenarioexpectedoutput> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SCENARIOEXPECTEDOUTPUT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
