package org.launchcode.capstoneproject.models.forms;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ScheduleForm {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private DateFormat appointmentdate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");




}
