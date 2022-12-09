<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="jhipsterGitApp.comment.home.createOrEditLabel" data-cy="CommentCreateUpdateHeading">Create or edit a Comment</h2>
        <div>
          <div class="form-group" v-if="comment.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="comment.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-depth">Depth</label>
            <input
              type="number"
              class="form-control"
              name="depth"
              id="comment-depth"
              data-cy="depth"
              :class="{ valid: !$v.comment.depth.$invalid, invalid: $v.comment.depth.$invalid }"
              v-model.number="$v.comment.depth.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-comment">Comment</label>
            <input
              type="text"
              class="form-control"
              name="comment"
              id="comment-comment"
              data-cy="comment"
              :class="{ valid: !$v.comment.comment.$invalid, invalid: $v.comment.comment.$invalid }"
              v-model="$v.comment.comment.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-readCnt">Read Cnt</label>
            <input
              type="number"
              class="form-control"
              name="readCnt"
              id="comment-readCnt"
              data-cy="readCnt"
              :class="{ valid: !$v.comment.readCnt.$invalid, invalid: $v.comment.readCnt.$invalid }"
              v-model.number="$v.comment.readCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-goodCnt">Good Cnt</label>
            <input
              type="number"
              class="form-control"
              name="goodCnt"
              id="comment-goodCnt"
              data-cy="goodCnt"
              :class="{ valid: !$v.comment.goodCnt.$invalid, invalid: $v.comment.goodCnt.$invalid }"
              v-model.number="$v.comment.goodCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-badCnt">Bad Cnt</label>
            <input
              type="number"
              class="form-control"
              name="badCnt"
              id="comment-badCnt"
              data-cy="badCnt"
              :class="{ valid: !$v.comment.badCnt.$invalid, invalid: $v.comment.badCnt.$invalid }"
              v-model.number="$v.comment.badCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="comment-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.comment.createdAt.$invalid, invalid: $v.comment.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.comment.createdAt.$model)"
                @change="updateZonedDateTimeField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.comment.createdAt.$anyDirty && $v.comment.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.comment.createdAt.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.comment.createdAt.ZonedDateTimelocal">
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-createdBy">Created By</label>
            <input
              type="text"
              class="form-control"
              name="createdBy"
              id="comment-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.comment.createdBy.$invalid, invalid: $v.comment.createdBy.$invalid }"
              v-model="$v.comment.createdBy.$model"
              required
            />
            <div v-if="$v.comment.createdBy.$anyDirty && $v.comment.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.comment.createdBy.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-modifiedAt">Modified At</label>
            <div class="d-flex">
              <input
                id="comment-modifiedAt"
                data-cy="modifiedAt"
                type="datetime-local"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.comment.modifiedAt.$invalid, invalid: $v.comment.modifiedAt.$invalid }"
                :value="convertDateTimeFromServer($v.comment.modifiedAt.$model)"
                @change="updateZonedDateTimeField('modifiedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-modifiedBy">Modified By</label>
            <input
              type="text"
              class="form-control"
              name="modifiedBy"
              id="comment-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.comment.modifiedBy.$invalid, invalid: $v.comment.modifiedBy.$invalid }"
              v-model="$v.comment.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-post">Post</label>
            <select class="form-control" id="comment-post" data-cy="post" name="post" v-model="comment.post">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="comment.post && postOption.id === comment.post.id ? comment.post : postOption"
                v-for="postOption in posts"
                :key="postOption.id"
              >
                {{ postOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-parent">Parent</label>
            <select class="form-control" id="comment-parent" data-cy="parent" name="parent" v-model="comment.parent">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="comment.parent && commentOption.id === comment.parent.id ? comment.parent : commentOption"
                v-for="commentOption in comments"
                :key="commentOption.id"
              >
                {{ commentOption.id }}
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
            :disabled="$v.comment.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./comment-update.component.ts"></script>
