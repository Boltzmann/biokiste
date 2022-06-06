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
        token && readSubscriptions(token)
        getAllPossibleSubscriptions()
            .then(data => {
                setSubscribables(data)
            })
            .catch(error => toast.error(error))
    }, [token])

    const readSubscriptions = (token: string) => {
        getSubscriptions(token)
            .then(subs => setSubscriptions(subs))
            .catch(() => toast.error("Connection failed to get abonnements. Please retry."))
    }

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
        setSubscriptions(subscriptions.filter(
            subscription => subscription.id !== boxId)
        )
    }

    const removeSubscriptionOnce = (boxId: string) => {
        toast.info("Removing " + boxId)

        function removeCustomerOnceFromSubscription(subscription: Subscription) {
            return userDetails &&
                userDetails.id &&
                subscription.customers.splice(subscription.customers.findIndex(() => userDetails.id));
        }

        subscriptions.map(subscription => {
            return removeCustomerOnceFromSubscription(subscription)
        })
        token && readSubscriptions(token)
        return subscriptions
    }

    return {userDetails, subscriptions, boxItems, removeFromSubscription, getBoxItems, subscribeToBox, subscribables}
}
