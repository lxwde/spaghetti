import { IEmployee } from '@/shared/model/employee.model';

export interface IJob {
  id?: number;
  jobTitle?: string | null;
  minSalary?: number | null;
  maxSalary?: number | null;
  employee?: IEmployee | null;
}

export class Job implements IJob {
  constructor(
    public id?: number,
    public jobTitle?: string | null,
    public minSalary?: number | null,
    public maxSalary?: number | null,
    public employee?: IEmployee | null
  ) {}
}
