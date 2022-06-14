import {Subscription} from "../model/Subscription";
import {useNavigate} from "react-router-dom";
import NewSubscription from "./NewSubscription";
import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";
import {AiOutlineMinus} from "react-icons/ai";
import "./ItemOrBoxes.css"

type AbonnementProps = {
    subscriptions: Subscription[] | undefined
    subscribeToBox: (boxId: string) => void
    subscribables: SubscriptionOverviewDto[] | undefined
    removeFromSubscriptionOnce: (boxId: string) => void | undefined
}

export default function Abonnements(
    {subscriptions, subscribeToBox, removeFromSubscriptionOnce, subscribables}: AbonnementProps
) {

    const navigate = useNavigate()

    return <div>
        <h2>Biokisten</h2><NewSubscription
        subscribeToBox={subscribeToBox}
        subscribables={subscribables}
    />
        <h2>Abonnements</h2>
        <div>
        {subscriptions &&
            subscriptions.map((sub, idx) =>
                <div className="item-or-box" key={idx}>
                    <div id="inactive"></div>
                    <button className="growable"
                            id="active"
                            onClick={() => navigate('/box/' + sub.id)}
                    >
                        {sub.name}
                    </button>
                    <button id="active"
                            onClick={() => removeFromSubscriptionOnce(sub.id)}
                    >
                        <AiOutlineMinus/>
                    </button>
                </div>
            )}
        </div>
    </div>
}
