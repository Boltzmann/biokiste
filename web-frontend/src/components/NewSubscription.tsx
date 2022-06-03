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
                    <div id="active" onClick={
                        () => subscribeToBox(subsls.id)
                    }> Abonniere {subsls.name}
                    </div>
            )
        }
    </div>
}
