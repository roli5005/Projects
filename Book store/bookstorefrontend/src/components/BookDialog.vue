<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Edit book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field v-model="book.price" label="Price" />
          <v-text-field v-model="book.quantity" label="Quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="editBook"> {{ "Save" }} </v-btn>
          <v-btn @click="deleteBook"> {{ "Delete" }} </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    editBook() {
      api.books
        .edit({
          id: this.book.id,
          title: this.book.title,
          author: this.book.author,
          genre: this.book.genre,
          price: this.book.price,
          quantity: this.book.quantity,
        })
        .then(() => this.$emit("refresh"));
    },
    deleteBook() {
      api.books
        .delete({
          id: this.book.id,
          title: this.book.title,
          author: this.book.author,
          genre: this.book.genre,
          price: this.book.price,
          quantity: this.book.quantity,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
