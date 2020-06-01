<template>
  <div>
    <div v-if="planExistis">
      <food-plan :nutritionPlan="nutritionPlan"></food-plan>
      <training-plan></training-plan>
    </div>
    <div v-else class="wrapper">
      <v-btn id="createPlanBtn" @click="createPlan()" x-large>
        <v-icon>mdi-food-fork-drink</v-icon>
        <v-divider class="mx-4" vertical></v-divider>
        <h2>Create nutrition and training plan</h2>
        <v-divider class="mx-4" vertical></v-divider>
        <v-icon>mdi-weight-lifter</v-icon>
      </v-btn>
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
        let createPlanInfoDto = {
          forbiddenFoodDto: {
            forbiddenFood: ["Cheese- brie"]
          },
          illnessesDto: {
            illnesses: ["HEART_DISEASE"]
          }
        };

        if (this.user.bodyType != null) {
          PlanService.create(store.state.userId, createPlanInfoDto).then(response => {
            console.log(response.data);
            this.planExistis = true;
          });
        } else {
          this.showSnackbar("You can not create plan before determinating body type!", "error");
          this.$router.push("/body-type");
        }
      }
    },
    getUserPlan() {
      UserService.getUser(this.$route.params.userId).then(response => {
        this.user = response.data;
        if(this.user.nutritionPlan == null) {
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
    },
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