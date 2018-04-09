package com.cmpt213.a5.courseplanner.model.dataobjects;

import com.cmpt213.a5.courseplanner.model.RawData;
import com.cmpt213.a5.courseplanner.model.RawDataSortByCatalogNumber;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Department {

    @JsonProperty("deptId")
    private long departmentId;

    @JsonProperty("name")
    private String subject;



    @JsonIgnore
    private List<Course> courses = new ArrayList<>();


    public Department() {

    }

    public Department(List<RawData> departmentRawData, long departmentId) {

        AtomicLong nextId = new AtomicLong();

        this.departmentId = departmentId;
        subject = departmentRawData.get(0).getSubject();

        Collections.sort(departmentRawData, new RawDataSortByCatalogNumber());

        List<RawData> courseRawData = new ArrayList<>();

        courseRawData.add(departmentRawData.get(0));

        for (int i = 1; i < departmentRawData.size(); i++) {

            RawData previousRawData = departmentRawData.get(i - 1);
            RawData currentRawData = departmentRawData.get(i);

            if (currentRawData.hasSameCatalogNumber(previousRawData)) {
                courseRawData.add(currentRawData);
            } else {

                courses.add(new Course(courseRawData, nextId.incrementAndGet()));

                courseRawData.clear();
                courseRawData.add(currentRawData);
            }
        }
        courses.add(new Course(courseRawData, nextId.incrementAndGet()));

    }


    // getters and setters for json fields.
    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



    public void printInModeDumpFormat() {
        for (Course course: courses) {
            course.printInModeDumpFormat();
        }
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Offering> getOfferingsOfCourse(long courseId) {
        for (Course course: courses) {
            if (course.getCourseId() == courseId) {
                return course.getOfferings();
            }
        }
        throw new CourseNotFoundException("Course of ID " + courseId + " not found.");
    }

    public List<Component> getComponentsOfOffering(long courseId, long offeringId) {
        for (Course course: courses) {
            if (course.getCourseId() == courseId) {
                return course.getComponentsOfOffering(offeringId);
            }
        }
        throw new CourseNotFoundException("Course of ID " + courseId + " not found.");
    }
}