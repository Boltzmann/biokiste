import React from "react";
import "./AppHead.css"
import {useNavigate} from "react-router-dom";
import ImportantUserDetails from "./ImportantUserDetails";
import {UserDetails} from "../model/UserDetails";

type AppHeadProps = {
    userDetails: UserDetails | undefined
}

export default function AppHead( {userDetails}: AppHeadProps){
    const navigate = useNavigate()

    return (
        <header>
            <ImportantUserDetails userDetails={userDetails} />
            <img src="/logo.svg"/>
            <button className="centered" id="active" onClick={() => navigate("/")}>Biokiste</button>
        </header>
    )
}
