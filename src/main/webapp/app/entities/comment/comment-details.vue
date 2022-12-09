<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="comment">
        <h2 class="jh-entity-heading" data-cy="commentDetailsHeading"><span>Comment</span> {{ comment.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Depth</span>
          </dt>
          <dd>
            <span>{{ comment.depth }}</span>
          </dd>
          <dt>
            <span>Comment</span>
          </dt>
          <dd>
            <span>{{ comment.comment }}</span>
          </dd>
          <dt>
            <span>Read Cnt</span>
          </dt>
          <dd>
            <span>{{ comment.readCnt }}</span>
          </dd>
          <dt>
            <span>Good Cnt</span>
          </dt>
          <dd>
            <span>{{ comment.goodCnt }}</span>
          </dd>
          <dt>
            <span>Bad Cnt</span>
          </dt>
          <dd>
            <span>{{ comment.badCnt }}</span>
          </dd>
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ comment.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Created By</span>
          </dt>
          <dd>
            <span>{{ comment.createdBy }}</span>
          </dd>
          <dt>
            <span>Modified At</span>
          </dt>
          <dd>
            <span>{{ comment.modifiedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Modified By</span>
          </dt>
          <dd>
            <span>{{ comment.modifiedBy }}</span>
          </dd>
          <dt>
            <span>Post</span>
          </dt>
          <dd>
            <div v-if="comment.post">
              <router-link :to="{ name: 'PostView', params: { postId: comment.post.id } }">{{ comment.post.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Parent</span>
          </dt>
          <dd>
            <div v-if="comment.parent">
              <router-link :to="{ name: 'CommentView', params: { commentId: comment.parent.id } }">{{ comment.parent.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="comment.id" :to="{ name: 'CommentEdit', params: { commentId: comment.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./comment-details.component.ts"></script>
