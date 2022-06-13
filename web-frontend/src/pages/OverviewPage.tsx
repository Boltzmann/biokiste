import Abonnements from "../components/Abonnements";
import {Subscription} from "../model/Subscription";
import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";

type OverviewPageProps= {
    subscribeToBox: (boxId: string) => void
    subscriptions: Subscription[] | undefined
    subscribables: SubscriptionOverviewDto[] | undefined
    removeFromSubscriptionOnce: (boxId: string) => void | undefined
}

export default function OverviewPage(
    {subscribeToBox, removeFromSubscriptionOnce, subscriptions, subscribables}: OverviewPageProps
) {

    return (
        <div>
            <Abonnements
                subscriptions={subscriptions}
                subscribeToBox={subscribeToBox}
                removeFromSubscriptionOnce={removeFromSubscriptionOnce}
                subscribables={subscribables}/>
        </div>
    )
}
