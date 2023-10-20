package com.geekster.DoctorAppointmentBookingApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Patient.class, property = "patientId")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;
    private String patientName;
    @Email
    private String patientEmail;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!*])(?!.*\\s).{8,16}$\n")
    private String patientPassword;
    @Enumerated(value = EnumType.STRING)
    private Gender patientGender;
    @Enumerated(value = EnumType.STRING)
    private BloodGroup patientBloodGroup;
    @Size(min = 10, max = 10)
    @Pattern(regexp = "\\d+")
    private String patientContact;
    private LocalDateTime patientDateOfBirth;

    @OneToMany(mappedBy = "patient")
    List<Appointment> appointments;
}
