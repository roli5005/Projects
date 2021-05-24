<template>
  <v-card>
    <v-card-title>
      Patients
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addPatient">Add Patient</v-btn>
      <v-btn @click="switchView">Show Consultations</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="patients"
      :search="search"
      @click:row="editPatient"
    ></v-data-table>
    <PatientDialog
      :opened="dialogVisible"
      :patient="selectedItem"
      @refresh="refreshList"
    ></PatientDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import PatientDialog from "@/components/PatientDialog";
import router from "@/router";

export default {
  name: "SecretaryViewPatents",
  components: { PatientDialog },
  data() {
    return {
      patients: [],
      search: "",
      headers: [
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
      this.patients = await api.patients.allPatients();
    },
    editPatient(patient) {
      this.selectedItem = patient;
      this.dialogVisible = true;
    },
    addPatient(patient) {
      this.selectedItem = patient;
      this.dialogVisible = true;
    },
    switchView() {
      router.push("/consultations");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
