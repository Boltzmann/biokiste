import {useContext, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {addUserSubscriptionToBox, getBoxItemsByBoxId} from "../service/api-service";
import {Item} from "../model/Item";
import {Subscription} from "../model/Subscription";
import {toast} from "react-toastify";

export default function useOrganicBox(){
    const [boxItems, setBoxItems] = useState<Item[]>()
    const [box, setBox] = useState<Subscription>()
    const {token} = useContext(AuthContext)

    const getBoxItems = (id: string) => {
        getBoxItemsByBoxId(id, token)
            .then(data => setBoxItems(data))
            .catch(error => toast.error(error))
    }

    const subscribeToBox = (boxId: string) => {
        addUserSubscriptionToBox(boxId, token)
            .then(data => setBox(data))
            .then(() => toast.info("BoxId: " + boxId))
            .catch(error => toast.error(error))
    }

    return { boxItems, getBoxItems, box, subscribeToBox }
}
