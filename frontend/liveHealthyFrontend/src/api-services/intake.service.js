import Axios from 'axios';

const ENDPOINTS = {
    SUBMIT: 'intake/submit/'
}

export default {
    submit(userId, dayIndex, caloriesDifference) {
        return Axios.post(ENDPOINTS.SUBMIT + userId + "/" + dayIndex, caloriesDifference);
    }
}