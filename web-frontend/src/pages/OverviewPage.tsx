import ImportantUserDetails from "../components/ImportantUserDetails";
import Abonnements from "../components/Abonnements";
import {Subscription} from "../model/Subscription";
import {UserDetails} from "../model/UserDetails";

type OverviewPageProps= {
    subscribeToBox: (boxId: string) => void
    subscriptions: Subscription[] | undefined
    userDetails: UserDetails | undefined
}

export default function OverviewPage({subscribeToBox, subscriptions, userDetails}: OverviewPageProps){

    return (
        <div>
            <ImportantUserDetails userDetails={userDetails} />
            <Abonnements subscriptions={subscriptions} userDetails={userDetails} subscribeToBox={subscribeToBox}/>
        </div>
    )
}
