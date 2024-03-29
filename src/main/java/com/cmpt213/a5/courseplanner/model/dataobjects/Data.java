package com.cmpt213.a5.courseplanner.model.dataobjects;

import com.cmpt213.a5.courseplanner.model.RawData;
import com.cmpt213.a5.courseplanner.model.RawDataSortBySubject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Data {

    private List<Department> departments = new ArrayList<>();

    private AtomicLong nextId = new AtomicLong();

    public Data() {

    }

    public Data(List<RawData> rawData) {

        AtomicLong nextId = new AtomicLong();


        if (rawData.size() == 0) {
            return;
        }


        Collections.sort(rawData, new RawDataSortBySubject());

        List<RawData> departmentRawData = new ArrayList<>();

        departmentRawData.add(rawData.get(0));

        for (int i = 1; i < rawData.size(); i++) {

            RawData previousRawData = rawData.get(i - 1);
            RawData currentRawData = rawData.get(i);

            if (currentRawData.isSameSubject(previousRawData)) {
                departmentRawData.add(currentRawData);
            } else {

                departments.add(new Department(departmentRawData, nextId.incrementAndGet()));

                departmentRawData.clear();
                departmentRawData.add(currentRawData);
            }
        }
        // add the last department.
        departments.add(new Department(departmentRawData, nextId.incrementAndGet()));

    }


    public void printInModelDumpFormat() {
        for (Department department: departments) {
            department.printInModeDumpFormat();
        }
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<Course> getCoursesOfDepartment(long departmentId) {
        for (Department department: departments) {
            if (department.getDepartmentId() == departmentId) {
                return department.getCourses();
            }
        }
        throw new DepartmentNotFoundException("Department of ID " + departmentId + " not found.");
    }

    public List<Offering> getOfferingsOfCourse(long departmentId, long courseId) {
        for (Department department: departments) {
            if (department.getDepartmentId() == departmentId) {
                return department.getOfferingsOfCourse(courseId);
            }
        }
        throw new DepartmentNotFoundException("Department of ID " + departmentId + " not found.");
    }

    public List<Component> getComponentsOfOffering(long departmentId, long courseId, long offeringId) {
        for (Department department: departments) {
            if (department.getDepartmentId() == departmentId) {
                return department.getComponentsOfOffering(courseId, offeringId);
            }
        }
        throw new DepartmentNotFoundException("Department of ID " + departmentId + " not found.");
    }

    public List<GraphData> getGraphDataOfDepartment(long departmentId) {
        for (Department department: departments) {
            if (department.getDepartmentId() == departmentId) {
                return department.getGraphDataList();
            }
        }
        throw new DepartmentNotFoundException("Department of ID " + departmentId + " not found.");
    }

    public void addOffering(RawData newRawData) {
        boolean isNewDepartment = true;
        for (Department department: departments) {
            if (newRawData.getSubject().equals(department.getSubject())) {
                department.addOffering(newRawData);
                isNewDepartment = false;
            }
        }
        if (isNewDepartment) {
            List<RawData> newRawDataList = new ArrayList<>();
            newRawDataList.add(newRawData);
            departments.add(new Department(newRawDataList, nextId.incrementAndGet()));
            Collections.sort(departments);
        }
    }

    public String getSubjectById(long departmentId) {
        for (Department department: departments) {
            if (department.getDepartmentId() == departmentId) {
                return department.getSubject();
            }
        }
        throw new DepartmentNotFoundException("Department of ID " + departmentId + " not found.");
    }

    public String getCatalogNumberOfCourse(long departmentId, long courseId) {
        for (Department department: departments) {
            if (department.getDepartmentId() == departmentId) {
                return department.getCatalogNumberById(courseId);
            }
        }
        throw new DepartmentNotFoundException("Department of ID " + departmentId + " not found.");
    }

    public Component getCourseComponentByDetails(String subject, String catalogNumber, int semester, String location, String componentCode) {
        for (Department department: departments) {
            if (department.getSubject().equals(subject)) {
                return department.getCourseComponentByDetails(catalogNumber, semester, location, componentCode);
            }
        }
        throw new DepartmentNotFoundException("Department " + subject + " not found.");
    }
}
