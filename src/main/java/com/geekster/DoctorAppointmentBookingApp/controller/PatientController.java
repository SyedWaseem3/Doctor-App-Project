package com.geekster.DoctorAppointmentBookingApp.controller;

import com.geekster.DoctorAppointmentBookingApp.model.*;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.ScheduleAppointmentDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.SignInputDto;
import com.geekster.DoctorAppointmentBookingApp.service.AppointmentService;
import com.geekster.DoctorAppointmentBookingApp.service.DoctorService;
import com.geekster.DoctorAppointmentBookingApp.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PatientController {
    @Autowired
    PatientService patientService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorService doctorService;

    //Sign up
    @PostMapping("patient/signup")
    public String patientSignUp(@RequestBody @Valid Patient newPatient){
        return patientService.patientSignUp(newPatient);
    }

    //Sign in
    @PostMapping("patient/signin")
    public String patientSignIn(@RequestBody @Valid SignInputDto signInputDto){
        return patientService.patientSignIn(signInputDto);
    }

    //Sign out
    @DeleteMapping("patient/signOut")
    public String patientSignOut(@RequestBody @Valid AuthenticationInputDto authInfo){
        return patientService.patientSignOut(authInfo);
    }

    //schedule an appointment
    @PostMapping("patient/appointment/schedule")
    public String scheduleAppointment(@RequestBody ScheduleAppointmentDto scheduleAppointmentDto){
        return appointmentService.scheduleAppointment(scheduleAppointmentDto.getAuthInfo(), scheduleAppointmentDto.getAppointment());
    }

    @DeleteMapping("patient/appointment/cancel/{appointmentId}")
    public String cancelAppointment(@RequestBody AuthenticationInputDto authInfo, @PathVariable Integer appointmentId){
        return appointmentService.cancelAppointment(authInfo, appointmentId);
    }

    @GetMapping("doctors/specialization/{specialization}/qualification/{qualification}")
    public List<Doctor> getDoctorsBySpecializationAndQualification(@RequestBody AuthenticationInputDto authInfo,@PathVariable Specialization specialization, @PathVariable Qualification qualification){
        return doctorService.getDoctorsBySpecializationOrQualification(authInfo, specialization, qualification);
    }
}
