import {useContext, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {getBoxItemsByBoxId} from "../service/api-service";
import {Item} from "../model/Item";
import {toast} from "react-toastify";

export default function useOrganicBox(){
    const [boxItems, setBoxItems] = useState<Item[]>()
    const {token} = useContext(AuthContext)

    const getBoxItems = (id: string) => {
        getBoxItemsByBoxId(id, token)
            .then(data => setBoxItems(data))
            .catch(error => toast.error(error))
    }

    return { boxItems, getBoxItems}
}
