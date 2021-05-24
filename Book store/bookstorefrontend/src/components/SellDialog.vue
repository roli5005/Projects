<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Buy Book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field v-model="book.price" label="Price" />
          <v-text-field v-model="book.quantity" label="Quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="buy">
            {{ "Buy" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "SellDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    buy() {
      if (this.book.quantity !== 0) {
        api.books
          .edit({
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            price: this.book.price,
            quantity: this.book.quantity - 1,
          })
          .then(() => this.$emit("refresh"));
      } else this.$emit("refresh");
    },
  },
};
</script>

<style scoped></style>
