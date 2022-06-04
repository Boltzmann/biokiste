import {useParams} from "react-router-dom";
import {useEffect} from "react";
import BoxItem from "../components/BoxItem";
import {Item} from "../model/Item"
type BoxDetailsPageProps = {
    boxItems: Item[] | undefined
    getBoxItems: (id: string) => void
}

export default function BoxDetailsPage({boxItems, getBoxItems}: BoxDetailsPageProps){

    const {id} = useParams()

    useEffect(() => {
        if (id) {
            getBoxItems(id)
        }
    }, [id, getBoxItems])

    return (
        <div className={"box-items"}>
            <h1>Contents of Box</h1>
            {boxItems && boxItems.map(item => <BoxItem  item={item}/>)}
        </div>
    )
}
