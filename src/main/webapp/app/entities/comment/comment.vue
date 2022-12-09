<template>
  <div>
    <h2 id="page-heading" data-cy="CommentHeading">
      <span id="comment-heading">Comments</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'CommentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-comment"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Comment </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && comments && comments.length === 0">
      <span>No comments found</span>
    </div>
    <div class="table-responsive" v-if="comments && comments.length > 0">
      <table class="table table-striped" aria-describedby="comments">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('depth')">
              <span>Depth</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'depth'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('comment')">
              <span>Comment</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'comment'"></jhi-sort-indicator>
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
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span>Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span>Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span>Modified At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span>Modified By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('post.id')">
              <span>Post</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'post.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('parent.id')">
              <span>Parent</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parent.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="comment in comments" :key="comment.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CommentView', params: { commentId: comment.id } }">{{ comment.id }}</router-link>
            </td>
            <td>{{ comment.depth }}</td>
            <td>{{ comment.comment }}</td>
            <td>{{ comment.readCnt }}</td>
            <td>{{ comment.goodCnt }}</td>
            <td>{{ comment.badCnt }}</td>
            <td>{{ comment.createdAt | formatDate }}</td>
            <td>{{ comment.createdBy }}</td>
            <td>{{ comment.modifiedAt | formatDate }}</td>
            <td>{{ comment.modifiedBy }}</td>
            <td>
              <div v-if="comment.post">
                <router-link :to="{ name: 'PostView', params: { postId: comment.post.id } }">{{ comment.post.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="comment.parent">
                <router-link :to="{ name: 'CommentView', params: { commentId: comment.parent.id } }">{{ comment.parent.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CommentView', params: { commentId: comment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CommentEdit', params: { commentId: comment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(comment)"
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
        ><span id="jhipsterGitApp.comment.delete.question" data-cy="commentDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-comment-heading">Are you sure you want to delete this Comment?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-comment"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeComment()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="comments && comments.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./comment.component.ts"></script>
