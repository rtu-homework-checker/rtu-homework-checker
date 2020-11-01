import { Moment } from 'moment';

export interface IScore {
  id?: number;
  ispassed?: boolean;
  createdat?: string;
  modifiedat?: string;
  deletedat?: string;
  fileId?: number;
  variantId?: number;
}

export const defaultValue: Readonly<IScore> = {
  ispassed: false,
};
