/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BoardDetailComponent from '@/entities/board/board-details.vue';
import BoardClass from '@/entities/board/board-details.component';
import BoardService from '@/entities/board/board.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Board Management Detail Component', () => {
    let wrapper: Wrapper<BoardClass>;
    let comp: BoardClass;
    let boardServiceStub: SinonStubbedInstance<BoardService>;

    beforeEach(() => {
      boardServiceStub = sinon.createStubInstance<BoardService>(BoardService);

      wrapper = shallowMount<BoardClass>(BoardDetailComponent, {
        store,
        localVue,
        router,
        provide: { boardService: () => boardServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBoard = { id: 123 };
        boardServiceStub.find.resolves(foundBoard);

        // WHEN
        comp.retrieveBoard(123);
        await comp.$nextTick();

        // THEN
        expect(comp.board).toBe(foundBoard);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBoard = { id: 123 };
        boardServiceStub.find.resolves(foundBoard);

        // WHEN
        comp.beforeRouteEnter({ params: { boardId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.board).toBe(foundBoard);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
