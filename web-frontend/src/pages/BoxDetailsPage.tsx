import {useParams} from "react-router-dom";
import {FormEvent, useEffect, useState} from "react";
import BoxItem from "../components/BoxItem";
import {Item} from "../model/Item"
import {Subscription} from "../model/Subscription";
import {toast} from "react-toastify";
import ProviderItem from "../components/ProviderItem";

type BoxDetailsPageProps = {
    boxItems: Item[] | undefined
    items: Item[]
    subscriptions: Subscription[] | undefined
    getBoxItems: (id: string) => void
    addNewItem: (itemName: Omit<Item, "id">) => void
    addItemToBox: (boxId: string, itemId: string) => void
}

export default function BoxDetailsPage({boxItems, getBoxItems, items, subscriptions, addNewItem, addItemToBox}: BoxDetailsPageProps){

    const {id} = useParams()
    const [itemName, setItemName] = useState<string>("")

    useEffect(() => {
        if (id) {
            getBoxItems(id)
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [id])

    const onSubmit = (event:FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if(itemName.trim() === ""){
            toast.error("Please give a meaningful item name.")
        } else {
            let itemDto: Omit<Item, "id"> = {"name": itemName}
            addNewItem(itemDto)
        }
    }

    let element = subscriptions && subscriptions.find(e => e.id === id)

    return (
        <div className={"box-items"}>
            <h1>{element && element.name}</h1>
            {boxItems && boxItems.map(item => <BoxItem  item={item}/>)}
            <h1>All items of Provider</h1>
            {id
                ? items.map(item => <ProviderItem item={item} addItemToBox={addItemToBox} boxId={id}/>)
                : <div>Something is wrong. Box not existent</div>}
            <form onSubmit={onSubmit}>
                <input type={"text"}
                       value={itemName}
                       placeholder={"New item name here."}
                       onChange={(event) => setItemName(event.target.value) }
                />
                <button type={"submit"} id="active">Add Item</button>
            </form>
        </div>
    )
}
