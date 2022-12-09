import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import BoardService from './board/board.service';
import PostService from './post/post.service';
import AttachGroupService from './attach-group/attach-group.service';
import AttachService from './attach/attach.service';
import CommentService from './comment/comment.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('boardService') private boardService = () => new BoardService();
  @Provide('postService') private postService = () => new PostService();
  @Provide('attachGroupService') private attachGroupService = () => new AttachGroupService();
  @Provide('attachService') private attachService = () => new AttachService();
  @Provide('commentService') private commentService = () => new CommentService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
