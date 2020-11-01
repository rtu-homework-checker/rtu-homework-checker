import { Moment } from 'moment';

export interface ITask {
  id?: number;
  title?: string;
  hasvariants?: boolean;
  modifiedat?: string;
  deletedat?: string;
  createdat?: string;
  userId?: number;
}

export const defaultValue: Readonly<ITask> = {
  hasvariants: false,
};
