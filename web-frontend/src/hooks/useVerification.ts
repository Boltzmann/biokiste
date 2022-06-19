import {sendVerificationEmail} from "../service/api-service";
import {toast} from "react-toastify";
import {VerificationDetails} from "../model/VerificationDetails";

export default function useVerification(){

    const startVerificationProcess = (verificationDetailsDto: VerificationDetails) => {
        sendVerificationEmail(verificationDetailsDto)
            .then(data => {
                console.debug(verificationDetailsDto.username + " started verification process.")
                console.debug("Response is " + data)
            })
            .catch(error => toast.error(error))
    }

    return startVerificationProcess
}
