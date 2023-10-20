package com.geekster.DoctorAppointmentBookingApp.repo;

import com.geekster.DoctorAppointmentBookingApp.model.BloodGroup;
import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientRepo extends JpaRepository<Patient, Integer> {
    Patient findFirstByPatientEmail(String newEmail);

    List<Patient> findByPatientBloodGroup(BloodGroup bloodGroup);

}
