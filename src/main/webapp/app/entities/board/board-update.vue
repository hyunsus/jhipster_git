<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="jhipsterGitApp.board.home.createOrEditLabel" data-cy="BoardCreateUpdateHeading">Create or edit a Board</h2>
        <div>
          <div class="form-group" v-if="board.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="board.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="board-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="board-title"
              data-cy="title"
              :class="{ valid: !$v.board.title.$invalid, invalid: $v.board.title.$invalid }"
              v-model="$v.board.title.$model"
              required
            />
            <div v-if="$v.board.title.$anyDirty && $v.board.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.board.title.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="board-category">Category</label>
            <input
              type="text"
              class="form-control"
              name="category"
              id="board-category"
              data-cy="category"
              :class="{ valid: !$v.board.category.$invalid, invalid: $v.board.category.$invalid }"
              v-model="$v.board.category.$model"
              required
            />
            <div v-if="$v.board.category.$anyDirty && $v.board.category.$invalid">
              <small class="form-text text-danger" v-if="!$v.board.category.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="board-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="board-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.board.createdAt.$invalid, invalid: $v.board.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.board.createdAt.$model)"
                @change="updateZonedDateTimeField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.board.createdAt.$anyDirty && $v.board.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.board.createdAt.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.board.createdAt.ZonedDateTimelocal">
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="board-createdBy">Created By</label>
            <input
              type="text"
              class="form-control"
              name="createdBy"
              id="board-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.board.createdBy.$invalid, invalid: $v.board.createdBy.$invalid }"
              v-model="$v.board.createdBy.$model"
              required
            />
            <div v-if="$v.board.createdBy.$anyDirty && $v.board.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.board.createdBy.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="board-modifiedAt">Modified At</label>
            <div class="d-flex">
              <input
                id="board-modifiedAt"
                data-cy="modifiedAt"
                type="datetime-local"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.board.modifiedAt.$invalid, invalid: $v.board.modifiedAt.$invalid }"
                :value="convertDateTimeFromServer($v.board.modifiedAt.$model)"
                @change="updateZonedDateTimeField('modifiedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="board-modifiedBy">Modified By</label>
            <input
              type="text"
              class="form-control"
              name="modifiedBy"
              id="board-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.board.modifiedBy.$invalid, invalid: $v.board.modifiedBy.$invalid }"
              v-model="$v.board.modifiedBy.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.board.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./board-update.component.ts"></script>
