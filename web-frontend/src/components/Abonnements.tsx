import {Subscription} from "../model/Subscription";

type SubscriptionProps = {
    subscriptions: Subscription[] | undefined
}

export default function Abonnements({subscriptions}: SubscriptionProps) {
    return <div>{subscriptions && subscriptions.map(sub => <div>{sub.name}</div>)}</div>
}
