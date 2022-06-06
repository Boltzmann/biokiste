import {Item} from "../model/Item";

type ItemProps = {
    item: Item
}

export default function BoxItem({item} : ItemProps){
    return (<div id="inactive">{item.name}</div>)
}
