package com.cmpt213.a5.courseplanner.model.managers;

import com.cmpt213.a5.courseplanner.model.DataProcessor;
import com.cmpt213.a5.courseplanner.model.RawData;
import com.cmpt213.a5.courseplanner.model.dataobjects.*;

import java.io.File;
import java.util.List;

public class DataManager {
    private static DataManager instance = new DataManager();

    private Data data;

    public static DataManager getInstance() {
        return instance;
    }

    private DataManager() {
//        String pathname = "./";
//        String pathname = "./data/test_data.csv";
//        String pathname = "./data/course_data_2016.csv";
        String pathname = "./data/course_data_2018.csv";

        File csvFile = new File(pathname);

        data = DataProcessor.processCourseDataFile(csvFile);
    }

    public void printInModelDumpFormat() {
        data.printInModelDumpFormat();
    }

    public List<Department> getDepartments() {
        return data.getDepartments();
    }

    public List<Course> getCoursesOfDepartment(long departmentId) {
        return data.getCoursesOfDepartment(departmentId);
    }

    public List<Offering> getOfferingsOfCourse(long departmentId, long courseId) {
        return data.getOfferingsOfCourse(departmentId, courseId);
    }

    public List<Component> getComponentsOfOffering(long departmentId, long courseId, long offeringId) {
        return data.getComponentsOfOffering(departmentId, courseId, offeringId);
    }

    public List<GraphData> getGraphDataOfDepartment(long departmentId) {
        return data.getGraphDataOfDepartment(departmentId);
    }

    public void addOffering(RawData newRawData) {
        data.addOffering(newRawData);
    }

    public String getSubjectById(long deptId) {
        return data.getSubjectById(deptId);
    }

    public String getCatalogNumberOfCourse(long deptId, long courseId) {
        return data.getCatalogNumberOfCourse(deptId, courseId);
    }

    public Component getCourseComponentByDetails(String subject, String catalogNumber, int semester, String location, String componentCode) {
        return data.getCourseComponentByDetails(subject, catalogNumber, semester, location, componentCode);
    }
}
