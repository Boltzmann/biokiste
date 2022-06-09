import {Item} from "../model/Item";

type ItemProps = {
    item: Item
    addItemToBox: (boxId: string, itemId: string) => void
    boxId: string
}

export default function ProviderItem({item, addItemToBox, boxId}: ItemProps){
    return (<div>
        <div id="inactive">{item.name}</div>
        <button id="active" onClick={() => addItemToBox(boxId, item.id)}>+</button>
        </div>
    )
}
