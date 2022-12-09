<template>
  <div>
    <h2 id="page-heading" data-cy="AttachHeading">
      <span id="attach-heading">Attaches</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'AttachCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-attach"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Attach </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && attaches && attaches.length === 0">
      <span>No attaches found</span>
    </div>
    <div class="table-responsive" v-if="attaches && attaches.length > 0">
      <table class="table table-striped" aria-describedby="attaches">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('org')">
              <span>Org</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'org'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span>Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('origName')">
              <span>Orig Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'origName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ext')">
              <span>Ext</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ext'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contentType')">
              <span>Content Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contentType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('path')">
              <span>Path</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'path'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileSize')">
              <span>File Size</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileSize'"></jhi-sort-indicator>
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
            <th scope="row" v-on:click="changeOrder('attachGroup.id')">
              <span>Attach Group</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'attachGroup.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="attach in attaches" :key="attach.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AttachView', params: { attachId: attach.id } }">{{ attach.id }}</router-link>
            </td>
            <td>{{ attach.org }}</td>
            <td>{{ attach.name }}</td>
            <td>{{ attach.origName }}</td>
            <td>{{ attach.ext }}</td>
            <td>{{ attach.contentType }}</td>
            <td>{{ attach.path }}</td>
            <td>{{ attach.fileSize }}</td>
            <td>{{ attach.createdAt | formatDate }}</td>
            <td>{{ attach.createdBy }}</td>
            <td>{{ attach.modifiedAt | formatDate }}</td>
            <td>{{ attach.modifiedBy }}</td>
            <td>
              <div v-if="attach.attachGroup">
                <router-link :to="{ name: 'AttachGroupView', params: { attachGroupId: attach.attachGroup.id } }">{{
                  attach.attachGroup.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AttachView', params: { attachId: attach.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AttachEdit', params: { attachId: attach.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(attach)"
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
        ><span id="jhipsterGitApp.attach.delete.question" data-cy="attachDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-attach-heading">Are you sure you want to delete this Attach?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-attach"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeAttach()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="attaches && attaches.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./attach.component.ts"></script>
