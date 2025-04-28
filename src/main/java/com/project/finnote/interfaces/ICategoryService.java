package com.project.finnote.interfaces;

import com.project.finnote.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();

    /** Vraća jednu kategoriju po ID-u (ili null ako ne postoji). */
    Category findById(int categoryId);
}
