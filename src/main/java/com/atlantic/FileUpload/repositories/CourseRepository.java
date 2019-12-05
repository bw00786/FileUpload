package com.atlantic.FileUpload.repositories;

import com.atlantic.FileUpload.model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, String> {

    @Override
    void delete(Course deleted);

    Course findById(Course id);
}
