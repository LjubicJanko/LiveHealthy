<template>
  <div>
    <div v-if="planExistis">
      <food-plan :nutritionPlan="nutritionPlan"></food-plan>
      <training-plan></training-plan>
    </div>
    <div v-else class="wrapper">
      <v-dialog v-model="dialog" persistent max-width="290">
        <template v-slot:activator="{ on }">
          <!-- <v-btn color="primary" dark v-on="on">Open Dialog</v-btn> -->
          <v-btn id="createPlanBtn" x-large v-on="on">
            <v-icon>mdi-food-fork-drink</v-icon>
            <v-divider class="mx-4" vertical></v-divider>
            <h2>Create nutrition and training plan</h2>
            <v-divider class="mx-4" vertical></v-divider>
            <v-icon>mdi-weight-lifter</v-icon>
          </v-btn>
        </template>
        <v-card>
          <v-card-title class="headline">Any decease we need to know about?</v-card-title>
          <v-card-text>
            <v-checkbox v-model="illnesses" label="HEADACHES" value="HEADACHES"></v-checkbox>
            <v-checkbox v-model="illnesses" label="HEART DISEASE" value="HEART_DISEASE"></v-checkbox>
            <v-checkbox v-model="illnesses" label="ARM INJURY" value="ARM_INJURY"></v-checkbox>
            <v-checkbox v-model="illnesses" label="LEG INJURY" value="LEG_INJURY"></v-checkbox>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="dialog = false">Disagree</v-btn>
            <v-btn color="green darken-1" text @click="createPlan()">Create</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
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
import TrainingPlan from "../components/TrainingPlan.vue";
import store from "@/store";

export default {
  name: "PlanOverview",
  components: {
    FoodPlan: FoodPlan,
    TrainingPlan: TrainingPlan
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
        this.dialog = false;
        this.loading = true;
        let createPlanInfoDto = {
          forbiddenFoodDto: {
            forbiddenFood: ["Cheese- brie"]
          },
          illnessesDto: {
            illnesses: this.illnesses
            // illnesses: ["HEART_DISEASE"]
          }
        };

        if (this.user.bodyType != null) {
          PlanService.create(store.state.userId, createPlanInfoDto).then(
            response => {
              console.log(response.data);
              this.planExistis = true;
              this.loading = false;
              window.location.reload();
            }
          );
        } else {
          this.loading = false;
          this.showSnackbar(
            "You can not create plan before determinating body type!",
            "error"
          );
          this.$router.push("/body-type");
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