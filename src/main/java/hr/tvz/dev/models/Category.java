package hr.tvz.dev.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class Category extends NamedEntity {
    String description;

    public Category(Long id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    public static List<Category> readCategories() {
        try(Stream<String> stream = Files.lines(new File("dat/categories.txt").toPath())) {
            List<String> data = stream.toList();
            int datasetSize = 3;
            int amountOfObjects = data.size() / datasetSize;
            List<Category> categories = new ArrayList<>();
            for(int i = 0; i < data.size(); i+=amountOfObjects) {
                categories.add(
                        new Category(
                                Long.parseLong(data.get(i)),
                                data.get(i+1),
                                data.get(i+2)
                        )
                );
            }
            return categories;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getDescription(), category.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescription());
    }
}
