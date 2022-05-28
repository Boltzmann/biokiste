import "./UserDetail.css"

type UserDetailProps = {
    detail:  string
}

export default function UserDetail({detail}: UserDetailProps){
    return <div className="Detail" >{detail}</div>
}
