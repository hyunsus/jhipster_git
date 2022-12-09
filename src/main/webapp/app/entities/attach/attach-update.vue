<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="jhipsterGitApp.attach.home.createOrEditLabel" data-cy="AttachCreateUpdateHeading">Create or edit a Attach</h2>
        <div>
          <div class="form-group" v-if="attach.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="attach.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-org">Org</label>
            <input
              type="number"
              class="form-control"
              name="org"
              id="attach-org"
              data-cy="org"
              :class="{ valid: !$v.attach.org.$invalid, invalid: $v.attach.org.$invalid }"
              v-model.number="$v.attach.org.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="attach-name"
              data-cy="name"
              :class="{ valid: !$v.attach.name.$invalid, invalid: $v.attach.name.$invalid }"
              v-model="$v.attach.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-origName">Orig Name</label>
            <input
              type="text"
              class="form-control"
              name="origName"
              id="attach-origName"
              data-cy="origName"
              :class="{ valid: !$v.attach.origName.$invalid, invalid: $v.attach.origName.$invalid }"
              v-model="$v.attach.origName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-ext">Ext</label>
            <input
              type="text"
              class="form-control"
              name="ext"
              id="attach-ext"
              data-cy="ext"
              :class="{ valid: !$v.attach.ext.$invalid, invalid: $v.attach.ext.$invalid }"
              v-model="$v.attach.ext.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-contentType">Content Type</label>
            <input
              type="text"
              class="form-control"
              name="contentType"
              id="attach-contentType"
              data-cy="contentType"
              :class="{ valid: !$v.attach.contentType.$invalid, invalid: $v.attach.contentType.$invalid }"
              v-model="$v.attach.contentType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-path">Path</label>
            <input
              type="text"
              class="form-control"
              name="path"
              id="attach-path"
              data-cy="path"
              :class="{ valid: !$v.attach.path.$invalid, invalid: $v.attach.path.$invalid }"
              v-model="$v.attach.path.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-fileSize">File Size</label>
            <input
              type="number"
              class="form-control"
              name="fileSize"
              id="attach-fileSize"
              data-cy="fileSize"
              :class="{ valid: !$v.attach.fileSize.$invalid, invalid: $v.attach.fileSize.$invalid }"
              v-model.number="$v.attach.fileSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="attach-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.attach.createdAt.$invalid, invalid: $v.attach.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.attach.createdAt.$model)"
                @change="updateZonedDateTimeField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.attach.createdAt.$anyDirty && $v.attach.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.attach.createdAt.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.attach.createdAt.ZonedDateTimelocal">
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-createdBy">Created By</label>
            <input
              type="text"
              class="form-control"
              name="createdBy"
              id="attach-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.attach.createdBy.$invalid, invalid: $v.attach.createdBy.$invalid }"
              v-model="$v.attach.createdBy.$model"
              required
            />
            <div v-if="$v.attach.createdBy.$anyDirty && $v.attach.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.attach.createdBy.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-modifiedAt">Modified At</label>
            <div class="d-flex">
              <input
                id="attach-modifiedAt"
                data-cy="modifiedAt"
                type="datetime-local"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.attach.modifiedAt.$invalid, invalid: $v.attach.modifiedAt.$invalid }"
                :value="convertDateTimeFromServer($v.attach.modifiedAt.$model)"
                @change="updateZonedDateTimeField('modifiedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-modifiedBy">Modified By</label>
            <input
              type="text"
              class="form-control"
              name="modifiedBy"
              id="attach-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.attach.modifiedBy.$invalid, invalid: $v.attach.modifiedBy.$invalid }"
              v-model="$v.attach.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="attach-attachGroup">Attach Group</label>
            <select class="form-control" id="attach-attachGroup" data-cy="attachGroup" name="attachGroup" v-model="attach.attachGroup">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="attach.attachGroup && attachGroupOption.id === attach.attachGroup.id ? attach.attachGroup : attachGroupOption"
                v-for="attachGroupOption in attachGroups"
                :key="attachGroupOption.id"
              >
                {{ attachGroupOption.id }}
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
            :disabled="$v.attach.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./attach-update.component.ts"></script>
