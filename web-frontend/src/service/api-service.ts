import {UserDetails} from "../model/UserDetails";
import axios from "axios";
import {Subscription} from "../model/Subscription";

export const getUserDetails: (token?: string) => Promise<UserDetails> = (token) => {
    return axios.get("/api/user/me", token
    ? {headers: {"Authorization": token}}
    : {})
        .then(response => response.data)
}

export const getSubscriptions: (token?: string) => Promise<Subscription[]> = (token) => {
    return axios.get("/api/user/subscribedBoxes", token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

