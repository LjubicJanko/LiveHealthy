<template>
    <div>
        <CreateCertificateForm/>
        <router-link to="/">Home</router-link>
    </div>
</template>

<script>

import CreateCertificateForm from '../components/CreateCertificateForm'

export default {
    name: 'CreateCertificate',
    components: {
        CreateCertificateForm
    }
}
</script>