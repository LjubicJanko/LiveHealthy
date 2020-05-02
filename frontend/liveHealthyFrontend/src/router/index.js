import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import BodyType from '../views/BodyType.vue'
import Login from '../views/Login.vue'
import SignIn from '../views/SignIn.vue'

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
    path: '/login',
    name: 'Login',
    component: Login
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
