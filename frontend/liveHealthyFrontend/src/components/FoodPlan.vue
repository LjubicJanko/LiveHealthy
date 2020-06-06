<template>
  <v-card class="mx-10 px-5 py-5 mt-12 text-center">
    <v-card-title class="justify-center">Nutrition plan for {{nutritionPlan.goal}}</v-card-title>
    <v-expansion-panels popout hover v-if="typeof planLocal != undefined">
      <v-expansion-panel
        v-for="(day,i) in planLocal.weeklyPlan"
        :key="day.dayOfTheWeek"
        :style="[checkIfSubmitIsAvailable(day) ? {'background': 'blue'} : {'background': '#FFF'}]"
      >
        <v-expansion-panel-header>Day {{day.dayOfTheWeek+1}}</v-expansion-panel-header>
        <v-expansion-panel-content>
          <div v-if="day.intakeSubmitType == 'REWARDED_NOT_NEEDED'">
            <h1
              style="color: green;"
            >THIS DAY IS YOUR REWARD DAY, YOU DO NOT HAVE TO FOLLOW THE PLAN TODAY.</h1>
          </div>
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

          <div v-if="checkIfSubmitIsAvailable(day)">
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
                    <v-btn color="blue darken-1" text @click="intakeByPlan(i)">Submit</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-speed-dial>
          </div>
          <div v-if="day.intakeSubmitType == 'REWARDED_NOT_NEEDED'">
            <h1
              style="color: green;"
            >THIS DAY IS YOUR REWARD DAY, YOU DO NOT HAVE TO FOLLOW THE PLAN TODAY.</h1>
          </div>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>

    <v-dialog v-model="loading" fullscreen full-width>
      <v-container fluid fill-height style="background-color: rgba(255, 255, 255, 0.5);">
        <v-layout justify-center align-center>
          <v-progress-circular size="100" width="15" indeterminate color="primary"></v-progress-circular>
        </v-layout>
      </v-container>
    </v-dialog>

    <v-snackbar v-model="snackbar.show" :timeout="5000" :color="snackbar.color" :top="true">
      {{snackbar.msg}}
      <v-btn dark @click="snackbar.show = false">Close</v-btn>
    </v-snackbar>
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
      prop: "nutritionPlan",
      event: "planChange",
      fab: false,
      dialog: false,
      caloriesDifference: 0,
      loading: false,
      snackbar: {
        show: false,
        color: "",
        msg: ""
      }
    };
  },
  computed: {
    planLocal: {
      get: function() {
        return this.nutritionPlan;
      },
      set: function(value) {
        this.$emit("planChange", value);
      }
    }
  },
  created() {
    if (this.planLocal != undefined) {
      var nutritionCopy = JSON.parse(JSON.stringify(this.nutritionPlan));
      nutritionCopy.weeklyPlan.sort(function(a, b) {
        return a.dayOfTheWeek - b.dayOfTheWeek;
      });
      this.planLocal = nutritionCopy;
    }
  },
  methods: {
    checkIfSubmitIsAvailable(day) {
      let available = true;
      this.planLocal.weeklyPlan.forEach(d => {
        if (d.dayOfTheWeek < day.dayOfTheWeek && d.intakeSubmitType == "NONE") {
          available = false; // submit is disabled for this day
        }
      });
      if (day.intakeSubmitType != "NONE") {
        available = false;
      }
      return available;
    },
    check() {
      console.log(this.nutritionPlan);
      console.log(this.nutritionPlan.weeklyPlan);
    },
    intakeByPlan(i) {
      this.dialog = false;
      this.loading = true;
      let userId = store.state.userId;
      let submitDto = {
        dayIndex: i,
        caloriesDifference: this.caloriesDifference
      };

      IntakeService.submit(userId, submitDto).then(response => {
        console.log(response);
        let nutPlan = response.data;
        console.log(nutPlan.weeklyPlan);
        this.nutritionPlan = nutPlan;
        location.reload();
        this.showSnackbar(
          "Successfully submited intake for day with index " + i,
          "success"
        );
        this.caloriesDifference = 0;
      });
    },
    // intakeNotByPlan(i) {
    //   this.dialog = false;
    //   let submitDto = {
    //     dayIndex: i,
    //     caloriesDifference: this.caloriesDifference
    //   };
    //   IntakeService.submit(store.state.userId, submitDto);
    // },
    increment() {
      this.caloriesDifference = parseInt(this.caloriesDifference, 10) + 50;
    },
    decrement() {
      this.caloriesDifference = parseInt(this.caloriesDifference, 10) - 50;
    },
    showSnackbar(message, color) {
      this.snackbar.color = color;
      this.snackbar.msg = message;
      this.snackbar.show = true;
    }
  }
};
</script>