import {Subscription} from "../model/Subscription";
import {useNavigate} from "react-router-dom";
import {UserDetails} from "../model/UserDetails";
import NewSubscription from "./NewSubscription";

type AbonnementProps = {
    subscriptions: Subscription[] | undefined
    userDetails: UserDetails | undefined
    subscribeToBox: (boxId: string) => void
    subscribables: string[] | undefined
}

export default function Abonnements({subscriptions, userDetails, subscribeToBox, subscribables}: AbonnementProps) {

    const navigate = useNavigate()

    return <div>
        <h2>Abonnierte Biokisten</h2><NewSubscription userDetails={userDetails} subscribeToBox={subscribeToBox}/>
        {subscriptions &&
        subscriptions.map(sub =>
            <div className="AboElement" id="active" onClick={() => navigate('/box/' + sub.id)}>
                {sub.name}
            </div>
        )}
        <h2>Alle Biokisten</h2>
        {subscribables && subscribables.map(subls => <div>{subls}</div>)}
    </div>
}
