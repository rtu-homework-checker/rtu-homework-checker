import { Moment } from 'moment';

export interface IFile {
  id?: number;
  modifiedat?: string;
  deletedat?: string;
  createdat?: string;
  filename?: string;
  taskId?: number;
  variantId?: number;
  studentId?: number;
}

export const defaultValue: Readonly<IFile> = {};
