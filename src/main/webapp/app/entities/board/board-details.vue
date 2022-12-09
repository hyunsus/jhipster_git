<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="board">
        <h2 class="jh-entity-heading" data-cy="boardDetailsHeading"><span>Board</span> {{ board.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Title</span>
          </dt>
          <dd>
            <span>{{ board.title }}</span>
          </dd>
          <dt>
            <span>Category</span>
          </dt>
          <dd>
            <span>{{ board.category }}</span>
          </dd>
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ board.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Created By</span>
          </dt>
          <dd>
            <span>{{ board.createdBy }}</span>
          </dd>
          <dt>
            <span>Modified At</span>
          </dt>
          <dd>
            <span>{{ board.modifiedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Modified By</span>
          </dt>
          <dd>
            <span>{{ board.modifiedBy }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="board.id" :to="{ name: 'BoardEdit', params: { boardId: board.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./board-details.component.ts"></script>
