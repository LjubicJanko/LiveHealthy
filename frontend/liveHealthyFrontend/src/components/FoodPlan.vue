<template>
  <v-card class="mx-10 px-5 py-5 mt-12 text-center">
    <v-card-title>Nutrition plan for {{nutritionPlan.goal}}</v-card-title>
    <v-expansion-panels popout hover>
      <v-expansion-panel v-for="(day,i) in nutritionPlan.weeklyPlan" :key="i">
        <v-expansion-panel-header>Day {{i+1}}</v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-card-title>Calories goal for the day: {{nutritionPlan.caloriesGoal}}</v-card-title>
          <v-card-title>Meals</v-card-title>
          <v-card v-for="(meal, j) in day.dailyFood" :key="j">
              Food group: <h3>{{meal.foodGroup}}</h3><br/>
              Description: <h1>{{meal.description}}</h1><br/>
              Kcal: <h3>{{meal.energy_kcal}}</h3><br/>
              Proteins: <h4>{{meal.proteins}}</h4>
              Fats: <h4>{{meal.fats}}</h4>
              Carbs: <h4>{{meal.carbs}}</h4>
          </v-card>
          <v-card-title>Snacks</v-card-title>
          <v-card v-for="(snack, j) in day.snacks" :key="j">
              {{snack}}
          </v-card>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-card>
</template>

<script>
export default {
  name: "FoodPlan",
  props: ['nutritionPlan'],
  data() {
    return {};
  },
  methods: {
    check() {
      console.log(this.nutritionPlan);
    }

  }
};
</script>