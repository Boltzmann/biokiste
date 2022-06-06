import {Subscription} from "../model/Subscription";
import {useNavigate} from "react-router-dom";
import {UserDetails} from "../model/UserDetails";
import NewSubscription from "./NewSubscription";
import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";
import {AiOutlineMinus} from "react-icons/ai";

type AbonnementProps = {
    subscriptions: Subscription[] | undefined
    userDetails: UserDetails | undefined
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
        {subscriptions &&
        subscriptions.map(sub =>
            <div><div className="AboElement" id="active" onClick={() => navigate('/box/' + sub.id)}>
                {sub.name}
            </div><div id="active" onClick={() => removeFromSubscriptionOnce(sub.id)}><AiOutlineMinus/></div></div>
        )}
    </div>
}
