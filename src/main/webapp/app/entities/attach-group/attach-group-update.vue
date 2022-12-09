<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="jhipsterGitApp.attachGroup.home.createOrEditLabel" data-cy="AttachGroupCreateUpdateHeading">
          Create or edit a AttachGroup
        </h2>
        <div>
          <div class="form-group" v-if="attachGroup.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="attachGroup.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-group-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="attach-group-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.attachGroup.createdAt.$invalid, invalid: $v.attachGroup.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.attachGroup.createdAt.$model)"
                @change="updateZonedDateTimeField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.attachGroup.createdAt.$anyDirty && $v.attachGroup.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.attachGroup.createdAt.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.attachGroup.createdAt.ZonedDateTimelocal">
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-group-createdBy">Created By</label>
            <input
              type="text"
              class="form-control"
              name="createdBy"
              id="attach-group-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.attachGroup.createdBy.$invalid, invalid: $v.attachGroup.createdBy.$invalid }"
              v-model="$v.attachGroup.createdBy.$model"
              required
            />
            <div v-if="$v.attachGroup.createdBy.$anyDirty && $v.attachGroup.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.attachGroup.createdBy.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-group-post">Post</label>
            <select class="form-control" id="attach-group-post" data-cy="post" name="post" v-model="attachGroup.post">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="attachGroup.post && postOption.id === attachGroup.post.id ? attachGroup.post : postOption"
                v-for="postOption in posts"
                :key="postOption.id"
              >
                {{ postOption.id }}
              </option>
            </select>
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
            :disabled="$v.attachGroup.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./attach-group-update.component.ts"></script>
