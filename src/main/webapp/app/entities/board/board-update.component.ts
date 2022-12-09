import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import PostService from '@/entities/post/post.service';
import { IPost } from '@/shared/model/post.model';

import { IBoard, Board } from '@/shared/model/board.model';
import BoardService from './board.service';

const validations: any = {
  board: {
    title: {
      required,
    },
    category: {
      required,
    },
    createdAt: {
      required,
    },
    createdBy: {
      required,
    },
    modifiedAt: {},
    modifiedBy: {},
  },
};

@Component({
  validations,
})
export default class BoardUpdate extends Vue {
  @Inject('boardService') private boardService: () => BoardService;
  @Inject('alertService') private alertService: () => AlertService;

  public board: IBoard = new Board();

  @Inject('postService') private postService: () => PostService;

  public posts: IPost[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.boardId) {
        vm.retrieveBoard(to.params.boardId);
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
    if (this.board.id) {
      this.boardService()
        .update(this.board)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Board is updated with identifier ' + param.id;
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
      this.boardService()
        .create(this.board)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Board is created with identifier ' + param.id;
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
      this.board[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.board[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.board[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.board[field] = null;
    }
  }

  public retrieveBoard(boardId): void {
    this.boardService()
      .find(boardId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        this.board = res;
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
  }
}
