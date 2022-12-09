import { IPost } from '@/shared/model/post.model';

export interface IBoard {
  id?: number;
  title?: string;
  category?: string;
  createdAt?: Date;
  createdBy?: string;
  modifiedAt?: Date | null;
  modifiedBy?: string | null;
  posts?: IPost[] | null;
}

export class Board implements IBoard {
  constructor(
    public id?: number,
    public title?: string,
    public category?: string,
    public createdAt?: Date,
    public createdBy?: string,
    public modifiedAt?: Date | null,
    public modifiedBy?: string | null,
    public posts?: IPost[] | null
  ) {}
}
