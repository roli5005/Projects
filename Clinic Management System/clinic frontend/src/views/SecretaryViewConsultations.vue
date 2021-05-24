<template>
  <v-card>
    <v-card-title>
      Consultation Details
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addConsultation">Add Consultation</v-btn>
      <v-btn @click="switchView">Show Patients</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="consultations"
      :search="search"
      @click:row="editConsultation"
    ></v-data-table>
    <CreateConsultationDialog
      :opened="dialogVisible"
      :consultation="selectedItem"
      :patients-name-list="patientsNameList"
      :doctors-name-list="doctorsNameList"
      :working-hours="workingHours"
      @refresh="refreshList"
    ></CreateConsultationDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import CreateConsultationDialog from "@/components/CreateConsultationDialog";
import router from "@/router";

export default {
  name: "SecretaryViewConsultations",
  components: { CreateConsultationDialog },
  data() {
    return {
      consultations: [],
      patientsNameList: [],
      doctorsNameList: [],
      workingHours: [],
      search: "",
      headers: [
        {
          text: "Patient name",
          align: "start",
          value: "patient_name",
        },
        {
          text: "Doctor name",
          value: "doctor_name",
        },
        {
          text: "Date",
          value: "date",
        },
        {
          text: "Time",
          value: "time",
        },
        {
          text: "Description",
          value: "description",
        },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.consultations = await api.consultations.allConsultations();
      this.patientsNameList = await api.patients.allPatientsName();
      this.doctorsNameList = await api.users.allDoctors();
      this.workingHours = ["8","9","10","11","12","13","14","15","16","17"];
    },
    addConsultation(consultation) {
      this.selectedItem = consultation;
      this.dialogVisible = true;
    },
    editConsultation(consultation){
      this.selectedItem = consultation;
      this.dialogVisible = true;
    },
    switchView() {
      router.push("/patients");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
