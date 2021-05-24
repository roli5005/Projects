<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Edit Patient" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="patient.name" label="Name" />
          <v-text-field v-model="patient.cardNumber" label="Card number" />
          <v-text-field v-model="patient.dateOfBirth" label="Date of birth" />
          <v-text-field v-model="patient.personalCode" label="Personal code" />
          <v-text-field v-model="patient.address" label="Address" />
        </v-form>
        <v-card-actions>
          <v-btn @click="editPatient">
            {{ "Save" }}
          </v-btn>
          <v-btn @click="addPatient">
            {{ "Add" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";

export default {
  name: "PatientDialog",
  props: {
    patient: Object,
    opened: Boolean,
    dialogVisible: Boolean,
    message: String,
    patientHeader: [
      {
        text: "Name",
        align: "start",
        value: "name",
      },
      {
        text: "Card Number",
        value: "cardNumber",
      },
      {
        text: "Date of birth",
        value: "dateOfBirth",
      },
      {
        text: "Personal code",
        value: "personalCode",
      },
      {
        text: "Address",
        value: "address",
      },
    ],
  },
  methods: {
    editPatient() {api.patients
        .edit({
          id: this.patient.id,
          name: this.patient.name,
          address: this.patient.address,
          personalCode: this.patient.personalCode,
          dateOfBirth: this.patient.dateOfBirth,
          cardNumber: this.patient.cardNumber,
        })
        .then(() => this.$emit("refresh"));
    },
    addPatient() {
      api.patients
        .create({
          name: this.patient.name,
          address: this.patient.address,
          personalCode: this.patient.personalCode,
          dateOfBirth: this.patient.dateOfBirth,
          cardNumber: this.patient.cardNumber,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
