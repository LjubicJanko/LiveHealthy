<template>
  <v-content id="admin">
    <v-container v-if="!ruleCreated">
      <v-card>
        <v-card-title>Define the rule</v-card-title>
        <v-card-text>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-select
              v-model="createBodyTypeRuleDto.shoulders"
              :items="shoulders"
              label="Shoulders"
              :rules="[v => !!v || 'Shoulders information is required']"
              required
            ></v-select>
            <v-select
              v-model="createBodyTypeRuleDto.forearms"
              :items="forearms"
              label="Forearms"
              :rules="[v => !!v || 'Forearms information is required']"
              required
            ></v-select>
            <v-select
              v-model="createBodyTypeRuleDto.bodyTendations"
              :items="bodyTendations"
              label="Body tendations"
              :rules="[v => !!v || 'Body tendation is required']"
              required
            ></v-select>
            <v-select
              v-model="createBodyTypeRuleDto.bodyLook"
              :items="bodyLook"
              label="Body look"
              :rules="[v => !!v || 'Body look is required']"
              required
            ></v-select>
            <v-select
              v-model="createBodyTypeRuleDto.weightTendations"
              :items="weightTendations"
              label="Weight tendations"
              :rules="[v => !!v || 'Weight tendation is required']"
              required
            ></v-select>

            <v-spacer></v-spacer>
            <v-radio-group mandatory row v-model="createBodyTypeRuleDto.bodyType">
              <v-radio label="ECTOMORPH" value="ECTOMORPH"></v-radio>
              <v-radio label="MESOMORPH" value="MESOMORPH"></v-radio>
              <v-radio label="ENDOMORPH" value="ENDOMORPH"></v-radio>
            </v-radio-group>
            <v-btn class="mr-4" @click="submit">Create rule</v-btn>
            <v-btn @click="clear">clear</v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-container>

    <show-rule v-else :rule="rule"></show-rule>
  </v-content>
</template>

<script>
// import store from "@/store";
import DynamicService from "../api-services/dynamic.service.js";
import ShowRule from "../components/ShowRule";

export default {
  name: "AdminPage",
  components: {
    ShowRule: ShowRule
  },
  data: () => ({
    ruleCreated: false,
    rule: "",
    select: null,
    valid: true,
    shoulders: [
      "Wider than my hips",
      "The same width as my hips",
      "Narrower than my hips"
    ],
    forearms: ["Big", "Average", "Small"],
    bodyTendations: [
      "Carry a bit of extra fat",
      "Stay lean, yet muscular",
      "Stay skinny"
    ],
    bodyLook: ["Round and soft", "Square and rugged", "Long and narrow"],
    weightTendations: [
      "Gain weight easily, but find it hard to lose",
      "I can gain and lose without too much of a struggle",
      "Have trouble gaining weight in the form of muscle or fat"
    ],
    checkbox: false,
    createBodyTypeRuleDto: {
      shoulders: "",
      forearms: "",
      bodyTendations: "",
      bodyLook: "",
      weightTendations: "",
      bodyType: ""
    },
    items: [
      {
        src: "src/resources/ecto.jpg"
      },
      {
        src: "../resources/meso.jpg"
      },
      {
        src: "../resources/endo.jpg"
      }
    ],
    selected: []
  }),
  computed: {},
  created() {
    this.ruleCreated = false;
  },
  methods: {
    goTo(url) {
      this.$router.push(url);
    },
    submit() {
      if (this.$refs.form.validate()) {
        console.log(this.createBodyTypeRuleDto);
        DynamicService.create(this.createBodyTypeRuleDto).then(response => {
          console.log(response);
          this.rule = response.data;
          this.ruleCreated = true;
        });
      }
    },
    clear() {
      this.$refs.form.reset();
      this.createBodyTypeRuleDto = {
        shoulders: "",
        forearms: "",
        bodyTendations: "",
        bodyLook: "",
        weightTendations: "",
        bodyType: ""
      };
    }
  }
};
</script>
<style>
</style>

