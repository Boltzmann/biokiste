import ImportantUserDetails from "../components/ImportantUserDetails";
import useUserDetails from "../hooks/useUserDetails";
import Abonnements from "../components/Abonnements";

export default function OverviewPage(){
    const {userDetails, subscriptions} = useUserDetails()

    return (
        <div>
            <ImportantUserDetails userDetails={userDetails} />
            <Abonnements subscriptions={subscriptions} />
        </div>
    )
}
