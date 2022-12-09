import { IAttachGroup } from '@/shared/model/attach-group.model';

export interface IAttach {
  id?: number;
  org?: number | null;
  name?: string | null;
  origName?: string | null;
  ext?: string | null;
  contentType?: string | null;
  path?: string | null;
  fileSize?: number | null;
  createdAt?: Date;
  createdBy?: string;
  modifiedAt?: Date | null;
  modifiedBy?: string | null;
  attachGroup?: IAttachGroup | null;
}

export class Attach implements IAttach {
  constructor(
    public id?: number,
    public org?: number | null,
    public name?: string | null,
    public origName?: string | null,
    public ext?: string | null,
    public contentType?: string | null,
    public path?: string | null,
    public fileSize?: number | null,
    public createdAt?: Date,
    public createdBy?: string,
    public modifiedAt?: Date | null,
    public modifiedBy?: string | null,
    public attachGroup?: IAttachGroup | null
  ) {}
}
