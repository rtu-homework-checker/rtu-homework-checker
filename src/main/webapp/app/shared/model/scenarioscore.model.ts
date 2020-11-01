export interface IScenarioscore {
  id?: number;
  passed?: boolean;
  scoreId?: number;
  scenarioexpectedoutputId?: number;
}

export const defaultValue: Readonly<IScenarioscore> = {
  passed: false,
};
