import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import BodyType from '../views/BodyType.vue'
import ShowAllCertificates from '../views/ShowAllCertificates.vue'
import CertificateOverview from '../views/CertificateOverview.vue'
import CertificateRevokeListOverview from '../views/CertificateRevokeListOverview.vue'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/body-type',
    name: 'BodyType',
    component: BodyType
  },
  {
    path: '/all-certificates',
    name: 'ShowAllCertificates',
    component: ShowAllCertificates
  },
  {
    path: '/certificate/:id',
    name: 'certificate',
    component: CertificateOverview
  },
  {
    path: '/crl',
    name: 'certificateRevokeList',
    component: CertificateRevokeListOverview
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
