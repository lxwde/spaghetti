export interface IAddress {
  id?: number;
  addressType?: string;
  city?: string;
  detail?: string;
}

export class Address implements IAddress {
  constructor(public id?: number, public addressType?: string, public city?: string, public detail?: string) {}
}
