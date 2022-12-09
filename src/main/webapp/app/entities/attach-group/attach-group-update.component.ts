import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import PostService from '@/entities/post/post.service';
import { IPost } from '@/shared/model/post.model';

import AttachService from '@/entities/attach/attach.service';
import { IAttach } from '@/shared/model/attach.model';

import { IAttachGroup, AttachGroup } from '@/shared/model/attach-group.model';
import AttachGroupService from './attach-group.service';

const validations: any = {
  attachGroup: {
    createdAt: {
      required,
    },
    createdBy: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class AttachGroupUpdate extends Vue {
  @Inject('attachGroupService') private attachGroupService: () => AttachGroupService;
  @Inject('alertService') private alertService: () => AlertService;

  public attachGroup: IAttachGroup = new AttachGroup();

  @Inject('postService') private postService: () => PostService;

  public posts: IPost[] = [];

  @Inject('attachService') private attachService: () => AttachService;

  public attaches: IAttach[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attachGroupId) {
        vm.retrieveAttachGroup(to.params.attachGroupId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.attachGroup.id) {
      this.attachGroupService()
        .update(this.attachGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AttachGroup is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.attachGroupService()
        .create(this.attachGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AttachGroup is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.attachGroup[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.attachGroup[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.attachGroup[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.attachGroup[field] = null;
    }
  }

  public retrieveAttachGroup(attachGroupId): void {
    this.attachGroupService()
      .find(attachGroupId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        this.attachGroup = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.postService()
      .retrieve()
      .then(res => {
        this.posts = res.data;
      });
    this.attachService()
      .retrieve()
      .then(res => {
        this.attaches = res.data;
      });
  }
}
