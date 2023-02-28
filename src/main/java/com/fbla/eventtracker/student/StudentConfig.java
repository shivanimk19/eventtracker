package com.fbla.eventtracker.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * This class configures the student database table with some default records.
 */
@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student oRodrigo = new Student(
                    "Olivia",
                    "Rodrigo",
                    758679847,
                    "or1234",
                    9,
                    "orodrigo@commack.k12.ny.us"
            );

            Student sCarpenter = new Student(
                    "Sabrina",
                    "Carpenter",
                    574834823,
                    "sc1234",
                    9,
                    "scarpenter@commack.k12.ny.us"
            );

            Student aHathaway = new Student(
                    "Anne",
                    "Hathaway",
                    564878345,
                    "ah1234",
                    9,
                    "ahathaway@commack.k12.ny.us"
            );

            Student pRudd = new Student(
                    "Paul",
                    "Rudd",
                    568457894,
                    "pr1234",
                    9,
                    "prudd@commack.k12.ny.us"
            );

            Student tHolland = new Student(
                    "Tom",
                    "Holland",
                    456765467,
                    "th1234",
                    10,
                    "tholland@commack.k12.ny.us"
            );

            Student jLopez = new Student(
                    "Jennifer",
                    "Lopez",
                    789878987,
                    "jl1234",
                    10,
                    "jlopez@commack.k12.ny.us"
            );

            Student bLively = new Student(
                    "Blake",
                    "Lively",
                    657783241,
                    "bl1234",
                    10,
                    "blively@commack.k12.ny.us"
            );

            Student tCruise = new Student(
                    "Tom",
                    "Cruise",
                    674329854,
                    "tc1234",
                    10,
                    "tcruise@commack.k12.ny.us"
            );

            Student tSwift = new Student(
                    "Taylor",
                    "Swift",
                    457893874,
                    "ts1234",
                    11,
                    "tswift@commack.k12.ny.us"
            );

            Student cEvans = new Student(
                    "Chris",
                    "Evans",
                    457839845,
                    "ce1234",
                    11,
                    "cevans@commack.k12.ny.us"
                    );

            Student kHudson = new Student(
                    "Kate",
                    "Hudson",
                    658729383,
                    "kh1234",
                    11,
                    "khudson@commack.k12.ny.us"
            );

            Student rGosling = new Student(
                    "Ryan",
                    "Gosling",
                    657878398,
                    "rg1234",
                    11,
                    "rgosling@commack.k12.ny.us"
            );

            Student tHanks = new Student(
                    "Tom",
                    "Hanks",
                    934875526,
                    "th1234",
                    12,
                    "thanks@commack.k12.ny.us"
            );

            Student bPitt = new Student(
                    "Brad",
                    "Pitt",
                    984753665,
                    "bp1234",
                    12,
                    "bpitt@commack.k12.ny.us"
            );

            Student jRoberts = new Student(
                    "Julia",
                    "Roberts",
                    874567899,
                    "jr1234",
                    12,
                    "khudson@commack.k12.ny.us"
            );

            Student eWatson = new Student(
                    "Emma",
                    "Watson",
                    776438763,
                    "ew1234",
                    12,
                    "ewatson@commack.k12.ny.us"
            );

            repository.saveAll(
                    List.of(oRodrigo, sCarpenter, aHathaway, pRudd, tHolland, jLopez, bLively, tCruise, tSwift, cEvans, kHudson, rGosling, tHanks, bPitt, jRoberts, eWatson)
            );
        };
    }
}
