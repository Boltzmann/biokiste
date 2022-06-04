import {Subscription} from "../model/Subscription";
import {useNavigate} from "react-router-dom";
import {UserDetails} from "../model/UserDetails";
import NewSubscription from "./NewSubscription";
import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";
import {AiOutlineMinus} from "react-icons/ai";
import {toast} from "react-toastify";

type AbonnementProps = {
    subscriptions: Subscription[] | undefined
    userDetails: UserDetails | undefined
    subscribeToBox: (boxId: string) => void
    subscribables: SubscriptionOverviewDto[] | undefined
    removeFromSubscription: (boxId: string) => void | undefined
}

export default function Abonnements(
    {subscriptions, userDetails, subscribeToBox, removeFromSubscription, subscribables}: AbonnementProps
) {

    const navigate = useNavigate()

    return <div>
        <h2>Biokisten</h2><NewSubscription
        userDetails={userDetails}
        subscribeToBox={subscribeToBox}
        removeFromSubscription={removeFromSubscription}
        subscribables={subscribables}
        />
        <h2>Abonnements</h2>
        {subscriptions &&
        subscriptions.map(sub =>
            <div><div key={sub.uuidi} className="AboElement" id="active" onClick={() => navigate('/box/' + sub.id)}>
                {sub.name}
            </div><div id="active" onClick={() => removeFromSubscription(sub.id)}><AiOutlineMinus/></div></div>
        )}
    </div>
}
