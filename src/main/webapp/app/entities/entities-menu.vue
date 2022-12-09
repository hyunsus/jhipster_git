<template>
  <div>
    <b-dropdown-item to="/board">
      <font-awesome-icon icon="asterisk" />
      <span>Board</span>
    </b-dropdown-item>
    <b-dropdown-item to="/post">
      <font-awesome-icon icon="asterisk" />
      <span>Post</span>
    </b-dropdown-item>
    <b-dropdown-item to="/attach-group">
      <font-awesome-icon icon="asterisk" />
      <span>Attach Group</span>
    </b-dropdown-item>
    <b-dropdown-item to="/attach">
      <font-awesome-icon icon="asterisk" />
      <span>Attach</span>
    </b-dropdown-item>
    <b-dropdown-item to="/comment">
      <font-awesome-icon icon="asterisk" />
      <span>Comment</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
