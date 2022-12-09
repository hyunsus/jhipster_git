/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AttachDetailComponent from '@/entities/attach/attach-details.vue';
import AttachClass from '@/entities/attach/attach-details.component';
import AttachService from '@/entities/attach/attach.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Attach Management Detail Component', () => {
    let wrapper: Wrapper<AttachClass>;
    let comp: AttachClass;
    let attachServiceStub: SinonStubbedInstance<AttachService>;

    beforeEach(() => {
      attachServiceStub = sinon.createStubInstance<AttachService>(AttachService);

      wrapper = shallowMount<AttachClass>(AttachDetailComponent, {
        store,
        localVue,
        router,
        provide: { attachService: () => attachServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAttach = { id: 123 };
        attachServiceStub.find.resolves(foundAttach);

        // WHEN
        comp.retrieveAttach(123);
        await comp.$nextTick();

        // THEN
        expect(comp.attach).toBe(foundAttach);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAttach = { id: 123 };
        attachServiceStub.find.resolves(foundAttach);

        // WHEN
        comp.beforeRouteEnter({ params: { attachId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.attach).toBe(foundAttach);
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
