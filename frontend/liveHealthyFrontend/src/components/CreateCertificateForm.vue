<template>
  <v-card class="mx-10 px-5 py-5 mt-12 text-center">
    <v-form>
      <v-container>
        <v-row>
          <v-col>
            <v-text-field v-model="certAliasName" label="Certificate name"></v-text-field>
            <v-text-field v-model="yearsToBeValid" label="Years to be valid"></v-text-field>
            <v-text-field v-model="cn" label="[CN] Common name"></v-text-field>
            <v-text-field v-model="o" label="[O] Organization"></v-text-field>
            <v-text-field v-model="ou" label="[OU] Organization unit"></v-text-field>
            <v-text-field v-model="l" label="[L] Locality"></v-text-field>
            <v-text-field v-model="st" label="[ST] State"></v-text-field>
            <v-text-field v-model="c" label="[C] Country"></v-text-field>
            <v-text-field v-model="e" label="[E] E-mail"></v-text-field>
          </v-col>
          <v-col>
            <v-row style="justify-content: center">
              <v-radio-group v-model="extensions.ca" row>
                <v-radio label="Certificate Authority" :value="true"></v-radio>
                <v-radio label="Leaf Certificate" :value="false"></v-radio>
              </v-radio-group>
            </v-row>
            <v-row style="justify-content: center">
              <v-col cols="4">
                <v-checkbox label="Non repudiation" v-model="extensions.nonRepudiation"></v-checkbox>
                <v-checkbox label="Digital signature" v-model="extensions.digitalSignature"></v-checkbox>
                <v-checkbox label="Key encipherment" v-model="extensions.keyEncipherment"></v-checkbox>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-btn color="success" @click="create">Create</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </v-card>
</template>

<script>
import CertificateService from "../api-services/certificate.service";

export default {
  name: "CreateCertificateForm",
  data() {
    return {
      certAliasName: "",
      yearsToBeValid: 1,
      cn: "",
      o: "",
      ou: "",
      l: "",
      st: "",
      c: "",
      e: "",
      extensions: {
        ca: false,
        nonRepudiation: false,
        digitalSignature: false,
        keyEncipherment: false
      }
    };
  },
  methods: {
    create() {
      let data = {
        yearsToBeValid: this.yearsToBeValid,
        certAliasName: this.certAliasName,
        subjectInfoDto: {
          cn: this.cn,
          o: this.o,
          ou: this.ou,
          l: this.l,
          st: this.st,
          c: this.c,
          e: this.e
        },
        certificateKeyUsageDTO: {
          ca: this.extensions.ca,
          nonRepudiation: this.extensions.nonRepudiation,
          digitalSignature: this.extensions.digitalSignature,
          keyEncipherment: this.extensions.keyEncipherment
        }
      };

      CertificateService.create(data);
    }
  }
};
</script>