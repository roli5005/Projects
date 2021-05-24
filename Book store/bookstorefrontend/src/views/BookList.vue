<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-btn @click="exportPDF">Export PDF</v-btn>
      <v-btn @click="exportCSV">Export CSV</v-btn>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addBook">Add Book</v-btn>
      <v-btn @click="showUsers">Show Users</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="editBook"
    ></v-data-table>
    <CreateBookDialog
    :opened="createDialogVisible"
    :book="selectedItem"
    @refresh="refreshList"
    ></CreateBookDialog>
    <BookDialog
      :opened="dialogVisible"
      :book="selectedItem"
      @refresh="refreshList"
    ></BookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "@/components/BookDialog";
import CreateBookDialog from "@/components/CreateBookDialog";
import router from "@/router";

export default {
  name: "BookList",
  components: {BookDialog,CreateBookDialog },
  data() {
    return {
      books: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "author", value: "author" },
        { text: "genre", value: "genre" },
        { text: "price", value: "price" },
        { text: "quantity", value: "quantity" },
      ],
      dialogVisible: false,
      createDialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editBook(book) {
      this.selectedItem = book;
      this.dialogVisible = true;
    },
    addBook() {
      this.createDialogVisible = true;
    },
    async exportPDF() {
      await api.books.generateReport("PDF");
    },
    async exportCSV() {
      await api.books.generateReport("CSV");
    },
    showUsers(){
      router.push("/users");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.createDialogVisible = false;
      this.selectedItem = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
