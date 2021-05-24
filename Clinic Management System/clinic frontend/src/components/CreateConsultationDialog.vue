<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Consultation" }}
        </v-toolbar>
        <v-form>
          <v-autocomplete
            v-model="consultation.patient_name"
            label="Patient"
            solo
            :items="patientsNameList"
          ></v-autocomplete>
          <v-autocomplete
            v-model="consultation.doctor_name"
            label="Doctor"
            solo
            :items="doctorsNameList"
          ></v-autocomplete>
          <v-text-field v-model="consultation.date" label="Date" />
          <v-autocomplete
            v-model="consultation.time"
            label="Time"
            solo
            :items="workingHours"
          ></v-autocomplete>
        </v-form>
        <v-card-actions>
          <v-btn @click="createConsultation"> {{ "Add" }} </v-btn>
          <v-btn @click="editConsultation"> {{ "Save" }} </v-btn>
          <v-btn @click="sendCheckIn">{{ "Check In" }}</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "CreateConsultationDialog",
  props: {
    consultation: Object,
    opened: Boolean,
    doctorsNameList: Array,
    patientsNameList: Array,
    workingHours: Array,
  },
  methods: {
    createConsultation() {
      api.consultations
        .create({
          patient_name: this.consultation.patient_name,
          doctor_name: this.consultation.doctor_name,
          date: this.consultation.date,
          time: this.consultation.time,
          description: "",
        })
        .then(() => this.$emit("refresh"));
    },
    editConsultation() {
      api.consultations
        .edit({
          id: this.consultation.id,
          patient_name: this.consultation.patient_name,
          doctor_name: this.consultation.doctor_name,
          date: this.consultation.date,
          time: this.consultation.time,
          description: "",
        })
        .then(() => this.$emit("refresh"));
    },
    sendCheckIn() {
      api.websocket.sendCheckIn(
        this.consultation.patient_name,
        this.consultation.date,
        this.consultation.time,
        this.consultation.doctor_name
      );
    },
  },
  created() {
    this.connected = api.websocket.connect();
  },
};
</script>

<style scoped></style>
