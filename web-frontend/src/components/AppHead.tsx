import React from "react";
import "./AppHead.css"
import {useNavigate} from "react-router-dom";
import useUserDetails from "../hooks/useUserDetails";

export default function AppHead(){
    const navigate = useNavigate()

    return (
        <header>
            <h1 className="centered" onClick={() => navigate("/")}>Biokiste</h1>
        </header>
    )
}
