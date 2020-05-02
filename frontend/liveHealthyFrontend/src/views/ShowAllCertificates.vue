<template>
    <v-card class="mx-10 px-5 py-5 mt-12 text-center">
        <h3>LIST OF ALL CERTIFICATES</h3>
        <CertificatesTableComponent></CertificatesTableComponent>
    </v-card>
</template>

<script>


import CertificatesTableComponent from '../components/CertificatesTableComponent'

export default {
    name: 'ShowAllCertificates',
    components: {
        CertificatesTableComponent
    }
}
</script>