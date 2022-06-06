import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";
import {AiOutlinePlus} from "react-icons/ai";

type NewSubscriptionProps = {
    subscribeToBox: (boxId: string) => void
    subscribables: SubscriptionOverviewDto[] | undefined
}

export default function NewSubscription({ subscribeToBox, subscribables}: NewSubscriptionProps ) {

    return <div>
        {subscribables &&
            subscribables.map(
                subsls =>
                    <div>
                        <div id="active" onClick={
                            () => subscribeToBox(subsls.id)
                        }><AiOutlinePlus/>
                        </div>
                        <div>{subsls.name}</div>
                    </div>)}
    </div>
}
