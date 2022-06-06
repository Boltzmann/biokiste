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
    removeFromSubscription: (boxId: string) => void | undefined
}

export default function OverviewPage(
    {subscribeToBox, removeFromSubscription, subscriptions, userDetails, subscribables}: OverviewPageProps
) {

    return (
        <div>
            <ImportantUserDetails userDetails={userDetails} />
            <Abonnements
                subscriptions={subscriptions}
                userDetails={userDetails}
                subscribeToBox={subscribeToBox}
                removeFromSubscription={removeFromSubscription}
                subscribables={subscribables}/>
        </div>
    )
}
