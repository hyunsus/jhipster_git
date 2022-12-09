import { IBoard } from '@/shared/model/board.model';

export interface IPost {
  id?: number;
  status?: string;
  title?: string;
  contentsContentType?: string | null;
  contents?: string | null;
  readCnt?: number | null;
  goodCnt?: number | null;
  badCnt?: number | null;
  createAt?: Date;
  createBy?: string;
  modifiedAt?: Date | null;
  modifiedBy?: string | null;
  board?: IBoard | null;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public status?: string,
    public title?: string,
    public contentsContentType?: string | null,
    public contents?: string | null,
    public readCnt?: number | null,
    public goodCnt?: number | null,
    public badCnt?: number | null,
    public createAt?: Date,
    public createBy?: string,
    public modifiedAt?: Date | null,
    public modifiedBy?: string | null,
    public board?: IBoard | null
  ) {}
}
