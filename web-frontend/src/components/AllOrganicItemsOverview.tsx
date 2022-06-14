import ProviderItem from "./ProviderItem";
import {ItemDto} from "../dto/ItemDto";
import {FormEvent, useState} from "react";
import {toast} from "react-toastify";
import {Item} from "../model/Item";

type AllOrganicItemsOverviewProps = {
    id: string | undefined
    items: Item[]
    addItemToBox: (boxId: string, itemId: string) => void
    changeItemName: (itemId: string, itemDto: ItemDto) => void
    addNewItem: (itemName: Omit<Item, "id">) => void
}

export default function AllOrganicItemsOverview({id, items, addNewItem, addItemToBox, changeItemName}: AllOrganicItemsOverviewProps){

    const [itemName, setItemName] = useState<string>("")

    const onSubmit = (event:FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if(itemName.trim() === ""){
            toast.error("Please give a meaningful item name.")
        } else {
            let itemDto: Omit<Item, "id"> = {"name": itemName}
            addNewItem(itemDto)
        }
    }

    return (
        <div>
            <h1>
                All organic items
            </h1>
            {id
                ? items.map((item, idx) => <ProviderItem key={item.id + "-" + idx}
                                                         item={item}
                                                         addItemToBox={addItemToBox}
                                                         boxId={id}
                                                         changeItemName={changeItemName}
                />)
                : <div>Something is wrong. Box not existent</div>}
            <form onSubmit={onSubmit}>
                <input type={"text"}
                       value={itemName}
                       placeholder={"New item name here."}
                       onChange={(event) => setItemName(event.target.value)}
                />
                <button type={"submit"} id="active">Add Item</button>
            </form>
        </div>
    )
}
