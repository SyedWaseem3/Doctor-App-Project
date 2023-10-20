package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.Doctor;
import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.model.Qualification;
import com.geekster.DoctorAppointmentBookingApp.model.Specialization;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    @Autowired
    IDoctorRepo doctorRepo;

    @Autowired
    PTokenService pTokenService;

    public List<Doctor> getAllDoctors(AuthenticationInputDto authInfo) {
        if(pTokenService.authenticate(authInfo)){
            return doctorRepo.findAll();
        }else{
            return null;
        }
    }

    public String addDoctor(Doctor newDoctor) {

        Integer docId = newDoctor.getDocId();

        if(docId != null && doctorRepo.existsById(docId)){
            return "Doctor already exists!";
        }

        newDoctor.setAppointments(null); // linking doesn't happen from not fk side

        doctorRepo.save(newDoctor);
        return "Doctor added!";
    }

    public Doctor getADoctorById(Integer id) {
        return doctorRepo.findById(id).orElseThrow();
    }

    public List<Doctor> getDoctorsBySpecializationOrQualification(AuthenticationInputDto authInfo,Specialization specialization, Qualification qualification) {
        if(pTokenService.authenticate(authInfo)) {
            List<Doctor> doctors = doctorRepo.findByDocSpecializationOrDocQualification(specialization, qualification);

            return doctors.stream()
                    .map(doc -> {
                        doc.setAppointments(null);
                        return doc;
                    }).collect(Collectors.toList());
        }else {
            return null;
        }
/*        for(Doctor doctor : doctors){
            doctor.setAppointments(null);
        }

        return doctors;*/
    }
}
