import {Gara} from './gara';

export interface Ticket {
  id?: number;
  price?: number;
  start?: string;
  end?: string;
  startDay?: string;
  startHours?: string;
  garage?: Gara;
  quality?: number;
  status?: boolean;
}
