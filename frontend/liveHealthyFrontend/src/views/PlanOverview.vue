<template>
  <div>
    <div v-if="planExistis">
      <food-plan :nutritionPlan="nutritionPlan"></food-plan>
    </div>

    <div v-else class="wrapper">
      <v-btn id="createPlanBtn" x-large @click="createPlan()">
        <v-icon>mdi-food-fork-drink</v-icon>
        <v-divider class="mx-4" vertical></v-divider>
        <h2>Create nutrition plan</h2>
        <v-divider class="mx-4" vertical></v-divider>
        <v-icon>mdi-food-fork-drink</v-icon>
      </v-btn>

      <v-dialog v-model="loading" fullscreen full-width>
        <v-container fluid fill-height style="background-color: rgba(255, 255, 255, 0.5);">
          <v-layout justify-center align-center>
            <v-progress-circular size="100" width="15" indeterminate color="primary"></v-progress-circular>
          </v-layout>
        </v-container>
      </v-dialog>
    </div>

    <v-snackbar v-model="snackbar.show" :timeout="5000" :color="snackbar.color" :top="true">
      {{snackbar.msg}}
      <v-btn dark @click="snackbar.show = false">Close</v-btn>
    </v-snackbar>
  </div>
</template>

<script>
import UserService from "../api-services/user.service";
import PlanService from "../api-services/plan.service";

import FoodPlan from "../components/FoodPlan.vue";
import store from "@/store";

export default {
  name: "PlanOverview",
  components: {
    FoodPlan: FoodPlan
  },
  data: () => ({
    id: 0,
    planExistis: false,
    user: {},
    nutritionPlan: {},
    dialog: false,
    illnesses: [],
    loading: false,
    snackbar: {
      show: false,
      color: "",
      msg: ""
    }
  }),
  beforeMount() {
    this.getUserPlan();
  },
  methods: {
    createPlan() {
      if (store.state.userLoggedIn) {
        this.loading = true;
        if (this.user.bodyType != null) {
          PlanService.create(store.state.userId).then(response => {
            this.nutritionPlan = response.data;
            console.log(this.nutritionPlan);
            this.planExistis = true;
            this.loading = false;
          });
        } else {
          this.loading = false;
          this.$router.push("/body-type");
          this.showSnackbar(
            "You can not create plan before determinating body type!",
            "error"
          );
        }
      }
    },
    getUserPlan() {
      UserService.getUser(this.$route.params.userId).then(response => {
        this.user = response.data;
        if (this.user.nutritionPlan == null) {
          this.planExistis = false;
        } else {
          this.planExistis = true;
          this.nutritionPlan = this.user.nutritionPlan;
        }
        console.log(this.user);
      });
    },
    showSnackbar(message, color) {
      this.snackbar.color = color;
      this.snackbar.msg = message;
      this.snackbar.show = true;
    }
  },
  created() {}
};
</script>
<style>
.wrapper {
  text-align: center;
}

#createPlanBtn {
  text-align: center;
  position: relative;
  top: 50%;
  background-color: white;
  color: black;
  border: 2px solid black;
  /* padding: 32px 32px; */
}
</style>