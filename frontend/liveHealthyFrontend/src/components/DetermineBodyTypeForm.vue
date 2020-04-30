<template>
  <v-card class="mx-10 px-5 py-5 mt-12 text-center">
    <v-form>
      <v-container>
        <v-row>
          <v-col>
            <v-card-title>Your body weight in kg</v-card-title>
            <v-text-field
              v-model="bodyWeight"
              hide-details
              single-line
              type="number"
              label="Body weight (kg)"
            ></v-text-field>
            <br />
            <v-card-title>Your body height in cm</v-card-title>
            <v-text-field
              v-model="bodyHeight"
              hide-details
              single-line
              type="number"
              label="Body height (cm)"
            ></v-text-field>
            <br />
            <v-card-title>Your age</v-card-title>
            <v-text-field v-model="age" hide-details single-line type="number" label="Years old"></v-text-field>
            <br />
            <v-card-title>Your daily activity</v-card-title>
            <v-radio-group v-model="activity" row :mandatory="false">
              <v-radio label="Under average" value="UnderAverage"></v-radio>
              <v-radio label="Average" value="Average"></v-radio>
              <v-radio label="Extremly active" value="AboveAverage"></v-radio>
            </v-radio-group>
          </v-col>
          <v-col>
            <v-card-title style="justify-content: center">Your sex</v-card-title>
            <v-row style="justify-content: center">
              <v-radio-group v-model="sex" row>
                <v-radio label="Male" value="true"></v-radio>
                <v-radio label="Female" value="false"></v-radio>
              </v-radio-group>
            </v-row>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-btn color="success" @click="determine">FIND MY BODY TYPE</v-btn>
          </v-col>
        </v-row>
      </v-container>

      <v-dialog v-model="popupInfo" justify="center">
        <v-card v-if="bodyType != null">
          <v-card-title class="headline" justify="center">
            <span class="headline">{{bodyType.bmi}} bmi</span>
          </v-card-title>
          <v-card-text>
              <v-row justify="space-around">
                <v-col cols="5" v-if="sex">
                  <v-img v-if="bodyType.type == 'endomorph'" src="../resources/male_endomorph.jpg" aspect-ratio="1.7" contain></v-img>
                  <v-img v-if="bodyType.type == 'mesomorph'" src="../resources/male_mesomorph.jpg" aspect-ratio="1.7" contain></v-img>
                  <v-img v-if="bodyType.type == 'ectomorph'" src="../resources/male_ectomorph.jpg" aspect-ratio="1.7" contain></v-img>
                </v-col>
                <v-col cols="5" v-if="!sex">
                  <v-img v-if="bodyType.type == 'endomorph'" src="../resources/female_endomorph.jpg" aspect-ratio="1.7"  contain></v-img>
                  <v-img v-if="bodyType.type == 'mesomorph'" src="../resources/female_mesomorph.jpg" aspect-ratio="1.7" contain></v-img>
                  <v-img v-if="bodyType.type == 'ectomorph'" src="../resources/female_ectomorph.jpg" aspect-ratio="1.7" contain></v-img>
                </v-col>
                <v-col cols="5">{{bodyType.description}}</v-col>
              </v-row>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="popupInfo = false">Close</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-form>
  </v-card>
</template>

<script>
import BasicDeterminationService from "../api-services/basic.determination.service";

export default {
  name: "CreateCertificateForm",
  data() {
    return {
      bodyWeight: null,
      bodyHeight: null,
      age: null,
      sex: "true",
      activity: "UnderAverage",

      bodyType: null,
      popupInfo: false,
      imgSource: ""
    };
  },
  methods: {
    determine() {
      let bodyInfo = {
        bodyWeight: this.bodyWeight,
        bodyHeight: this.bodyHeight,
        age: this.age,
        sex: this.sex,
        activity: this.activity
      };
      BasicDeterminationService.determine(bodyInfo)
        .then(response => {
          this.bodyType = response.data;
          this.imgSource = "../resources/male_" + this.bodyType.type + ".jpg"
          this.popupInfo = true;
        })
        .catch(response => {
          console.log(response);
        });
    }
  }
};
</script>