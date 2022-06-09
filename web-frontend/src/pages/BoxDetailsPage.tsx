import {useParams} from "react-router-dom";
import {FormEvent, useEffect, useState} from "react";
import BoxItem from "../components/BoxItem";
import {Item} from "../model/Item"
import {Subscription} from "../model/Subscription";
import {toast} from "react-toastify";

type BoxDetailsPageProps = {
    boxItems: Item[] | undefined
    items: Item[]
    subscriptions: Subscription[] | undefined
    getBoxItems: (id: string) => void
    addNewItem: (itemName: Omit<Item, "id">) => void
}

export default function BoxDetailsPage({boxItems, getBoxItems, items, subscriptions, addNewItem}: BoxDetailsPageProps){

    const {id} = useParams()
    const [itemName, setItemName] = useState<string>("")

    useEffect(() => {
        if (id) {
            // google useCallback or useMemo
            // https://stackoverflow.com/questions/59597371/react-useeffect-dependency-of-usecallback-always-triggers-render
            getBoxItems(id)
        }
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
            {boxItems && boxItems.map((item, idx) => <BoxItem key={item.id + "-" + idx} item={item}/>)}
            <h1>All items of Provider</h1>
            {items.map((item, idx) => <BoxItem key={item.id + "-" + idx} item={item}/>)}
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
