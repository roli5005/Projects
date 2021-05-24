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
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="consultations"
      :search="search"
      @click:row="editDescription"
    ></v-data-table>
    <DescriptionDialog
      :opened="dialogVisible"
      :consultation="selectedItem"
      @refresh="refreshList"
    ></DescriptionDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import DescriptionDialog from "@/components/DescriptionDialog";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "DoctorView",
  components: { DescriptionDialog },
  data() {
    return {
      consultations: [],
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
    },
    subscribeToSocket() {
      this.socket = new SockJS("http://localhost:8090/message");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        { "Access-Control-Allow-Origin": "*" },
        () => {
          this.connected = true;
          this.stompClient.subscribe(
            "/api/consultations/descriptions/notify",
            (tick) => {
              let response = JSON.parse(tick.body);
              if (
                response.doctor_name === this.$store.state.auth.user.username
              ) {
                console.log(response.message);
              }
            },
            { "Access-Control-Allow-Origin": "*" }
          );
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    editDescription(consultation) {
      this.selectedItem = consultation;
      this.dialogVisible = true;
    },
  },
  created() {
    this.refreshList();
    this.subscribeToSocket();
  },
};
</script>

<style scoped></style>
