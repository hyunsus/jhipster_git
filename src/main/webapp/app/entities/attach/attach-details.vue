<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="attach">
        <h2 class="jh-entity-heading" data-cy="attachDetailsHeading"><span>Attach</span> {{ attach.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Org</span>
          </dt>
          <dd>
            <span>{{ attach.org }}</span>
          </dd>
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ attach.name }}</span>
          </dd>
          <dt>
            <span>Orig Name</span>
          </dt>
          <dd>
            <span>{{ attach.origName }}</span>
          </dd>
          <dt>
            <span>Ext</span>
          </dt>
          <dd>
            <span>{{ attach.ext }}</span>
          </dd>
          <dt>
            <span>Content Type</span>
          </dt>
          <dd>
            <span>{{ attach.contentType }}</span>
          </dd>
          <dt>
            <span>Path</span>
          </dt>
          <dd>
            <span>{{ attach.path }}</span>
          </dd>
          <dt>
            <span>File Size</span>
          </dt>
          <dd>
            <span>{{ attach.fileSize }}</span>
          </dd>
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ attach.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Created By</span>
          </dt>
          <dd>
            <span>{{ attach.createdBy }}</span>
          </dd>
          <dt>
            <span>Modified At</span>
          </dt>
          <dd>
            <span>{{ attach.modifiedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Modified By</span>
          </dt>
          <dd>
            <span>{{ attach.modifiedBy }}</span>
          </dd>
          <dt>
            <span>Attach Group</span>
          </dt>
          <dd>
            <div v-if="attach.attachGroup">
              <router-link :to="{ name: 'AttachGroupView', params: { attachGroupId: attach.attachGroup.id } }">{{
                attach.attachGroup.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="attach.id" :to="{ name: 'AttachEdit', params: { attachId: attach.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./attach-details.component.ts"></script>
