import {useState} from "react";
import {UserDetails} from "../model/UserDetails";
import {sendVerificationEmail} from "../service/api-service";
import {toast} from "react-toastify";
import {VerificationDetails} from "../model/VerificationDetails";

export default function useVerification(){
    const [verificationDetails, setVerificationDetails] = useState<UserDetails>()

    const startVerificationProcess = (verificationDetailsDto: VerificationDetails) => {
        sendVerificationEmail(verificationDetailsDto)
            .then(data => {
                toast.info(data.username + " " + data.email)
                setVerificationDetails(data)
            })
            .catch(error => toast.error(error))
    }

    return startVerificationProcess
}
