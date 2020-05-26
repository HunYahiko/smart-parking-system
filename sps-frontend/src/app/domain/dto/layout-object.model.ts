import {LayoutObjectType} from '../enums/layout-object-type.enum';
import {LayoutObjectOrientation} from '../enums/layout-object-orientation.enum';

export interface LayoutObject {
  id: string;
  type: LayoutObjectType;
  orientation: LayoutObjectOrientation;
  x: number;
  y: number;
}
