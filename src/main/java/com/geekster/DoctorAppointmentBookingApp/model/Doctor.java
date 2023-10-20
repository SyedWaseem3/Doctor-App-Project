package com.geekster.DoctorAppointmentBookingApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Doctor.class, property = "docId")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer docId;
    private String docName;
    private double docFee;
    @Enumerated(value = EnumType.STRING)
    private Specialization docSpecialization;
    @Enumerated(value = EnumType.STRING)
    private Qualification docQualification;
    @Size(min = 10, max = 10)
    @Pattern(regexp = "\\d+")
    private String docContact;

    @OneToMany(mappedBy = "doctor")
    List<Appointment> appointments;
}
