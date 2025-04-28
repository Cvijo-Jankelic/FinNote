package com.project.finnote.remote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.finnote.entity.Category;
import com.project.finnote.interfaces.ICategoryService;

import java.util.List;

public class RemoteCategoryService implements ICategoryService {
    private final RemoteServiceHelper helper = new RemoteServiceHelper();

    @Override
    public List<Category> findAll() {
        try {
            String json = helper.sendCommand("GET_CATEGORIES");
            return helper.mapper().readValue(json, new TypeReference<List<Category>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Remote getAllCategories failed", e);
        }
    }

    @Override
    public Category findById(int categoryId) {
        // no direct server support for single fetch; fetch all then filter
        return findAll().stream()
                .filter(c -> c.getCategoryId() == categoryId)
                .findFirst()
                .orElse(null);
    }
}
