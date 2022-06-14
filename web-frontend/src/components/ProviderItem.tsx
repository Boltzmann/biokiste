import {Item} from "../model/Item";
import {AiOutlineEdit, AiOutlinePlus, AiOutlineSend} from "react-icons/ai";
import {ItemDto} from "../dto/ItemDto";
import {FormEvent, useState} from "react";
import "./ItemOrBoxes.css"

type ItemProps = {
    item: Item
    addItemToBox: (boxId: string, itemId: string) => void
    boxId: string | undefined
    changeItemName: (itemId: string, itemDto: ItemDto) => void
}

export default function ProviderItem({item, addItemToBox, boxId, changeItemName}: ItemProps) {
    const [isNameEditable, setIsNameEditable] = useState<boolean>(false)
    const [newName, setNewName] = useState<string>("")

    const onSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const itemDto: ItemDto = {name: newName}
        changeItemName(item.id, itemDto)
    }

    return (
        <div className="item-or-box">
            <button id="active" onClick={() => {
                boxId && addItemToBox(boxId, item.id)
            }}>
                <AiOutlinePlus/>
            </button>
            {isNameEditable ?
                <form className="growable" onSubmit={onSubmit}>
                    <input type={"text"}
                           value={newName}
                           placeholder={item.name}
                           onChange={(event) => setNewName(event.target.value)}
                    />
                    <button type={"submit"} id="active">
                        <AiOutlineSend/>
                    </button>
                </form> :
                <div className="growable" id="inactive">
                    {item.name}
                </div>
            }
            <button id="active" onClick={() => {
                setIsNameEditable(!isNameEditable)
            }}>
                <AiOutlineEdit/>
            </button>
        </div>
    )
}
