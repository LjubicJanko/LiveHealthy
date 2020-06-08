import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import BodyType from '../views/BodyType.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import SignIn from '../views/SignIn.vue'
import PlanOverview from '../views/PlanOverview'
import AdminPage from '../views/AdminPage'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/adminPage',
    name: 'AdminPage',
    component: AdminPage
  },
  {
    path: '/body-type',
    name: 'BodyType',
    component: BodyType
  },
  {
    path: '/myPlan/:userId',
    name: 'PlanOverview',
    component: PlanOverview
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/logout',
    name: 'Logout',
    component: Logout
  },
  {
    path: '/signIn',
    name: 'SignIn',
    component: SignIn
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
