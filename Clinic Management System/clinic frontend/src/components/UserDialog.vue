<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Edit item" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.id" label="ID" />
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.password" label="Password" />
          <v-text-field v-model="user.username" label="Name" />
          <v-text-field v-model="user.role" label="Role" />
        </v-form>
        <v-card-actions>
          <v-btn @click="editUser">
            {{ "Save" }}
          </v-btn>
          <v-btn @click="deleteUser">
            {{ "Delete" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
    userHeader: [
      {
        text: "Email",
        align: "start",
        value: "email",
      },
      {
        text: "Username",
        value: "username",
      },
      {
        text: "Password",
        value: "password",
      },
      {
        text: "Role",
        value: "role",
      },
    ],
  },
  methods: {
    editUser() {
      api.users
        .edit({
          id: this.user.id,
          email: this.user.email,
          password: this.user.password,
          username: this.user.username,
          role: this.user.role,
        })
        .then(() => this.$emit("refresh"));
    },
    deleteUser() {
      api.users
        .delete({
          id: this.user.id,
          email: this.user.email,
          password: this.user.password,
          username: this.user.username,
          role: this.user.role,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
