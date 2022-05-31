import {Item} from "./Item";

export type Subscription = {
    id: string
    name: string
    weekOfYear: number
    size: string
    content: Item[]
}
