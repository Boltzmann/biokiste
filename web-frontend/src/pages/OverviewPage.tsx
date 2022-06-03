import ImportantUserDetails from "../components/ImportantUserDetails";
import Abonnements from "../components/Abonnements";
import {Subscription} from "../model/Subscription";
import {UserDetails} from "../model/UserDetails";

type OverviewPageProps= {
    subscribeToBox: (boxId: string) => void
    subscriptions: Subscription[] | undefined
    userDetails: UserDetails | undefined
    subscribables: string[] | undefined
}

export default function OverviewPage({subscribeToBox, subscriptions, userDetails, subscribables}: OverviewPageProps){

    return (
        <div>
            <ImportantUserDetails userDetails={userDetails} />
            <Abonnements
                subscriptions={subscriptions}
                userDetails={userDetails}
                subscribeToBox={subscribeToBox}
                subscribables={subscribables}/>
        </div>
    )
}
