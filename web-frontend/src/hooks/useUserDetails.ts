import {useContext, useEffect, useState} from "react";
import {UserDetails} from "../model/UserDetails";
import {AuthContext} from "../context/AuthProvider";
import {toast} from "react-toastify";
import {getUserDetails} from "../service/api-service";

export default function useUserDetails(){
    const [userDetails, setUserDetails] = useState<UserDetails>()
    const {token} = useContext(AuthContext)

    useEffect(() =>{
        getUserDetails(token)
            .then(details => setUserDetails(details))
            .catch(() => toast.error("Connection failed. Please retry."))
    }, [token])

    return {userDetails}
}