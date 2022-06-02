import {useContext, useEffect, useState} from "react";
import {UserDetails} from "../model/UserDetails";
import {Subscription} from "../model/Subscription";
import {AuthContext} from "../context/AuthProvider";
import {toast} from "react-toastify";
import {addUserSubscriptionToBox, getBoxItemsByBoxId, getSubscriptions, getUserDetails} from "../service/api-service";
import {Item} from "../model/Item";

export default function useUserDetails(){
    const [userDetails, setUserDetails] = useState<UserDetails>()
    const [subscriptions, setSubscriptions] = useState<Subscription[]>()
    const [boxItems, setBoxItems] = useState<Item[]>()
    const {token} = useContext(AuthContext)

    useEffect(() =>{
        getUserDetails(token)
            .then(details => setUserDetails(details))
            .catch(() => toast.error("Connection failed to get user details. Please retry."))
        getSubscriptions(token)
            .then(subs => setSubscriptions(subs))
            .catch(() => toast.error("Connection failed to get abonnements. Please retry."))
    }, [token])

    const getBoxItems = (id: string) => {
        getBoxItemsByBoxId(id, token)
            .then(data => setBoxItems(data))
            .catch(error => toast.error(error))
    }

    const subscribeToBox = (boxId: string) => {
        addUserSubscriptionToBox(boxId, token)
            .then(data => {subscriptions ? setSubscriptions([...subscriptions, data]) : setSubscriptions([data])})
            .catch(error => toast.error(error))
    }

    return {userDetails, subscriptions, boxItems, getBoxItems, subscribeToBox}
}
