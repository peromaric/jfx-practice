package hr.tvz.dev.models.builders;

import hr.tvz.dev.models.Category;

public class CategoryBuilder {
    private Long id;
    private String name;
    private String description;

    public CategoryBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CategoryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public Category createCategory() {
        return new Category(id, name, description);
    }
}