import Axios from 'axios';
// import Vue from 'vue';
import store from '@/store';
import { User } from '../model/User';
import * as _ from 'lodash';

const AUTH_HEADER = 'Authorization';

const ENDPOINTS = {
    LOGIN: 'auth/login',
    SIGNIN: 'basic/signIn'
}

export default {

    setLocalStorageAuthData(data) {
        localStorage.setItem('accessToken',data.accessToken);  
        localStorage.setItem('userId', data.userDTO ? data.userDTO.id : null);
      },

    setAuthHeader(unset = false) {
        if (unset) {
          delete Axios.defaults.headers[AUTH_HEADER];
          return;
        }
        _.assign(
          Axios.defaults.headers,
          {'Authorization' : 'JWT' + localStorage.getItem('accessToken')}
        );
      },

    //   initStoreAuth() {
    //     const userData = JSON.parse(localStorage.getItem('user'));
    //     if (userData) {
    //       this.setAuthHeader();
    //       store.commit('auth', userData);
    //       return LoginApiService.refreshUser().then((response) => {
    //         if (response.data) {
    //           this.updateUserInLocalStorage(response.data);
    //         }
    //       });
    //     }
    //     return this.setAuthHeader(true);
    //   },

    login(bodyInfo) {
        return Axios.post(ENDPOINTS.LOGIN, bodyInfo).then((response) => {
            var user = new User();
            Object.assign(user, response.data);
            this.setLocalStorageAuthData(user);
            this.setAuthHeader();
            store.commit('login')
            store.commit('setUserId', response.data.userDTO.id);
            store.commit('setUser', response.data.userDTO);
            return response;
        });
      },

    signIn(registration){
        return Axios.post(ENDPOINTS.SIGNIN, registration)
    },

    logout() {
      this.setLocalStorageAuthData({
        accessToken: null,
        userId: null,
      });
      this.setAuthHeader(true);
      store.commit('logout');
    },
  
}