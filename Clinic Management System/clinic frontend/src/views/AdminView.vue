<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add User</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedItem"
      @refresh="refreshList"
    ></UserDialog>
    <CreateUserDialog
      :opened="createDialogVisible"
      :user="selectedItem"
      @refresh="refreshList"
    ></CreateUserDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import CreateUserDialog from "@/components/CreateUserDialog";
import UserDialog from "@/components/UserDialog";

export default {
  name: "AdminView",
  components: { CreateUserDialog, UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "Email", value: "email" },
        { text: "Role", value: "role" },
      ],
      dialogVisible: false,
      createDialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.createDialogVisible = false;
      this.selectedItem = {};
      this.users = await api.users.allUsers();
    },
    editUser(user) {
      this.selectedItem = user;
      this.dialogVisible = true;
    },
    addUser() {
      this.createDialogVisible = true;
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
