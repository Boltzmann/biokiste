import {useContext, useEffect, useState} from "react";
import {UserDetails} from "../model/UserDetails";
import {Subscription} from "../model/Subscription";
import {AuthContext} from "../context/AuthProvider";
import {toast} from "react-toastify";
import {
    addItem,
    addUserSubscriptionToBox,
    deleteItemFromBox,
    findAllItems,
    getAllPossibleSubscriptions,
    getBoxItemsByBoxId,
    getSubscriptions,
    getUserDetails,
    putChangedItem,
    putItemToBox,
    removeUserSubscriptionFromBox
} from "../service/api-service";
import {Item} from "../model/Item";
import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";
import {ItemDto} from "../dto/ItemDto";

export default function useUserDetailsBoxesAndBoxItems(){
    const [userDetails, setUserDetails] = useState<UserDetails>()
    const [subscriptions, setSubscriptions] = useState<Subscription[]>([])
    const [boxItems, setBoxItems] = useState<Item[]>([])
    const [subscribables, setSubscribables] = useState<SubscriptionOverviewDto[]>()
    const [items, setItems] = useState<Item[]>([])
    const {token} = useContext(AuthContext)

    useEffect(() =>{
        getUserDetails(token)
            .then(details => setUserDetails(details))
            .catch(() => toast.error("Connection failed to get user details. Please retry."))
        getAllPossibleSubscriptions()
            .then(data => {
                setSubscribables(data)
            })
            .catch(error => toast.error(error))
    }, [token])

    useEffect(() => {
        readSubscriptions()
        getAllItems()
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [token])

    const readSubscriptions = () => {
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

    const removeFromSubscriptionOnce = (boxId: string) => {
        toast.info("Removing " + boxId)
        removeUserSubscriptionFromBox(boxId, token)
        readSubscriptions()
    }

    const getAllItems = () => {
        findAllItems(token)
            .then(data => setItems(data))
            .catch(error => toast.error(error))
    }

    const addNewItem = (itemDto: Omit<Item, "id">) => {
        addItem(itemDto, token)
            .then(data => setItems([...items, data]))
            .catch(error => toast.error(error))
    }

    const addItemToBox = (boxId: string, itemId: string) => {
        putItemToBox(boxId, itemId, token)
            .then(data => getBoxItems(data.id))
            .catch(error => toast.error(error))
    }

    const removeItemFromBox = (boxId: string, itemId: string) => {
        deleteItemFromBox(boxId, itemId, token)
        setBoxItems(boxItems.filter(boxItem => boxItem.id !== itemId))
    }

    function replaceBoxItems(data: Item) {
        const numberItems2Replace =
            boxItems.filter(item => data.id === item.id).length
        const upatedBoxItems = boxItems.filter(item => data.id !== item.id)
        for (let i = 0; i < numberItems2Replace; i++) {
            upatedBoxItems.push(data)
        }
        setBoxItems(upatedBoxItems)
    }

    const changeItemName = (itemId: string, itemDto: ItemDto) => {
        putChangedItem(itemId, itemDto, token)
            .then(data => {
                    setItems(
                        [...items.filter(item => data.id !== item.id), data]
                    )
                    replaceBoxItems(data);
                }
            )
    }

    return {
        userDetails, subscriptions,
        boxItems, removeFromSubscriptionOnce,
        getBoxItems, subscribeToBox,
        subscribables, items,
        addNewItem, addItemToBox, removeItemFromBox,
        changeItemName
    }
}
