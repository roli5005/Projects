import Vue from "vue";
import VueRouter from "vue-router";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import AdminView from "@/views/AdminView";
import SecretaryViewPatients from "@/views/SecretaryViewPatients";
import SecretaryViewConsultations from "@/views/SecretaryViewConsultations";
import DoctorView from "@/views/DoctorView";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: AdminView,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "BooksCustomer" });
      }
    },
  },
  {
    path: "/patients",
    name: "Patients",
    component: SecretaryViewPatients,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/consultations",
    name: "Consultations",
    component: SecretaryViewConsultations,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/consultations/descriptions",
    name: "Descriptions",
    component: DoctorView,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
