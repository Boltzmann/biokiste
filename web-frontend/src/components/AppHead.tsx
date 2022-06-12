import React from "react";
import "./AppHead.css"
import {useNavigate} from "react-router-dom";

export default function AppHead(){
    const navigate = useNavigate()

    return (
        <header>
            <button className="centered" id="active" onClick={() => navigate("/")}>Biokiste</button>
        </header>
    )
}
