package com.example.projekat.controller;

import java.text.DateFormat;
import java.text.ParseException;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class DateConverter extends AbstractBeanField {
    private final DateFormat dateFormat;

    public DateConverter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException {
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new CsvDataTypeMismatchException();
        }
    }
}

