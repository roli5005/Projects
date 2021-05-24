<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Create User" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.password" label="Password" />
          <v-text-field v-model="user.username" label="Name" />
          <v-text-field v-model="user.role" label="Role" />
        </v-form>
        <v-card-actions>
          <v-btn @click="createUser">
            {{ "Create" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "CreateUserDialog",
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
    createUser() {
      api.users
        .create({
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
