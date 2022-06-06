import ImportantUserDetails from "../components/ImportantUserDetails";
import Abonnements from "../components/Abonnements";
import {Subscription} from "../model/Subscription";
import {UserDetails} from "../model/UserDetails";
import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";

type OverviewPageProps= {
    subscribeToBox: (boxId: string) => void
    subscriptions: Subscription[] | undefined
    userDetails: UserDetails | undefined
    subscribables: SubscriptionOverviewDto[] | undefined
    removeFromSubscriptionOnce: (boxId: string) => void | undefined
}

export default function OverviewPage(
    {subscribeToBox, removeFromSubscriptionOnce, subscriptions, userDetails, subscribables}: OverviewPageProps
) {

    return (
        <div>
            <ImportantUserDetails userDetails={userDetails} />
            <Abonnements
                subscriptions={subscriptions}
                subscribeToBox={subscribeToBox}
                removeFromSubscriptionOnce={removeFromSubscriptionOnce}
                subscribables={subscribables}/>
        </div>
    )
}
