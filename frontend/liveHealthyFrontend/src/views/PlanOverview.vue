<template>
  <div>
    <div v-if="planExistis">
      <food-plan></food-plan>
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
  </div>
</template>

<script>
import UserService from "../api-services/user.service";

import FoodPlan from "../components/FoodPlan.vue";
import TrainingPlan from "../components/TrainingPlan.vue";
import store from '@/store';

export default {
  name: "PlanOverview",
  components: {
    FoodPlan: FoodPlan,
    TrainingPlan: TrainingPlan
  },
  data: () => ({
    id: 0,
    planExistis: false,
    user: {}
  }),
  beforeMount() {
    this.getUserPlan();
  },
  methods: {
    createPlan() {
        if(store.state.userLoggedIn){
            if(this.user.bodyType != null){
                alert("moze odma plan")
            } else{
                alert("moras da odredis tip tela");
            }
        }
    },
    getUserPlan() {
        UserService.getUser(this.$route.params.userId).then((response) => {
            this.user = response.data;
        });
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