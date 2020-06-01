<template>
  <div>
    <v-stepper v-model="e1" alt-labels>
      <v-stepper-header>
        <v-stepper-step :complete="e1 > 1" step="1">SEX</v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="e1 > 2" step="2">SHOULDERS</v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="e1 > 3" step="3">FOREARMS</v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="e1 > 4" step="4">BODY TENDATIONS</v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="e1 > 5" step="5">BODY LOOK</v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="e1 > 6" step="6">CONCERNING MY WEIGHT, I</v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1" class="text-xs-center">
          <v-layout row wrap>
            <v-flex xs6 offset-xs3>
              <v-card class="mb-12 black--text" height="200px">
                <v-card-title primary-title class="layout justify-center">
                  <h1>I AM</h1>
                </v-card-title>
                <v-card-text class="layout justify-center">
                  <v-radio-group v-model="sex" row>
                    <v-radio label="Male" value="true" color="black"></v-radio>
                    <v-radio label="Female" value="false" color="black"></v-radio>
                  </v-radio-group>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>
          <v-btn color="primary" @click="e1 = 2">Continue</v-btn>
        </v-stepper-content>

        <v-stepper-content step="2">
          <v-layout row wrap>
            <v-flex xs6 offset-xs3>
              <v-card class="mb-12" height="200px">
                <v-card-title primary-title class="layout justify-center">
                  <h1>MY SHOULDERS ARE</h1>
                </v-card-title>

                <v-card-text class="layout justify-center">
                  <v-radio-group v-model="shoulders">
                    <v-radio label="Wider than my hips" value="Wider than my hips" color="black"></v-radio>
                    <v-radio
                      label="The same width as my hips"
                      value="The same width as my hips"
                      color="black"
                    ></v-radio>
                    <v-radio
                      label="Narrower than my hips"
                      value="Narrower than my hips"
                      color="black"
                    ></v-radio>
                  </v-radio-group>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>

          <v-btn color="primary" @click="e1 = 3">Continue</v-btn>
          <v-btn @click="e1 = 1">Back</v-btn>
        </v-stepper-content>

        <v-stepper-content step="3">
          <v-layout row wrap>
            <v-flex xs6 offset-xs3>
              <v-card class="mb-12" height="200px">
                <v-card-title primary-title class="layout justify-center">
                  <h1>MY FOREARMS LOOK</h1>
                </v-card-title>

                <v-card-text class="layout justify-center">
                  <v-radio-group v-model="forearms">
                    <v-radio label="Big" value="Big" color="black"></v-radio>
                    <v-radio label="Average" value="Average" color="black"></v-radio>
                    <v-radio label="Small" value="Small" color="black"></v-radio>
                  </v-radio-group>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>

          <v-btn color="primary" @click="e1 = 4">Continue</v-btn>
          <v-btn @click="e1 = 2">Back</v-btn>
        </v-stepper-content>
        <v-stepper-content step="4">
          <v-layout row wrap>
            <v-flex xs6 offset-xs3>
              <v-card class="mb-12" height="200px">
                <v-card-title primary-title class="layout justify-center">
                  <h1>MY BODY TENDS TO</h1>
                </v-card-title>

                <v-card-text class="layout justify-center">
                  <v-radio-group v-model="bodyTendations">
                    <v-radio
                      label="Carry a bit of extra fat"
                      value="Carry a bit of extra fat"
                      color="black"
                    ></v-radio>
                    <v-radio
                      label="Stay lean, yet muscular"
                      value="Stay lean, yet muscular"
                      color="black"
                    ></v-radio>
                    <v-radio label="Stay skinny" value="Stay skinny" color="black"></v-radio>
                  </v-radio-group>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>

          <v-btn color="primary" @click="e1 = 5">Continue</v-btn>
          <v-btn @click="e1 = 3">Back</v-btn>
        </v-stepper-content>
        <v-stepper-content step="5">
          <v-layout row wrap>
            <v-flex xs6 offset-xs3>
              <v-card class="mb-12" height="200px">
                <v-card-title primary-title class="layout justify-center">
                  <h1>MY BODY LOOKS</h1>
                </v-card-title>

                <v-card-text class="layout justify-center">
                  <v-radio-group v-model="bodyLook">
                    <v-radio label="Round and soft" value="Round and soft" color="black"></v-radio>
                    <v-radio label="Square and rugged" value="Square and rugged" color="black"></v-radio>
                    <v-radio label="Long and narrow" value="Long and narrow" color="black"></v-radio>
                  </v-radio-group>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>

          <v-btn color="primary" @click="e1 = 6">Continue</v-btn>
          <v-btn @click="e1 = 2">Back</v-btn>
        </v-stepper-content>
        <v-stepper-content step="6">
          <v-layout row wrap>
            <v-flex xs6 offset-xs3>
              <v-card class="mb-12" height="200px">
                <v-card-title primary-title class="layout justify-center">
                  <h1>CONCERNING MY WEIGHT, I</h1>
                </v-card-title>

                <v-card-text class="layout justify-center">
                  <v-radio-group v-model="weightTendations">
                    <v-radio
                      label="Gain weight easily, but find it hard to lose"
                      value="Gain weight easily, but find it hard to lose"
                      color="black"
                    ></v-radio>
                    <v-radio
                      label="I can gain and lose without too much of a struggle"
                      value="I can gain and lose without too much of a struggle"
                      color="black"
                    ></v-radio>
                    <v-radio
                      label="Have trouble gaining weight in the form of muscle or fat"
                      value="Have trouble gaining weight in the form of muscle or fat"
                      color="black"
                    ></v-radio>
                  </v-radio-group>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>

          <v-btn color="primary" @click="determine()">Finish</v-btn>
          <v-btn @click="e1 = 5">Back</v-btn>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
    <v-dialog v-model="popupInfo" justify="center">
      <v-card v-if="bodyType != null">
        <v-card-title class="headline" justify="center">
          <span class="headline">We found your body type</span>
        </v-card-title>
        <v-card-text>
          <v-row justify="space-around">
            <v-col cols="5" v-if="sex">
              <v-img
                v-if="bodyType.type == 'endomorph'"
                src="../resources/male_endomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'mesomorph'"
                src="../resources/male_mesomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'ectomorph'"
                src="../resources/male_ectomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'ECTOMORPH_MESOMORPH'"
                src="../resources/male_ectomorph-mesomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'ENDOMORPH_MESOMORPH'"
                src="../resources/male_mesomorph-endomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'nonexistent'"
                src="../resources/confused.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
            </v-col>
            <v-col cols="5" v-else>
              <v-img
                v-if="bodyType.type == 'endomorph'"
                src="../resources/female_endomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'mesomorph'"
                src="../resources/female_mesomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'ectomorph'"
                src="../resources/female_ectomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'ECTOMORPH_MESOMORPH'"
                src="../resources/male_ectomorph-mesomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'ENDOMORPH_MESOMORPH'"
                src="../resources/male_mesomorph-endomorph.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
              <v-img
                v-if="bodyType.type == 'nonexistent'"
                src="../resources/confused.jpg"
                aspect-ratio="1.7"
                contain
              ></v-img>
            </v-col>
            <v-col cols="5"><span style="font-size: 25px; white-space: pre-wrap;">{{bodyType.description}}</span></v-col>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="popupInfo = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
import BasicDeterminationService from "../api-services/basic.determination.service";
import store from '@/store';

export default {
  data() {
    return {
      sex: "true",
      shoulders: "Wider than my hips",
      forearms: "Big",
      bodyTendations: "Carry a bit of extra fat",
      bodyLook: "Round and soft",
      weightTendations: "Gain weight easily, but find it hard to lose",
      ex11: "orange",
      e1: 1,

      bodyType: {},
      popupInfo: false
    };
  },
  methods: {
    determine() {
      let bodyInfo = {
        sex: this.sex,
        shoulders: this.shoulders,
        forearms: this.forearms,
        bodyTendations: this.bodyTendations,
        bodyLook: this.bodyLook,
        weightTendations: this.weightTendations
      };
      let userId = store.state.userId;
      console.log(store.state.userId);
      console.log(bodyInfo);
      BasicDeterminationService.determine(bodyInfo, userId)
        .then(response => {
          this.bodyType = response.data;
          this.popupInfo = true;
        })
        .catch(response => {
          alert(response);
        });
    }
  }
};
</script>