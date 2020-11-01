import { Moment } from 'moment';

export interface IVariant {
  id?: number;
  title?: string;
  modifiedat?: string;
  deletedat?: string;
  createdat?: string;
  taskId?: number;
}

export const defaultValue: Readonly<IVariant> = {};
