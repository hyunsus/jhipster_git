<template>
  <div>
    <h2 id="page-heading" data-cy="AttachGroupHeading">
      <span id="attach-group-heading">Attach Groups</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'AttachGroupCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-attach-group"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Attach Group </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && attachGroups && attachGroups.length === 0">
      <span>No attachGroups found</span>
    </div>
    <div class="table-responsive" v-if="attachGroups && attachGroups.length > 0">
      <table class="table table-striped" aria-describedby="attachGroups">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span>Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span>Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('post.id')">
              <span>Post</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'post.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="attachGroup in attachGroups" :key="attachGroup.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AttachGroupView', params: { attachGroupId: attachGroup.id } }">{{ attachGroup.id }}</router-link>
            </td>
            <td>{{ attachGroup.createdAt | formatDate }}</td>
            <td>{{ attachGroup.createdBy }}</td>
            <td>
              <div v-if="attachGroup.post">
                <router-link :to="{ name: 'PostView', params: { postId: attachGroup.post.id } }">{{ attachGroup.post.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AttachGroupView', params: { attachGroupId: attachGroup.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AttachGroupEdit', params: { attachGroupId: attachGroup.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(attachGroup)"
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
        ><span id="jhipsterGitApp.attachGroup.delete.question" data-cy="attachGroupDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-attachGroup-heading">Are you sure you want to delete this Attach Group?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-attachGroup"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeAttachGroup()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="attachGroups && attachGroups.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./attach-group.component.ts"></script>
