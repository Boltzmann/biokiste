import {Subscription} from "../model/Subscription";
import {useNavigate} from "react-router-dom";

type SubscriptionProps = {
    subscriptions: Subscription[] | undefined
}

export default function Abonnements({subscriptions}: SubscriptionProps) {

    const navigate = useNavigate()

    return <div>{subscriptions &&
        subscriptions.map(sub =>
            <div className="AboElement" id="active" onClick={() => navigate('/box/' + sub.id)}>
                {sub.name}
            </div>
        )}
    </div>
}
