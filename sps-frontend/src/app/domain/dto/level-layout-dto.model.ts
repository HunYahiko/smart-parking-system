import {LayoutObject} from './layout-object.model';

export interface LevelLayoutDto {
  layoutObjects: Array<LayoutObject>;
  width: number;
  length: number;
}
