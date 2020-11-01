export interface IStudent {
  id?: number;
  studentcode?: string;
  name?: string;
  surname?: string;
}

export const defaultValue: Readonly<IStudent> = {};
