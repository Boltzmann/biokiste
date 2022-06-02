import {UserDetails} from "../model/UserDetails";
import {Item} from "../model/Item"
import axios from "axios";
import {Subscription} from "../model/Subscription";
import {toast} from "react-toastify";

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

export const getBoxItemsByBoxId: (id: string, token?: string) => Promise<Item[]> = (id, token) => {
    return axios.get(`/api/box/${id}`, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const addUserSubscriptionToBox: ( boxId: string, token?: string) => Promise<Subscription> = ( boxId, token) => {
    return axios.post("/api/user/subscribeBox", boxId, token
        ? {headers: {"Authorization": token, "Content-Type": "application/json"}}
        : {headers: {"Content-Type": "application/json"}})
        .then(response => {toast.info("Box Id: " + boxId); return response.data})
}

