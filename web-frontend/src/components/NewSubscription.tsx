import {UserDetails} from "../model/UserDetails";
import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";

type NewSubscriptionProps = {
    userDetails: UserDetails | undefined
    subscribeToBox: (boxId: string) => void
    subscribables: SubscriptionOverviewDto[] | undefined
}

export default function NewSubscription({ userDetails, subscribeToBox , subscribables}: NewSubscriptionProps ){

    return <div>
        {subscribables &&
            subscribables.map(
                subsls =>
                    <button onClick={
                        () => subscribeToBox(subsls.id)
                    }> Add {userDetails && userDetails.id} to Box {subsls.id}
                    </button>
            )
        }
    </div>
}
