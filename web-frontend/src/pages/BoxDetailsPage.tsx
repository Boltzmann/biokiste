import {useParams} from "react-router-dom";
import {useEffect} from "react";
import useOrganicBox from "../hooks/useOrganicBox";
import BoxItem from "../components/BoxItem";

export default function BoxDetailsPage(){

    const {id} = useParams()
    const {boxItems, getBoxItems} = useOrganicBox()

    useEffect(() => {
        if (id) {
            getBoxItems(id)
        }
    }, [id])

    return (
        <div className={"box-items"}>
            <h1>Contents of Box</h1>
            {boxItems && boxItems.map(item => <BoxItem  item={item}/>)}
        </div>
    )
}
