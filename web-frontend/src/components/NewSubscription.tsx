import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";
import {AiOutlinePlus} from "react-icons/ai";
import "./ItemOrBoxes.css"
import {useNavigate} from "react-router-dom";

type NewSubscriptionProps = {
    subscribeToBox: (boxId: string) => void
    subscribables: SubscriptionOverviewDto[] | undefined
}

export default function NewSubscription({ subscribeToBox, subscribables}: NewSubscriptionProps ) {

    const navigate = useNavigate()

    return <div>
        {subscribables &&
            subscribables.map(
                subsls =>
                    <div className="item-or-box" key={subsls.id}>
                        <div id="inactive"></div>
                        <button className="AboElement growable"
                                id="active"
                                onClick={() => navigate('/box/' + subsls.id)}
                        >{subsls.name}</button>
                        <button id="active" onClick={
                            () => subscribeToBox(subsls.id)
                        }><AiOutlinePlus/>
                        </button>
                    </div>)}
    </div>
}
