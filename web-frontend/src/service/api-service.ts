import {UserDetails} from "../model/UserDetails";
import {Item} from "../model/Item"
import axios from "axios";
import {Subscription} from "../model/Subscription";
import {toast} from "react-toastify";
import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";
import {ItemDto} from "../dto/ItemDto";

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

export const getAllPossibleSubscriptions: () => Promise<SubscriptionOverviewDto[]> = () => {
    return axios.get("/allBoxes")
        .then(response => {toast.info(response.data); return response.data})
}

export const addUserSubscriptionToBox: ( boxId: string, token?: string) => Promise<Subscription> = ( boxId, token) => {
    return axios.post("/api/user/subscribeBox", boxId, token
        ? {headers: {"Authorization": token, "Content-Type": "application/json"}}
        : {headers: {"Content-Type": "application/json"}})
        .then(response => {toast.info("Box Id: " + boxId); return response.data})
}

export const removeUserSubscriptionFromBox: ( boxId: string, token?: string) => Promise<void> = ( boxId, token ) => {
    return axios.delete("/api/user/subscribeBox/" + boxId, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const findAllItems: (token?: string) => Promise<Item[]> = (token) => {
    return axios.get("/api/item/all", token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const addItem: ( itemDto: Omit<Item, "id">, token?: string ) => Promise<Item> = ( itemDto, token) => {
    return axios.post("api/item", itemDto, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const putItemToBox: (boxId: string, itemId: string, token?: string) => Promise<Subscription> = (boxId, itemId, token) => {
    return axios.put("api/box/" + boxId + "/item/" + itemId, {}, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const deleteItemFromBox: (boxId: string, itemId: string, token?: string) => void = (boxId, itemId, token) => {
    axios.delete("api/box/" + boxId + "/item/" + itemId, token
        ? {headers: {"Authorization": token}}
        : {})
}

export const putChangedItem: (itemId: string, itemDto: ItemDto, token?: string) => Promise<Item> = (itemId, itemDto, token) => {
    return axios.put("api/item/" + itemId, itemDto, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const sendVerificationEmail: (userDetails: Omit<UserDetails, "id" | "customerId" | "verificationCode">) => Promise<string> = (userDetails) => {
    return axios.post("/verification", userDetails)
        .then(response => response.data)
}
