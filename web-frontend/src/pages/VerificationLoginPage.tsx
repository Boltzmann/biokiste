import LoginForm from "../components/LoginForm";
import {useParams} from "react-router-dom";

export default function VerificationLoginPage(){

    const {verificationId} = useParams()

    return (
        <LoginForm verificationId={verificationId} />
    )
}
