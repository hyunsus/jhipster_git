import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAttachGroup } from '@/shared/model/attach-group.model';
import AttachGroupService from './attach-group.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AttachGroupDetails extends Vue {
  @Inject('attachGroupService') private attachGroupService: () => AttachGroupService;
  @Inject('alertService') private alertService: () => AlertService;

  public attachGroup: IAttachGroup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attachGroupId) {
        vm.retrieveAttachGroup(to.params.attachGroupId);
      }
    });
  }

  public retrieveAttachGroup(attachGroupId) {
    this.attachGroupService()
      .find(attachGroupId)
      .then(res => {
        this.attachGroup = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
