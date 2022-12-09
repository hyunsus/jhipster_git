/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AttachGroupDetailComponent from '@/entities/attach-group/attach-group-details.vue';
import AttachGroupClass from '@/entities/attach-group/attach-group-details.component';
import AttachGroupService from '@/entities/attach-group/attach-group.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AttachGroup Management Detail Component', () => {
    let wrapper: Wrapper<AttachGroupClass>;
    let comp: AttachGroupClass;
    let attachGroupServiceStub: SinonStubbedInstance<AttachGroupService>;

    beforeEach(() => {
      attachGroupServiceStub = sinon.createStubInstance<AttachGroupService>(AttachGroupService);

      wrapper = shallowMount<AttachGroupClass>(AttachGroupDetailComponent, {
        store,
        localVue,
        router,
        provide: { attachGroupService: () => attachGroupServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAttachGroup = { id: 123 };
        attachGroupServiceStub.find.resolves(foundAttachGroup);

        // WHEN
        comp.retrieveAttachGroup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.attachGroup).toBe(foundAttachGroup);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAttachGroup = { id: 123 };
        attachGroupServiceStub.find.resolves(foundAttachGroup);

        // WHEN
        comp.beforeRouteEnter({ params: { attachGroupId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.attachGroup).toBe(foundAttachGroup);
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
