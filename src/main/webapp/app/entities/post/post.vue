<template>
  <div>
    <h2 id="page-heading" data-cy="PostHeading">
      <span id="post-heading">Posts</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'PostCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-post">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Post </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && posts && posts.length === 0">
      <span>No posts found</span>
    </div>
    <div class="table-responsive" v-if="posts && posts.length > 0">
      <table class="table table-striped" aria-describedby="posts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span>Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span>Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contents')">
              <span>Contents</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contents'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('readCnt')">
              <span>Read Cnt</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('goodCnt')">
              <span>Good Cnt</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'goodCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('badCnt')">
              <span>Bad Cnt</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'badCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createAt')">
              <span>Create At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createBy')">
              <span>Create By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span>Modified At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span>Modified By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('board.id')">
              <span>Board</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'board.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="post in posts" :key="post.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PostView', params: { postId: post.id } }">{{ post.id }}</router-link>
            </td>
            <td>{{ post.status }}</td>
            <td>{{ post.title }}</td>
            <td>
              <a v-if="post.contents" v-on:click="openFile(post.contentsContentType, post.contents)">open</a>
              <span v-if="post.contents">{{ post.contentsContentType }}, {{ byteSize(post.contents) }}</span>
            </td>
            <td>{{ post.readCnt }}</td>
            <td>{{ post.goodCnt }}</td>
            <td>{{ post.badCnt }}</td>
            <td>{{ post.createAt | formatDate }}</td>
            <td>{{ post.createBy }}</td>
            <td>{{ post.modifiedAt | formatDate }}</td>
            <td>{{ post.modifiedBy }}</td>
            <td>
              <div v-if="post.board">
                <router-link :to="{ name: 'BoardView', params: { boardId: post.board.id } }">{{ post.board.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PostView', params: { postId: post.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PostEdit', params: { postId: post.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(post)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="jhipsterGitApp.post.delete.question" data-cy="postDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-post-heading">Are you sure you want to delete this Post?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-post"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePost()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="posts && posts.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post.component.ts"></script>
