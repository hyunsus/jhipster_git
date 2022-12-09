<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="post">
        <h2 class="jh-entity-heading" data-cy="postDetailsHeading"><span>Post</span> {{ post.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ post.status }}</span>
          </dd>
          <dt>
            <span>Title</span>
          </dt>
          <dd>
            <span>{{ post.title }}</span>
          </dd>
          <dt>
            <span>Contents</span>
          </dt>
          <dd>
            <div v-if="post.contents">
              <a v-on:click="openFile(post.contentsContentType, post.contents)">open</a>
              {{ post.contentsContentType }}, {{ byteSize(post.contents) }}
            </div>
          </dd>
          <dt>
            <span>Read Cnt</span>
          </dt>
          <dd>
            <span>{{ post.readCnt }}</span>
          </dd>
          <dt>
            <span>Good Cnt</span>
          </dt>
          <dd>
            <span>{{ post.goodCnt }}</span>
          </dd>
          <dt>
            <span>Bad Cnt</span>
          </dt>
          <dd>
            <span>{{ post.badCnt }}</span>
          </dd>
          <dt>
            <span>Create At</span>
          </dt>
          <dd>
            <span>{{ post.createAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Create By</span>
          </dt>
          <dd>
            <span>{{ post.createBy }}</span>
          </dd>
          <dt>
            <span>Modified At</span>
          </dt>
          <dd>
            <span>{{ post.modifiedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Modified By</span>
          </dt>
          <dd>
            <span>{{ post.modifiedBy }}</span>
          </dd>
          <dt>
            <span>Board</span>
          </dt>
          <dd>
            <div v-if="post.board">
              <router-link :to="{ name: 'BoardView', params: { boardId: post.board.id } }">{{ post.board.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="post.id" :to="{ name: 'PostEdit', params: { postId: post.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post-details.component.ts"></script>
