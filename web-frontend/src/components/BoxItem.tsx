import {Item} from "../model/Item";
import {AiOutlineMinus} from "react-icons/ai";

type ItemProps = {
    item: Item
    removeItemFromBox: (boxId: string, itemId: string) => void
    boxId: string | undefined
}

export default function BoxItem({item, removeItemFromBox, boxId}: ItemProps) {
    return (
        <div>
            <div id="inactive">
                {item.name}
            </div>
            <button id="active" onClick={() => {
                boxId && removeItemFromBox(boxId, item.id)
            }}>
                <AiOutlineMinus/>
            </button>
        </div>
    )
}
