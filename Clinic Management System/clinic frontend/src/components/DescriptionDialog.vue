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
          <span>{{consultation.patient_name}}</span>
          <br>
          <span>{{consultation.doctor_name}}</span>
          <br>
          <span>{{consultation.date}}</span>
          <br>
          <span>{{consultation.time}}</span>
          <v-text-field v-model="consultation.description" label="Description" />
        </v-form>
        <v-card-actions>
          <v-btn @click="editDescription">
            {{ "Save" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";

export default {
  name: "DescriptionDialog",
  props: {
    consultation: Object,
    opened: Boolean,
    userHeader: [
      {
        text: "Description",
        align: "start",
        value: "description",
      },
    ],
  },
  methods: {
    editDescription() {
      api.consultations
        .updateDescription({
          id: this.consultation.id,
          description: this.consultation.description,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
