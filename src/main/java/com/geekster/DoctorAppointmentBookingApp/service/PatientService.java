package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.BloodGroup;
import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.model.PatientAuthenticationToken;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.SignInputDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IPTokenRepo;
import com.geekster.DoctorAppointmentBookingApp.repo.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    IPatientRepo patientRepo;

    @Autowired
    PTokenService pTokenService;

    public String patientSignUp(Patient newPatient) {

        //check if already exists -> not allowed : try logging in

        String newEmail = newPatient.getPatientEmail();

        Patient existingPatient = patientRepo.findFirstByPatientEmail(newEmail);

        if(existingPatient != null){
            return "email already exists!";
        }
        // passwords are encrypted before we store it in table
        String signUpPassword = newPatient.getPatientPassword();

        try {
            String encryptedPassword = PasswordEncryptor.encrypt(signUpPassword);
            newPatient.setPatientPassword(encryptedPassword);
            patientRepo.save(newPatient);
            return "Patient registered!";
        } catch (NoSuchAlgorithmException e) {
            return "Internal Server issues while saving password, try again later!!!";
        }

        //patient table - save patient
    }

    public String patientSignIn(SignInputDto signInputDto){

        //check if the email is there in table
        //sign in only possible if this person ever signed up

        String email = signInputDto.getEmail();

        Patient existingPatient = patientRepo.findFirstByPatientEmail(email);

        if(existingPatient == null){
            return "Not a valid email, Please sign up first";
        }

        //password should be matched

        String password = signInputDto.getPassword();

        try {
            String encryptedPassword = PasswordEncryptor.encrypt(password);

            if(existingPatient.getPatientPassword().equals(encryptedPassword)){
                //return a token for this sign in
                PatientAuthenticationToken token = new PatientAuthenticationToken(existingPatient);

                if(EmailService.sendEmail(email,"otp after login", token.getTokenValue())){
                    pTokenService.createToken(token);
                }else{
                    return "error while generating token!";
                }


                return "check email for otp/token!";
            }else{
                //password is wrong
                return "Invalid Credentials!";
            }
        } catch (NoSuchAlgorithmException e) {
            return "Internal Server issues while saving password, try again later!!!";
        }
    }

    public String patientSignOut(AuthenticationInputDto authInfo) {

        if(pTokenService.authenticate(authInfo)){
            String tokenValue = authInfo.getTokenValue();
            pTokenService.deleteToken(tokenValue);
            return "Sign Out successful!";
        }else{
            return "Un Authenticated access!";
        }
    }

    public List<Patient> getAllPatient() {
        return patientRepo.findAll();
    }

    public List<Patient> getAllPatientsByBloodGroup(BloodGroup bloodGroup) {
        return patientRepo.findByPatientBloodGroup(bloodGroup);
    }
}
