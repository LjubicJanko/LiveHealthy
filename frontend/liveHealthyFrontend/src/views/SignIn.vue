<template>
    <div style="height: 100%;">
        <v-content>
            <v-container fluid fill-height>
                <v-layout align-center justify-center>
                    <v-flex xs12 sm8 md4>
                        <v-card class="elevation-12">
                        <v-toolbar dark color="black">
                            <v-toolbar-title>Create a new account</v-toolbar-title>
                            <v-spacer></v-spacer>
                        </v-toolbar>
                        <v-card-text>
                           <v-layout wrap>
                            <v-flex xs12 sm6>
                                <v-text-field label="First name*" v-model=registration.firstname :rules="firstname_rules" required></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6>
                                <v-text-field label="Last name*" v-model=registration.lastname :rules="lastname_rules" required></v-text-field>
                            </v-flex>
                            <v-flex xs12>
                                <v-text-field label="Username*" v-model=registration.username :rules="username_rules" required></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6>
                                <v-text-field label="Password*" v-model="registration.password" :type="'password'" :rules="password_rules" required></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6>
                                <v-text-field label="Confirm password*" v-model="passwordConfirmation" :error-messages='passwordMatchError()' :type="'password'" :rules="confirm_password_rules" required></v-text-field>
                            </v-flex>
                            <v-flex xs12>
                                <v-text-field label="Email*" v-model=registration.email :rules="email_rules" required></v-text-field>
                            </v-flex>
                            </v-layout> 
                        </v-card-text>
                        <small>*indicates required field</small>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="white" @click="back" >Back</v-btn>
                            <v-btn :disabled="!formValid" color="primary" @click="validateUser"> Create an account</v-btn>
                            <v-btn color="error" @click="reset">Reset</v-btn>
                        </v-card-actions>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-content>
    <v-snackbar
        v-model="snackbar.show"
        :timeout="5000"
        :color="snackbar.color"
        :top="true"
    >
      {{snackbar.msg}}
      <v-btn
          dark
          flat
          @click="snackbar.show = false"
      >
      Close
      </v-btn>
    </v-snackbar>
    </div>
</template>

<script>
import Registration from "@/model/Registration.js"

export default {
    name: "Registration",
    data: () => ({
        formValid:true,
        passwordConfirmation:null,
        firstname_rules:[
            v => !!v || 'First name is required'
        ],
        lastname_rules:[
            v => !!v || 'Last name is required'
        ],
        username_rules:[
            v => !!v || 'Username is required'
        ],
        password_rules:[
            v => !!v || 'Password is required',
            v => (v && v.length >= 5) || 'Password is too short'
        ],
        email_rules:[
            v => !!v || 'Email is required',
            v => /[0-9]*[A-Z]*[0-9]*[a-z]+[0-9]*[A-Z]*[0-9]*@[a-z]+\.[a-z]+/.test(v) || 'Please enter a valid email'
        ],
        confirm_password_rules:[
            v => !!v || 'Password confirmation is required'
        ],
        registration : new Registration(),
        snackbar: {
            show: false,
            color: "",
            msg: "",
        }
    }),
    methods: {
        passwordMatchError () {
            return (this.registration.password === this.passwordConfirmation) ? '' : 'Please enter a matching password'
        },
        reset() {
            this.$refs.form.reset();
        },
        validateUser() {
            if(this.$refs.form.validate()){
                this.onSubmit();
            }
        },
        onSubmit() {
            // UserController.create(this.registration).
            //     then((response) => {
            //         this.showSnackbar("Successful registration", "success");
            //         this.back();
            //     })
            //     .catch((error) => {
            //         this.showSnackbar(error.response.data, "error")
            //     })
        },
        back() {
            this.$router.push('login');
        },
        showSnackbar(message,color) {
            this.snackbar.color = color;
            this.snackbar.msg = message;
            this.snackbar.show = true;
        }
    }
}
</script>