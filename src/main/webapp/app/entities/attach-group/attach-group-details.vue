<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="attachGroup">
        <h2 class="jh-entity-heading" data-cy="attachGroupDetailsHeading"><span>AttachGroup</span> {{ attachGroup.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ attachGroup.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Created By</span>
          </dt>
          <dd>
            <span>{{ attachGroup.createdBy }}</span>
          </dd>
          <dt>
            <span>Post</span>
          </dt>
          <dd>
            <div v-if="attachGroup.post">
              <router-link :to="{ name: 'PostView', params: { postId: attachGroup.post.id } }">{{ attachGroup.post.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="attachGroup.id"
          :to="{ name: 'AttachGroupEdit', params: { attachGroupId: attachGroup.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./attach-group-details.component.ts"></script>
