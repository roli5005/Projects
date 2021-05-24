import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allConsultations() {
    return HTTP.get(BASE_URL + "/consultations", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allConsultationsDoctor() {
    return HTTP.get(BASE_URL + "/consultations/descriptions", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(consult) {
    return HTTP.delete(BASE_URL + "/consultations/" + consult.id, {
      headers: authHeader(),
    }).then();
  },
  edit(consult) {
    return HTTP.patch(BASE_URL + "/consultations", consult, {
      headers: authHeader(),
    }).then();
  },
  updateDescription(consult){
    return HTTP.patch(BASE_URL + "/consultations/descriptions", consult, {
      headers: authHeader(),
    }).then();
  },
  create(consult) {
    return HTTP.post(BASE_URL + "/consultations", consult, {
      headers: authHeader(),
    }).then();
  },
};
