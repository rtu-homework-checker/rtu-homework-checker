import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVariant, defaultValue } from 'app/shared/model/variant.model';

export const ACTION_TYPES = {
  FETCH_VARIANT_LIST: 'variant/FETCH_VARIANT_LIST',
  FETCH_VARIANT: 'variant/FETCH_VARIANT',
  CREATE_VARIANT: 'variant/CREATE_VARIANT',
  UPDATE_VARIANT: 'variant/UPDATE_VARIANT',
  DELETE_VARIANT: 'variant/DELETE_VARIANT',
  RESET: 'variant/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVariant>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type VariantState = Readonly<typeof initialState>;

// Reducer

export default (state: VariantState = initialState, action): VariantState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VARIANT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VARIANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VARIANT):
    case REQUEST(ACTION_TYPES.UPDATE_VARIANT):
    case REQUEST(ACTION_TYPES.DELETE_VARIANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_VARIANT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VARIANT):
    case FAILURE(ACTION_TYPES.CREATE_VARIANT):
    case FAILURE(ACTION_TYPES.UPDATE_VARIANT):
    case FAILURE(ACTION_TYPES.DELETE_VARIANT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VARIANT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_VARIANT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VARIANT):
    case SUCCESS(ACTION_TYPES.UPDATE_VARIANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VARIANT):
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

const apiUrl = 'api/variants';

// Actions

export const getEntities: ICrudGetAllAction<IVariant> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VARIANT_LIST,
    payload: axios.get<IVariant>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IVariant> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VARIANT,
    payload: axios.get<IVariant>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVariant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VARIANT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVariant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VARIANT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVariant> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VARIANT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
