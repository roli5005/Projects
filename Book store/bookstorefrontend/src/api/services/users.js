import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  delete(user){
    return HTTP.delete(BASE_URL + "/users/" + user.id, {
      headers: authHeader(),
    }).then();
  },
  edit(user) {
    return HTTP.patch(BASE_URL + "/users", user, {
      headers: authHeader(),
    }).then();
  },
  create(user) {
    return HTTP.post(BASE_URL + "/users", user, {
      headers: authHeader(),
    }).then();
  },
};
