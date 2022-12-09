import { IPost } from '@/shared/model/post.model';

export interface IComment {
  id?: number;
  depth?: number | null;
  comment?: string | null;
  readCnt?: number | null;
  goodCnt?: number | null;
  badCnt?: number | null;
  createdAt?: Date;
  createdBy?: string;
  modifiedAt?: Date | null;
  modifiedBy?: string | null;
  comments?: IComment[] | null;
  post?: IPost | null;
  parent?: IComment | null;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public depth?: number | null,
    public comment?: string | null,
    public readCnt?: number | null,
    public goodCnt?: number | null,
    public badCnt?: number | null,
    public createdAt?: Date,
    public createdBy?: string,
    public modifiedAt?: Date | null,
    public modifiedBy?: string | null,
    public comments?: IComment[] | null,
    public post?: IPost | null,
    public parent?: IComment | null
  ) {}
}
