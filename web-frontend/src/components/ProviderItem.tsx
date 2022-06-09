import {Item} from "../model/Item";
import {AiOutlinePlus} from "react-icons/ai";

type ItemProps = {
    item: Item
    addItemToBox: (boxId: string, itemId: string) => void
    boxId: string | undefined
}

export default function ProviderItem({item, addItemToBox, boxId}: ItemProps) {
    return (
        <div>
            <div id="inactive">
                {item.name}
            </div>
            <button id="active" onClick={() => {
                boxId && addItemToBox(boxId, item.id)
            }}>
                <AiOutlinePlus/>
            </button>
        </div>
    )
}
