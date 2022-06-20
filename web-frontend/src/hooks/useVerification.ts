import {sendVerificationEmail} from "../service/api-service";
import {toast} from "react-toastify";
import {VerificationDetails} from "../model/VerificationDetails";
import {useNavigate} from "react-router-dom";

export default function useVerification(){
    const navigate = useNavigate()

    const startVerificationProcess = (verificationDetailsDto: VerificationDetails) => {
        sendVerificationEmail(verificationDetailsDto)
            .then(data => {
                console.debug(verificationDetailsDto.username + " started verification process.")
                console.debug("Response is " + data)
            })
            .then(() => navigate("/post-verification-start"))
            .catch(error => toast.error(error))
    }

    return startVerificationProcess
}
