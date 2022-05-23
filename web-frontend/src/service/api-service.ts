import {UserDetails} from "../model/UserDetails";
import axios from "axios";

export const getUserDetails: (token?: string) => Promise<UserDetails> = (token) => {
    return axios.get("/api/user/me", token
    ? {headers: {"Authorization": token}}
    : {})
        .then(response => response.data)
}