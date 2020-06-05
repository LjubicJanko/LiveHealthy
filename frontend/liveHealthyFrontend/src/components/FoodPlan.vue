<template>
  <v-card class="mx-10 px-5 py-5 mt-12 text-center">
    <v-card-title class="justify-center">Nutrition plan for {{nutritionPlan.goal}}</v-card-title>
    <v-expansion-panels popout hover>
      <v-expansion-panel v-for="(day,i) in nutritionPlan.weeklyPlan" :key="i">
        <v-expansion-panel-header>Day {{i+1}}</v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-card-title>Calories goal for the day: {{nutritionPlan.caloriesGoal}}</v-card-title>
          <v-card-title>Meals</v-card-title>
          <v-card v-for="(meal, j) in day.dailyFood" :key="j">
            Food group:
            <h3>{{meal.foodGroup}}</h3>
            <br />Description:
            <h1>{{meal.description}}</h1>
            <br />Kcal:
            <h3>{{meal.energy_kcal}}</h3>
            <br />Proteins:
            <h4>{{meal.proteins}}</h4>Fats:
            <h4>{{meal.fats}}</h4>Carbs:
            <h4>{{meal.carbs}}</h4>
          </v-card>
          <v-card-title v-if="day.snacks.length > 0">Snacks</v-card-title>
          <v-card v-for="(snack, j) in day.snacks" :key="j">{{snack}}</v-card>

          <v-card-title>Submit information about your daily intake</v-card-title>
          <v-speed-dial
            v-model="fab"
            bottom
            right
            direction="left"
            transition="slide-y-reverse-transition"
            style="position: absolute;"
          >
            <template v-slot:activator>
              <v-btn v-model="fab" color="blue darken-2" dark fab>
                <v-icon v-if="fab">mdi-arrow-collapse-right</v-icon>
                <v-icon v-else>mdi-nutrition</v-icon>
              </v-btn>
            </template>
            <v-tooltip top>
              <template v-slot:activator="{ on }">
                <v-btn fab dark small color="indigo" v-on="on" @click="intakeByPlan(i)">
                  <v-icon>mdi-checkbox-marked-circle</v-icon>
                </v-btn>
              </template>
              <span>I followed the plan.</span>
            </v-tooltip>

            <v-dialog v-model="dialog" persistent max-width="600px">
              <template v-slot:activator="{ on }">
                <v-btn fab dark small color="red" v-on="on">
                  <v-icon>mdi-close-circle</v-icon>
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">Number of calories by which you missed limit</span>
                </v-card-title>
                <v-card-subtitle>+-50 calorie difference</v-card-subtitle>
                <v-card-text>
                  <v-row cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="caloriesDifference"
                      type="number"
                      label="Number"
                      append-outer-icon="mdi-plus"
                      @click:append-outer="increment"
                      prepend-icon="mdi-minus"
                      @click:prepend="decrement"
                    ></v-text-field>
                  </v-row>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
                  <v-btn color="blue darken-1" text @click="intakeNotByPlan(i)">Submit</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-speed-dial>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-card>
</template>

<script>
import IntakeService from "../api-services/intake.service";
import store from "@/store";

export default {
  name: "FoodPlan",
  props: ["nutritionPlan"],
  data() {
    return {
      fab: false,
      dialog: false,
      caloriesDifference: 0
    };
  },
  methods: {
    check() {
      console.log(this.nutritionPlan);
      console.log(this.nutritionPlan.weeklyPlan);
    },
    intakeByPlan(i) {
      let userId = store.state.userId;
      let submitDto = {
        dayIndex: i,
        caloriesDifference: 0
      };

      IntakeService.submit(userId, submitDto);
    },
    intakeNotByPlan(i) {
      this.dialog = false;
      let submitDto = {
        dayIndex: i,
        caloriesDifference: this.caloriesDifference
      };
      IntakeService.submit(store.state.userId, submitDto);
    },
    increment() {
      this.caloriesDifference = parseInt(this.caloriesDifference, 10) + 50;
    },
    decrement() {
      this.caloriesDifference = parseInt(this.caloriesDifference, 10) - 50;
    }
  }
};
</script>