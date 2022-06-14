import {useParams} from "react-router-dom";
import {useEffect} from "react";
import BoxItem from "../components/BoxItem";
import {Item} from "../model/Item"
import {Subscription} from "../model/Subscription";
import {ItemDto} from "../dto/ItemDto";
import AllOrganicItemsOverview from "../components/AllOrganicItemsOverview";

type BoxDetailsPageProps = {
    boxItems: Item[] | undefined
    items: Item[]
    subscriptions: Subscription[] | undefined
    getBoxItems: (id: string) => void
    addNewItem: (itemName: Omit<Item, "id">) => void
    addItemToBox: (boxId: string, itemId: string) => void
    removeItemFromBox: (boxId: string, itemId: string) => void
    changeItemName: (itemId: string, itemDto: ItemDto) => void
}

export default function BoxDetailsPage({
                                           boxItems,
                                           getBoxItems,
                                           items,
                                           subscriptions,
                                           addNewItem,
                                           addItemToBox,
                                           removeItemFromBox,
                                           changeItemName
                                       }: BoxDetailsPageProps) {

    const {id} = useParams()

    useEffect(() => {
        if (id) {
            getBoxItems(id)
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [id])


    let element = subscriptions && subscriptions.find(e => e.id === id)

    return (
        <div className={"box-items"}>
            <h1>
                {element && element.name}
            </h1>
            {boxItems && boxItems.map((item, idx) =>
                <BoxItem key={item.id + "-" + idx}
                         item={item}
                         removeItemFromBox={removeItemFromBox}
                         boxId={id}
                />
            )}
            items &&
            <AllOrganicItemsOverview id={id}
                                     items={items}
                                     addItemToBox={addItemToBox}
                                     addNewItem={addNewItem}
                                     changeItemName={changeItemName}
            />
        </div>
    )
}
