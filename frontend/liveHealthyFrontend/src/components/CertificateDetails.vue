<template>
  <v-card class="mx-auto" max-width="800">
    <v-container class="fill-height" fluid>
      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>Name</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.aliasName}}</p>
          </v-layout>
        </v-col>
      </v-row>
      <v-divider inset></v-divider>
      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>Serial number</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.serialNo}}</p>
          </v-layout>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>Valid from</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.startDate}}</p>
          </v-layout>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>Valid until</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.endDate}}</p>
          </v-layout>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>Common name</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.subject.cn}}</p>
          </v-layout>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>Organisation</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.subject.o}}</p>
          </v-layout>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>Locality</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.subject.l}}</p>
          </v-layout>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>Country</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.subject.c}}</p>
          </v-layout>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>State</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.subject.st}}</p>
          </v-layout>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-layout justify-center>
            <p>Email</p>
          </v-layout>
        </v-col>
        <v-col>
          <v-layout justify-center>
            <p>{{certificate.subject.e}}</p>
          </v-layout>
        </v-col>
      </v-row>
    </v-container>
    <v-divider></v-divider>
  </v-card>
</template>

<script>
export default {
    name: "CertificateDetails",
    props: ['certificate']
}
</script>