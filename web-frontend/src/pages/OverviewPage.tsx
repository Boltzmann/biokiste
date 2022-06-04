import ImportantUserDetails from "../components/ImportantUserDetails";
import useUserDetails from "../hooks/useUserDetails";
import Abonnements from "../components/Abonnements";

export default function OverviewPage(){
    const {userDetails, subscriptions} = useUserDetails()

    return (
        <div>
            <ImportantUserDetails userDetails={userDetails} />
            <h2>Abonnierte Biokisten</h2>
            <Abonnements subscriptions={subscriptions} />
        </div>
    )
}
