/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import PostService from '@/entities/post/post.service';
import { Post } from '@/shared/model/post.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Post Service', () => {
    let service: PostService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new PostService();
      currentDate = new Date();
      elemDefault = new Post(123, 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', 0, 0, 0, currentDate, 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Post', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            createAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Post', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Post', async () => {
        const returnedFromService = Object.assign(
          {
            status: 'BBBBBB',
            title: 'BBBBBB',
            contents: 'BBBBBB',
            readCnt: 1,
            goodCnt: 1,
            badCnt: 1,
            createAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createBy: 'BBBBBB',
            modifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Post', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Post', async () => {
        const patchObject = Object.assign(
          {
            contents: 'BBBBBB',
            goodCnt: 1,
            createBy: 'BBBBBB',
            modifiedBy: 'BBBBBB',
          },
          new Post()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Post', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Post', async () => {
        const returnedFromService = Object.assign(
          {
            status: 'BBBBBB',
            title: 'BBBBBB',
            contents: 'BBBBBB',
            readCnt: 1,
            goodCnt: 1,
            badCnt: 1,
            createAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createBy: 'BBBBBB',
            modifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Post', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Post', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Post', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
