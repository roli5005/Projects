<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Add Consultation" }}
        </v-toolbar>
        <span>{{ message }}</span>
        <v-card-actions>
          <v-btn @click="closeThis"> {{ "OK" }} </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>

export default {
  name: "Notification",
  props: {
    message: String,
    opened: Boolean,
  },
  methods: {
    closeThis() {
      this.opened = false;
    },
  },
};
</script>

<style scoped></style>
