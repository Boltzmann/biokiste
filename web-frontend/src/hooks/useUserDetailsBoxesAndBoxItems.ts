import {useContext, useEffect, useState} from "react";
import {UserDetails} from "../model/UserDetails";
import {Subscription} from "../model/Subscription";
import {AuthContext} from "../context/AuthProvider";
import {toast} from "react-toastify";
import {
    addUserSubscriptionToBox,
    getAllPossibleSubscriptions,
    getBoxItemsByBoxId,
    getSubscriptions,
    getUserDetails, removeUserSubscriptionFromBox
} from "../service/api-service";
import {Item} from "../model/Item";
import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";

export default function useUserDetailsBoxesAndBoxItems(){
    const [userDetails, setUserDetails] = useState<UserDetails>()
    const [subscriptions, setSubscriptions] = useState<Subscription[]>([])
    const [boxItems, setBoxItems] = useState<Item[]>([])
    const [subscribables, setSubscribables] = useState<SubscriptionOverviewDto[]>()
    const {token} = useContext(AuthContext)

    useEffect(() =>{
        getUserDetails(token)
            .then(details => setUserDetails(details))
            .catch(() => toast.error("Connection failed to get user details. Please retry."))
        getSubscriptions(token)
            .then(subs => setSubscriptions(subs))
            .catch(() => toast.error("Connection failed to get abonnements. Please retry."))
        getAllPossibleSubscriptions()
            .then(data => {
                setSubscribables(data)
            })
            .catch(error => toast.error(error))
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

    const removeFromSubscription = (boxId: string) => {
        removeUserSubscriptionFromBox(boxId, token)
            .then(() => {
                    subscriptions ?? toast.info("Removing subscription")
                    setSubscriptions(subscriptions.filter(subscriptions => subscriptions.id !== boxId))
                }
            )
            .catch(error => toast.error(error))
    }

    return {userDetails, subscriptions, boxItems, removeFromSubscription, getBoxItems, subscribeToBox, subscribables}
}
